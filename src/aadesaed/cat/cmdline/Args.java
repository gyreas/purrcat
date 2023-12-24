package aadesaed.cat.cmdline;

import java.util.ArrayList;
import java.util.Arrays;

public class Args {
  /*
   * Configuration which the app is called with. Parsed commandline arguments
   * and options for the `cat` program.
   *
   * -h | --help
   * -v | --version
   * -n | --number-lines
   * -b | --number-nonblank
   * -s | --squeeze-blank-lines
   * <files>
   */
  public ArrayList<String> files;
  public boolean display_help;
  public boolean display_version;
  public boolean display_line_numbers;
  public boolean display_line_numbers_nonblank;
  public boolean squeeze_blank_lines;

  public Args() {
    files = new ArrayList<>();
  }

  /** Return an Args with default values */
  public static Args default_args() {
    var config = new Args();
    config.display_help = false;
    config.display_line_numbers = false;
    config.squeeze_blank_lines = false;
    config.display_version = true; // indicates that the app works, just no input file
    config.display_line_numbers_nonblank = true;
    config.files = new ArrayList<>();
    return config;
  }

  /*
   * Parse the arguments passed to the program.
   */
  public static Args parse_args(String[] argv) {
    if (argv.length == 0) {
      default_args();
    }

    Args config = new Args();
    ArrayList<String> args = new ArrayList<>(Arrays.asList(argv));

    int i = 0; // always get the first element since we're consuming
    while (!args.isEmpty()) {
      String arg = args.get(i);
      if (arg.equals("-h") || arg.equals("--help")) {
        config.display_help = true;
        args.remove(i);
      } else if (arg.equals("-V") || arg.equals("--version")) {
        config.display_version = true;
        args.remove(i);
      } else if (arg.equals("-n") || arg.equals("--number")) {
        config.display_line_numbers = true;
        args.remove(i);
      } else if (arg.equals("-s") || arg.equals("--squeeze-blank")) {
        config.squeeze_blank_lines = true;
        args.remove(i);
      } else if (arg.equals("-b") || arg.equals("--number-nonblank")) {
        config.display_line_numbers_nonblank = true;
        args.remove(i);
      } else if (arg.equals("--")) {
        config.files = new ArrayList<>(args.subList(i + 1, args.size()));
        return config;
      } else if (arg.startsWith("-")) {
        System.out.printf("Unknown options: %s.\n", arg);
        System.exit(1);
      } else {
        // TODO: `write` to output stream, no `print`
        config.files.add(arg);
        args.remove(i);
      }
    }

    return config;
  }
}
