package aadesaed.cat.app;

import static org.apache.commons.cli.CommandLine.*;
import static java.lang.System.exit;

import aadesaed.cat.cmdline.Args;

public class App {
   // TODO: Use a HashTable for this
   private static String program      = "cat";
   private static String license      = "BSD 2.0"; // TODO: read from a file
   private static String version      = "0.0.1"; // TODO: read from a file
   private static String author       = "Saheed Adeleye";
   private static String author_email = "aadesaed@tuta.io";
   private static String newline      = System.getProperty("line.separator");

   public static void main(String[] args) {
      // if "--help" or "-h" (print_usage()) # prio 1 (highest)
      // if "--version" or "-v" (print_version()) (2nd highest, except when it's the first opt) 
      // 
      Args a;
      if (args.length == 0) a = Args.default_args();
      else                  a = Args.parse_args(args);

      if (a.display_help) {
         print_version(); 
         print_usage(); 
         exit(0); 
      }
      if (a.display_version) {
         print_version(); 
         exit(0); 
      }
   }

   private static void print_usage() {
      String usage = String.join(
         newline,
         app_meta(),
         "-h, --help     display this help and exit",
         "-n, --number   number all output lines",
         "-V, --version  output version information and exit"
      );

      System.out.printf("%s\n", usage);
   }

   private static String app_meta() {
      StringBuilder app_meta = new StringBuilder();
      app_meta.append(String.format("Author: %s <%s>\n", author, author_email));
      app_meta.append(String.format("License: %s\n", license));
      return app_meta.toString();
   }

   private static void print_version() {
      StringBuilder version_ = new StringBuilder();
      version_.append(String.format("%s %s\n", program, version));
      System.out.printf("%s", version_);
   }
}
