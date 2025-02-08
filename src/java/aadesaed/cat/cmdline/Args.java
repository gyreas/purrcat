package aadesaed.cat.cmdline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
   * -T | --show-tabs
   * -v | --show-nonprinting
   * <files incl. stdin>
   */
  public ArrayList<String> files;
  public boolean display_help;
  public boolean display_version;
  public boolean display_line_numbers;
  public boolean display_line_numbers_nonblank;
  public boolean display_tabs;
  public boolean display_nonprinting;
  public boolean squeeze_blank_lines;

  public Args() {
    this.files = new ArrayList<>();
  }

  /** Return an Args with default values */
  public static Args default_args() {
    Args config = new Args();
    config.display_help = false;
    config.display_line_numbers = false;
    config.squeeze_blank_lines = false;
    config.display_version = false;
    config.display_line_numbers_nonblank = false;
    config.display_tabs = false;
    config.display_nonprinting = false;
    config.files = new ArrayList<>(List.of("-"));
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
      } else if (arg.equals("-T") || arg.equals("--show-tabs")) {
        config.display_tabs = true;
        args.remove(i);
      } else if (arg.equals("-v") || arg.equals("--show-nonprinting")) {
        config.display_nonprinting = true;
        args.remove(i);
      } else if (arg.equals("--")) {
        config.files = new ArrayList<>(args.subList(i + 1, args.size()));
        return config;
      } else if (arg.equals("-")) {
        config.files.add(arg);
        args.remove(i);
      } else if (arg.startsWith("-")) {
        String rest = arg.substring(1);
        args.remove(i);
        if (rest.chars().anyMatch(c -> "TVbhnsv".contains(Character.toString(c)))) {
          done:
          for (int a = 0; a < rest.length(); a++) {
            char first = rest.charAt(a);
            if (first == 'h') {
              config.display_help = true;
            } else if (first == 'V') {
              config.display_version = true;
            } else if (first == 'v') {
              config.display_nonprinting = true;
            } else if (first == 's') {
              config.squeeze_blank_lines = true;
            } else if (first == 'n') {
              config.display_line_numbers = true;
            } else if (first == 'b') {
              config.display_line_numbers_nonblank = true;
            } else if (first == 'T') {
              config.display_tabs = true;
            } else {
              invalid_Option(Character.toString(first));
              break done;
            }
          }
        } else {
          invalid_Option(arg.substring(1, 2));
        }
      } else {
        config.files.add(arg);
        args.remove(i);
      }
    }

    /* Default to standard input when no file was specified. */
    if (config.files.isEmpty()) config.files.add("-");

    return config;
  }

  private static void invalid_Option(String arg) {
    System.out.printf(
        "purrcat: '%s' is an invalid option.\n",
        arg.startsWith("--") ? arg.replaceFirst("--", "") : arg.replaceFirst("-", ""));
    System.out.println("Try 'purrcat --help' for available options.");
    System.exit(1);
  }
}
