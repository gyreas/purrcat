package aadesaed.cat.app;

import static java.lang.System.exit;
import static org.testng.Assert.assertNotSame;

import aadesaed.cat.cmdline.Args;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import org.testng.annotations.Test;

public class App {

  private static final String PROGRAM = "cat";
  private static final String LICENSE = "MPL-2.0"; // TODO: read from a file
  private static final String VERSION = "v0.0.2"; // TODO: read from a file
  private static final String AUTHOR = "Saheed Adeleye";
  private static final String AUTHOR_EMAIL = "aadesaed@tuta.io";
  private static final String NEWLINE = System.lineSeparator();

  private enum Numbering_Mode {
    None,
    All,
    Non_Blank,
  }

  private static class OutputState {
    int line_Number = 1;
    boolean at_Line_Start = true;
    boolean one_Blank_Kept = false;
    boolean skipped_Carriage_Return = false;
  }

  private static class OutputOptions {
    Numbering_Mode number;
    boolean squeeze_Blank_Lines;
    boolean show_Tabs;
    boolean show_Ends;
    boolean show_Nonprint;

    OutputOptions(Numbering_Mode number, boolean squeeze_Blank_Lines, boolean show_Tabs) {
      this.number = number;
      this.squeeze_Blank_Lines = squeeze_Blank_Lines;
      this.show_Tabs = show_Tabs;
    }

    String tab() {
      if (this.show_Tabs) return "^I";
      else return "\t";
    }

    String end_Of_Line() {
      if (this.show_Ends) return "$\n";
      else return "\n";
    }
  }

  public static void main(String[] args) throws IOException {
    Args config;
    if (args.length == 0) config = Args.default_args();
    else config = Args.parse_args(args);

    if (config.display_help) {
      print_version();
      print_usage();
      exit(0);
    }
    if (config.display_version) {
      print_version();
      exit(0);
    }
    if (config.files.isEmpty()) System.out.println();
    else {
      // TODO: combine this with Args.java to eliminate duplicate code
      Numbering_Mode mode;
      if (config.display_line_numbers && !config.display_line_numbers_nonblank)
        mode = Numbering_Mode.All;
      else if (config.display_line_numbers_nonblank) mode = Numbering_Mode.Non_Blank;
      else mode = Numbering_Mode.None;

      OutputOptions options =
          new OutputOptions(mode, config.squeeze_blank_lines, config.display_tabs);

      ArrayList<String> files = config.files;
      for (String file : files) {
        OutputState state = new OutputState();
        // TODO: fix resource usage in this class
        FileChannel reader = new FileInputStream(file).getChannel();
        write_Lines(reader, state, options);
      }
    }
  }

  // TODO: This is it don't get scared now
  private static void write_Lines(FileChannel handle, OutputState state, OutputOptions options)
      throws IOException {
    int buf_Size = 1024 * 31;
    PrintStream out = System.out;
    WritableByteChannel writer = Channels.newChannel(out);
    ByteBuffer in_Buf = ByteBuffer.allocate(buf_Size);

    // each read
    int amt_Read = handle.read(in_Buf);
    while (amt_Read != -1) {
      // go through the buffer
      int pos = 0; // position of the current char
      while (pos < amt_Read) {
        byte b = in_Buf.get(pos);
        // skip empty line_Number, enumerating them if possible
        if (state.skipped_Carriage_Return) {
          out.write('\r');
          state.skipped_Carriage_Return = false;
          state.at_Line_Start = false;
        }

        if (b == '\n') {
          write_New_Line(out, state, options);
          pos++;
          state.at_Line_Start = true;
          continue;
        }

        state.one_Blank_Kept = false;
        if (state.at_Line_Start && options.number != Numbering_Mode.None) {
          write_Line_Number(out, state.line_Number);
          state.line_Number++;
        }

        // // dump everything else
        int len = amt_Read - pos;
        // slicing is relative to zero index of the buffer, not relative to the current position
        int offset = write_End(writer, in_Buf.slice(pos, len), options);
        // end of buffer?
        if (pos + offset == amt_Read) {
          state.at_Line_Start = false;
          break;
        }
        if (in_Buf.get(pos + offset) == '\r') {
          state.skipped_Carriage_Return = true;
        } else {
          write_End_Of_Line(out, options.end_Of_Line().getBytes());
          state.at_Line_Start = true;
        }
        pos += offset + 1;
      }
      in_Buf.clear();
      amt_Read = handle.read(in_Buf);
      out.flush();
    }
    handle.close();
  }

  private static void write_End_Of_Line(PrintStream writer, byte[] end_Of_Line) throws IOException {
    writer.write(end_Of_Line);
  }

