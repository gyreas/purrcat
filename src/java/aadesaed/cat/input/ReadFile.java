package aadesaed.cat.input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {
    /* TODO: Multiple read method for files: entire file, read functionals*/

    /* Return a file in memory */
    public static Path get_file(String path) {
        Path file = Paths.get(path);
        return file;
    }

    /* Read entire file to String.
     *
     * Useful for when no commondline option was provided.
     */
    public static String read_to_string(Path p) {
        Charset charset = Charset.forName("US-ASCII");
        String content = null;

        try {
            content = Files.readString(p, charset);
        } catch (IOException x) {
            System.err.format("Unable to read %s %n", p.toString());
        }

        if (content == null) return "";
        else return content; /* file */
    }

    /* Read a file applying the given function on each line.
     *
     * For now, the functions are specified as String and only one per call.
     */
    public static String read_by(Path p, String method) {
        return "Seriously, I have to read";
    }
}
