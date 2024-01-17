package aadesaed.cat.app;

import static aadesaed.cat.input.Utils.*;
import static java.lang.System.exit;

import aadesaed.cat.app.Output_Options.Numbering_Mode;
import aadesaed.cat.cmdline.Args;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;

public class App {
  private static final Props PROPS = new Props("/META-INF/application.properties");
  private static final String NEWLINE = System.lineSeparator();

  public static void main(String[] args) throws IOException {
    Args config = Args.parse_args(args);
    int code = 0;

    if (config.display_help) {
      print_Version();
      print_Usage();
      exit(code);
    }
    if (config.display_version) {
      print_Version();
      exit(code);
    }

    // TODO: combine this with Args.java to eliminate duplicate code
    Numbering_Mode mode;
    if (config.display_line_numbers && !config.display_line_numbers_nonblank)
      mode = Numbering_Mode.All;
    else if (config.display_line_numbers_nonblank) mode = Numbering_Mode.Non_Blank;
    else mode = Numbering_Mode.None;

    Output_Options options =
        new Output_Options(
            mode, config.squeeze_blank_lines, config.display_tabs, config.display_nonprinting);

    ArrayList<String> files = config.files;
    Output_State state = new Output_State();
    for (String path : files) {
      // check what file is.
      try {
        cat_Path(path, state, options);
      } catch (NoSuchFileException nsfe) {
        System.err.println("No such file or directory: " + path);
        code = 1;
      } catch (AccessDeniedException ade) {
        System.err.println("Permission denied: " + path);
        code = 1;
      } catch (Purrcat_Exception pe) {
        System.err.println(pe.getLocalizedMessage());
        code = 1;
      }
    }
    exit(code);
  }

  private static void print_Usage() {
    String usage =
        String.join(
            NEWLINE,
            app_Meta(),
            "Concatenate files to standard output.",
            "With no file, or when file is -, read standard input.",
            "",
            "USAGE:",
            "    purrcat [options] [files]",
            "-----",
            "-h, --help               display this help and exit",
            "-n, --number             number all output lines",
            "-b, --number-nonblank    number all nonempty output lines, overrides -n",
            "-s, --squeeze-blank      suppress repeated empty output line",
            "-T, --show-tabs          display TAB characters as ^I",
            "-v, --show-nonprinting   use ^ and M- notation, except for LFD and TAB",
            "-V, --version            output version information and exit",
            NEWLINE,
            "Examples:",
            "  purrcat f - g  Output f's contents, then standard input, then g's contents.",
            "  purrcat        Copy standard input to standard output. ",
            NEWLINE);

    System.out.printf("%s\n", usage);
  }

  private static String app_Meta() {
    return String.format("author: %s\n", PROPS.get_Author())
        + String.format("license: %s\n", PROPS.get_License());
  }

  private static void print_Version() {
    String version = PROPS.get_Version();
    System.out.printf("%s", String.format("%s %s\n", PROPS.get_App_Name(), version));
  }
}