  private static int write_End(WritableByteChannel writer, ByteBuffer buf, OutputOptions options)
      throws IOException {
    int amt;
    // if (options.show_Nonprint) {
    //   System.exit(1);
    //   amt = 0; // write_nonprint_to_end(in_buf, writer, options.tab().as_bytes())
    // } else
    if (options.show_Tabs) {
      amt = write_Tab_To_End(writer, buf);
    } else {
      amt = write_To_End(writer, buf);
    }
    return amt;
  }

  private static void write_New_Line(PrintStream writer, OutputState state, OutputOptions options)
      throws IOException {
    if (state.skipped_Carriage_Return && options.show_Ends) {
      writer.write("^M".getBytes());
      state.skipped_Carriage_Return = false;
    }

    if (!state.at_Line_Start || !options.squeeze_Blank_Lines || !state.one_Blank_Kept) {
      state.one_Blank_Kept = true;
      if (state.at_Line_Start && options.number == Numbering_Mode.All) {
        write_Line_Number(writer, state.line_Number);
        state.line_Number++;
      }
      writer.write(options.end_Of_Line().getBytes());
    }
  }

  private static void write_Line_Number(PrintStream writer, int lineno) throws IOException {
    String line = String.format("%6d\t", lineno);
    writer.write(line.getBytes());
  }

  private static int find_Tab_In_Buffer(ByteBuffer buffer) {
    int len = buffer.remaining();
    for (int i = 0; i < len; i++) {
      byte b = buffer.get(i);
      if (b == '\n' || b == '\t' || b == '\r') {
        return i;
      }
    }
    return -1;
  }

  @Test(enabled = true)
  public void can_Find_Tab_In_Buffer() {
    // System.out.println("[Can find tab in buffer]");
    ByteBuffer buf = ByteBuffer.wrap("A\ttab".getBytes());
    assertNotSame(find_Tab_In_Buffer(buf), -1, "There's a tab in there.");
  }

  private static int write_Tab_To_End(WritableByteChannel writer, ByteBuffer in_Buf)
      throws IOException {
    int count = 0;
    int len = in_Buf.remaining();
    while (true) {
      int pos = find_Tab_In_Buffer(in_Buf);
      if (pos != -1) {
        byte b = in_Buf.get(pos);
        writer.write(in_Buf.slice(0, pos));
        if (b == '\t') {
          System.out.write("^I".getBytes());
          count += pos + 1;
        } else {
          // 10 ('\n') or 13 ('\r')
          return count + pos;
        }
        // Update in_Buf to the remaining part
        len -= pos + 1;
        in_Buf = in_Buf.slice(pos + 1, len);
      } else {
        // No newline or tab found, write the entire buffer
        writer.write(in_Buf);
        return len;
      }
    }
  }

  private static int write_To_End(WritableByteChannel writer, ByteBuffer buf) throws IOException {
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

  // fn write_nonprint_to_end<W: Write>(in_buf: &[u8], writer: &mut W, tab: &[u8]) -> usize {
  //     let mut count = 0;

  //     for byte in in_buf.iter().copied() {
  //         if byte == b'\n' {
  //             break;
  //         }
  //         match byte {
  //             9 => writer.write_all(tab),
  //             0..=8 | 10..=31 => writer.write_all(&[b'^', byte + 64]),
  //             32..=126 => writer.write_all(&[byte]),
  //             127 => writer.write_all(&[b'^', b'?']),
  //             128..=159 => writer.write_all(&[b'M', b'-', b'^', byte - 64]),
  //             160..=254 => writer.write_all(&[b'M', b'-', byte - 128]),
  //             _ => writer.write_all(&[b'M', b'-', b'^', b'?']),
  //         }
  //         .unwrap();
  //         count += 1;
  //     }
  //     count
  // }

  private static void print_usage() {
    String usage =
        String.join(
            NEWLINE,
            app_meta(),
            "-h, --help               display this help and exit",
            "-n, --number             number all output lines",
            "-b, --number-nonblank    number all nonempty output lines, overrides -n",
            "-s, --squeeze-blank      suppress repeated empty output line",
            "-T, --show-tabs          display TAB characters as ^I",
            "-V, --version            output version information and exit");

    System.out.printf("%s\n", usage);
  }

  private static String app_meta() {
    return String.format("Author: %s <%s>\n", AUTHOR, AUTHOR_EMAIL)
        + String.format("License: %s\n", LICENSE);
  }

  private static void print_version() {
    System.out.printf("%s", String.format("%s %s\n", PROGRAM, VERSION));
  }
}
