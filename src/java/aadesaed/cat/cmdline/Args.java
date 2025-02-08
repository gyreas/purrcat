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
    public boolean displayHelp;
    public boolean displayVersion;
    public boolean displayLineNumbers;
    public boolean displayLineNumbersNonblank;
    public boolean displayTabs;
    public boolean displayNonprinting;
    public boolean squeezeBlankLines;

    public Args() {
        this.files = new ArrayList<>();
    }

    /** Return an Args with default values */
    public static Args defaultArgs() {
        Args config = new Args();
        config.displayHelp = false;
        config.displayLineNumbers = false;
        config.squeezeBlankLines = false;
        config.displayVersion = false;
        config.displayLineNumbersNonblank = false;
        config.displayTabs = false;
        config.displayNonprinting = false;
        config.files = new ArrayList<>(List.of("-"));
        return config;
    }

    /*
     * Parse the arguments passed to the program.
     */
    public static Args parseArgs(String[] argv) {
        if (argv.length == 0) {
            defaultArgs();
        }

        Args config = new Args();
        ArrayList<String> args = new ArrayList<>(Arrays.asList(argv));

        int i = 0; // always get the first element since we're consuming
        while (!args.isEmpty()) {
            String arg = args.get(i);
            if (arg.equals("-h") || arg.equals("--help")) {
                config.displayHelp = true;
                args.remove(i);
            } else if (arg.equals("-V") || arg.equals("--version")) {
                config.displayVersion = true;
                args.remove(i);
            } else if (arg.equals("-n") || arg.equals("--number")) {
                config.displayLineNumbers = true;
                args.remove(i);
            } else if (arg.equals("-s") || arg.equals("--squeeze-blank")) {
                config.squeezeBlankLines = true;
                args.remove(i);
            } else if (arg.equals("-b") || arg.equals("--number-nonblank")) {
                config.displayLineNumbersNonblank = true;
                args.remove(i);
            } else if (arg.equals("-T") || arg.equals("--show-tabs")) {
                config.displayTabs = true;
                args.remove(i);
            } else if (arg.equals("-v") || arg.equals("--show-nonprinting")) {
                config.displayNonprinting = true;
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
                            config.displayHelp = true;
                        } else if (first == 'V') {
                            config.displayVersion = true;
                        } else if (first == 'v') {
                            config.displayNonprinting = true;
                        } else if (first == 's') {
                            config.squeezeBlankLines = true;
                        } else if (first == 'n') {
                            config.displayLineNumbers = true;
                        } else if (first == 'b') {
                            config.displayLineNumbersNonblank = true;
                        } else if (first == 'T') {
                            config.displayTabs = true;
                        } else {
                            invalidOption(Character.toString(first));
                            break done;
                        }
                    }
                } else {
                    invalidOption(arg.substring(1, 2));
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

    private static void invalidOption(String arg) {
        System.out.printf(
                "purrcat: '%s' is an invalid option.\n",
                arg.startsWith("--") ? arg.replaceFirst("--", "") : arg.replaceFirst("-", ""));
        System.out.println("Try 'purrcat --help' for available options.");
        System.exit(1);
    }
}
