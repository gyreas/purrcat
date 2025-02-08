package aadesaed.cat.app;

import static org.testng.Assert.assertNotSame;

import static java.lang.System.exit;
import static java.nio.ByteBuffer.allocate;
import static java.nio.ByteBuffer.wrap;

import aadesaed.cat.cmdline.Args;
import aadesaed.cat.input.Input;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
    private static final Props PROPS = new Props("/META-INF/application.properties");
    private static final String NEWLINE = System.lineSeparator();
    private static final int STDOUT_FILENO = 1;
    private static final int BUF_SIZE = 1024 * 32;

    private static native void checkFdPath(int fd, String path);

    public static void main(String[] args) throws IOException {
        Args config = Args.parseArgs(args);
        int code = 0;

        if (config.displayHelp) {
            printVersion();
            printUsage();
            exit(code);
        }
        if (config.displayVersion) {
            printVersion();
            exit(code);
        }

        // TODO: combine this with Args.java to eliminate duplicate code
        Output.NumberingMode mode;
        if (config.displayLineNumbers && !config.displayLineNumbersNonblank)
            mode = Output.NumberingMode.All;
        else if (config.displayLineNumbersNonblank) mode = Output.NumberingMode.NonBlank;
        else mode = Output.NumberingMode.None;

        Output.Options options =
                new Output.Options(
                        mode,
                        config.squeezeBlankLines,
                        config.displayTabs,
                        config.displayNonprinting);

        ArrayList<String> files = config.files;
        Output.State state = new Output.State();

        String no_jar = System.getenv("NO_JAR");
        if (no_jar != null && no_jar.equals("1")) {
            System.loadLibrary("purrcatfstat");
        } else {
            Path dest = Paths.get(System.getProperty("user.home"), ".local/state");
            String newjl = System.getProperty("java.library.path") + ":" + dest.toString();

            System.setProperty("java.library.path", newjl);

            Path sopath = dest.resolve("purrcatfstat.so");

            if (!Files.exists(sopath)) Files.createFile(sopath);

            var res = App.class.getResourceAsStream("/META-INF/libpurrcatfstat.so");
            Files.write(sopath, res.readAllBytes());

            System.load(sopath.toString());
        }

        for (String path : files) {
            // check what file is.
            try {
                catPath(path, state, options);
            } catch (NoSuchFileException nsfe) {
                System.err.println("No such file or directory: " + path);
                code = 1;
            } catch (AccessDeniedException ade) {
                System.err.println("Permission denied: " + path);
                code = 1;
            } catch (PurrcatException pe) {
                System.err.println(path + ": " + pe.getLocalizedMessage());
                exit(1);
            }
        }
        exit(code);
    }

    /**
     * Concatenates the content of each path to standard output based on {@code state} and {@code
     * options}.
     *
     * @param path file path as string
     * @param state output state
     * @param options output options
     * @throws IOException if an I/O error occurs
     * @throws PurrcatException if the given path is either a directory, or deeply-nested symbolic
     *     link, which may be a fluke
     */
    public static void catPath(String path, Output.State state, Output.Options options)
            throws IOException, PurrcatException {
        Input.Type it = getInputType(path);
        if (it == Input.Type.Stdin) {
            try (var stdin = Channels.newChannel(System.in);
                    Input.Handle handle = new Input.Handle(stdin, false)) {
                catHandle(handle, state, options);
            }
        } else if (it == Input.Type.Directory) {
            throw new PurrcatException.IsDirectory(path + ": Is a directory.");
        } else {
            /* File Statistics of the redirect file. */
            checkFdPath(STDOUT_FILENO, path);
            try (FileChannel file = (FileChannel) Files.newByteChannel(Paths.get(path));
                    Input.Handle handle = new Input.Handle(file, false)) {
                catHandle(handle, state, options);
            } catch (/* This could be a fluke */ FileNotFoundException fnfe) {
                throw new PurrcatException.TooManySymlinks();
            }
        }
    }

    /**
     * Concatenates the handle to standard output based on {@code state} and {@code options}.
     *
     * @param handle input handle to write to standard output
     * @param state output state
     * @param options output options
     * @throws IOException if an I/O error occurs
     */
    private static void catHandle(Input.Handle handle, Output.State state, Output.Options options)
            throws IOException {
        writeLines(handle, state, options);
    }

    /**
     * Determine the type of filesystem object from the given path.
     *
     * @param path file path as string
     * @return filesystem object type
     */
    private static Input.Type getInputType(String path) throws IOException, PurrcatException {
        if (path.equals("-")) return Input.Type.Stdin;

        Path p = Paths.get(path);
        if (Files.notExists(p)) throw new NoSuchFileException(path);
        if (Files.isDirectory(p)) return Input.Type.Directory;
        else if (Files.isRegularFile(p)) return Input.Type.File;
        else if (Files.isSymbolicLink(p)) return Input.Type.SymLink;
        else {
            throw new PurrcatException.UnknownFileType(
                    "Unknown filetype: " + Files.probeContentType(p));
        }
    }

    /**
     * Using some exotic method to write fast when no input or arguments are provided.
     *
     * @param handle the handle to read
     * @param state output state
     * @param options output options
     */
    private static void writeFast(
            Input.Handle handle, Output.State state, Output.Options options) {}

    /**
     * Write the stream to stdout line by line.
     *
     * @param handle the handle to read
     * @param state output state
     * @param options output options
     */
    private static void writeLines(Input.Handle handle, Output.State state, Output.Options options)
            throws IOException {
        WritableByteChannel writer = Channels.newChannel(System.out);
        ByteBuffer inBuf = allocate(BUF_SIZE);

        // each read
        int amtRead = handle.read(inBuf);
        while (amtRead != -1) {
            // go through the buffer
            int pos = 0; // position of the current char
            while (pos < amtRead) {
                byte b = inBuf.get(pos);
                // skip empty lineNumber, enumerating them if possible
                if (state.skippedCarriageReturn) {
                    System.out.write('\r');
                    state.skippedCarriageReturn = false;
                    state.atLineStart = false;
                }

                if (b == '\n') {
                    writeNewline(writer, state, options);
                    pos++;
                    state.atLineStart = true;
                    continue;
                }

                state.oneBlankKept = false;
                if (state.atLineStart && options.number != Output.NumberingMode.None) {
                    writeLineNumber(writer, state.lineNumber);
                    state.lineNumber++;
                }

                // dump everything else
                int len = amtRead - pos;
                /* slicing is relative to zero index of the buffer, not relative to the current
                 * position
                 */
                int offset = writeEnd(writer, inBuf.slice(pos, len), options);
                // end of buffer?
                if (pos + offset == amtRead) {
                    state.atLineStart = false;
                    break;
                }
                if (inBuf.get(pos + offset) == '\r') {
                    state.skippedCarriageReturn = true;
                } else {
                    writeEndOfLine(writer, options.endOfLine().getBytes());
                    state.atLineStart = true;
                }
                pos += offset + 1;
            }
            inBuf.clear();
            amtRead = handle.read(inBuf);
        }
    }

    private static void writeEndOfLine(WritableByteChannel writer, byte[] endOfLine)
            throws IOException {
        writer.write(wrap(endOfLine));
    }

    private static int writeEnd(WritableByteChannel writer, ByteBuffer buf, Output.Options options)
            throws IOException {
        int amt;
        if (options.showNonprint) {
            amt = writeNonprintToEnd(writer, buf, options.tab().getBytes());
        } else if (options.showTabs) {
            amt = writeTabToEnd(writer, buf);
        } else {
            amt = writeToEnd(writer, buf);
        }
        return amt;
    }

    private static int writeToEnd(WritableByteChannel writer, ByteBuffer buf) throws IOException {
        int len = buf.remaining();
        for (int i = 0; i < len; i++) {
            byte b = buf.get(i);
            if (b == '\n' || b == '\r') {
                writer.write(buf.slice(0, i));
                return i;
            }
        }
        writer.write(buf.slice(0, len));
        return len;
    }

    private static int writeNonprintToEnd(WritableByteChannel writer, ByteBuffer buf, byte[] tab)
            throws IOException {
        int count = 0;
        int len = buf.remaining();
        for (int i = 0; i < len; ++i) {
            int b = Byte.toUnsignedInt(buf.get(i));
            if (b == (byte) '\n') break;
            if (b == 9) System.out.write(tab);
            else if ((0 < b && b <= 8) || (10 <= b && b <= 31))
                writer.write(wrap(new byte[] {'^', (byte) (b + 64)}));
            else if (32 <= b && b <= 126) {
                writer.write(wrap(new byte[] {(byte) b}));
            } else if (b == 127) writer.write(wrap(new byte[] {'^', '?'}));
            else if (128 <= (int) b && (int) b <= 159) {
                writer.write(wrap(new byte[] {'M', '-', '^', (byte) (b - 64)}));
            } else if (160 <= (int) b && (int) b <= 254) {
                writer.write(wrap(new byte[] {'M', '-', (byte) (b - 128)}));
            } else writer.write(wrap(new byte[] {'M', '-', '^', '?'}));

            count++;
        }
        return count;
    }

    private static void writeNewline(
            WritableByteChannel writer, Output.State state, Output.Options options)
            throws IOException {
        if (state.skippedCarriageReturn && options.showEnds) {
            writer.write(wrap("^M".getBytes()));
            state.skippedCarriageReturn = false;
        }

        if (!state.atLineStart || !options.squeezeBlankLines || !state.oneBlankKept) {
            state.oneBlankKept = true;
            if (state.atLineStart && options.number == Output.NumberingMode.All) {
                writeLineNumber(writer, state.lineNumber);
                state.lineNumber++;
            }
            writer.write(wrap(options.endOfLine().getBytes()));
        }
    }

    private static void writeLineNumber(WritableByteChannel writer, int lineno) throws IOException {
        String line = String.format("%6d\t", lineno);
        writer.write(wrap(line.getBytes()));
    }

    private static int writeTabToEnd(WritableByteChannel writer, ByteBuffer inBuf)
            throws IOException {
        int count = 0;
        int len = inBuf.remaining();
        while (true) {
            int pos = findTabInBuffer(inBuf);
            if (pos != -1) {
                byte b = inBuf.get(pos);
                writer.write(inBuf.slice(0, pos));
                if (b == '\t') {
                    writer.write(wrap("^I".getBytes()));
                    count += pos + 1;
                } else {
                    // 10 ('\n') or 13 ('\r')
                    return count + pos;
                }
                // Update inBuf to the remaining part
                len -= pos + 1;
                inBuf = inBuf.slice(pos + 1, len);
            } else {
                // No newline or tab found, write the entire buffer
                writer.write(inBuf);
                return len;
            }
        }
    }

    /**
     * Find the next tab or EOL character in the given buffer.
     *
     * @param buffer the buffer to scan.
     * @return index in the buffer, -1 otherwise.
     */
    private static int findTabInBuffer(ByteBuffer buffer) {
        int len = buffer.remaining();
        for (int i = 0; i < len; i++) {
            byte b = buffer.get(i);
            if (b == '\n' || b == '\t' || b == '\r') {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void canFindTabInBuffer() {
        System.out.println("[TEST] [ Can find tab in buffer ]");
        ByteBuffer buf = wrap("A\ttab".getBytes());
        assertNotSame(findTabInBuffer(buf), -1, "There's a tab in there.");
    }

    private static void printUsage() {
        String usage =
                String.join(
                        NEWLINE,
                        appMeta(),
                        "Concatenate files to standard output.",
                        "With no file, or when file is -, read standard input.",
                        "",
                        "USAGE:",
                        "    purrcat [options] [files]",
                        "-----",
                        "   -h, --help               display this help and exit",
                        "   -n, --number             number all output lines",
                        "   -b, --number-nonblank    number all nonempty output lines, overrides"
                                + " -n",
                        "   -s, --squeeze-blank      suppress repeated empty output line",
                        "   -T, --show-tabs          display TAB characters as ^I",
                        "   -v, --show-nonprinting   use ^ and M- notation, except for LFD and TAB",
                        "   -V, --version            output version information and exit",
                        "",
                        "Examples:",
                        "  purrcat f - g  Output f's contents, then standard input, then g's"
                                + " contents.",
                        "  purrcat        Copy standard input to standard output. ",
                        NEWLINE);

        System.out.print(usage);
    }

    private static String appMeta() {
        return "author: " + PROPS.getAuthor() + "license: " + PROPS.getLicense() + NEWLINE;
    }

    private static void printVersion() {
        System.out.printf("%s", String.format("%s %s\n", PROPS.getAppName(), PROPS.getVersion()));
    }
}
