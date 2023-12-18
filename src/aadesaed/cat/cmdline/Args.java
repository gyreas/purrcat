package aadesaed.cat.cmdline;

import aadesaed.cat.input.ReadFile;

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
    * <files>
    */
   public ArrayList<String>   files;
   public boolean             display_help;
   public boolean             display_version;
   public boolean             display_line_numbers;
   public boolean             display_nonblank_lines;

   public Args() {
      this.files = new ArrayList<>();
   }

   /** Return an Args with default values */
   public static Args default_args() {
      var config                       = new Args();
      config.display_help              = false;
      config.display_line_numbers      = false;
      config.display_version           = true; // indicates that the app works, just no input file
      config.display_nonblank_lines    = true;
      config.files                     = new ArrayList<>();
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
      ArrayList<String> a = new ArrayList<>(Arrays.asList(argv));

      int i = 0; // always get the first element since we're consuming
      while (a.size() != 0) {
         String arg = a.get(i);
         if (arg.equals("-h") || arg.equals("--help")) {
            config.display_help = true;
            a.remove(i);
         } else if (arg.equals("-V") || arg.equals("--version")) {
            config.display_version = true;
            a.remove(i);
         } else if (arg.equals("-n") || arg.equals("--number")) {
            config.display_line_numbers = true;
            System.out.println("Displaying line numbers.");
            a.remove(i);
         } else if (arg.equals("--")) {
            config.files = new ArrayList<>(a.subList(i + 1, a.size()));
            return config;
         }
         else if (arg.startsWith("-")) {
            System.out.printf("Unknown options: %s.\n", arg);
            System.exit(1);
         } else {
            // TODO: `write` to output stream, no `print`
            config.files.add(arg);
            a.remove(i);
         }
      }

      return config;
   }

}
