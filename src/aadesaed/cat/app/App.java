package aadesaed.cat.app;

import static java.lang.System.exit;

import aadesaed.cat.cmdline.Args;
import aadesaed.cat.input.ReadFile;

import java.io.*;
import java.nio.file.*;

public class App {
   private static String program      = "cat";
   private static String license      = "BSD 2.0"; // TODO: read from a file
   private static String version      = "v0.0.1"; // TODO: read from a file
   private static String author       = "Saheed Adeleye";
   private static String author_email = "aadesaed@tuta.io";
   private static String newline      = System.getProperty("line.separator");

   public static void main(String[] args) {
      Args config;
      if (args.length == 0) config = Args.default_args();
      else                  config = Args.parse_args(args);

      if (config.display_help) {
         print_version(); 
         print_usage(); 
         exit(0); 
      }
      if (config.display_version) {
         print_version(); 
         exit(0); 
      }
      if (config.files.size() == 0) System.out.println();
      else { 
         for (String path : config.files) {
            Path p = ReadFile.get_file(path);

            try {
               BufferedReader file = Files.newBufferedReader(p);
               int i = 1;
               int empty = 0;

               while (true) {
                  String line = file.readLine();

                  if (line == null) break;

                  if (config.squeeze_blank_lines && line.isBlank()) {
                     if (empty >= 1) continue;
                     empty++;
                  }; 
                  if (config.display_line_numbers_nonblank && line.isBlank()) {
                     System.out.println(); 
                     continue;
                  } else if (config.display_line_numbers || config.squeeze_blank_lines) {
                     line = show_line_number(line, i);
                  }

                  System.out.printf("%s\n", line);
                  i++;
               }
               // System.out.printf("Line: %s\n", line);
            } catch (IOException x){
               x.printStackTrace();
            }
         }
      }
   }

   private static String show_line_number(String line, int lineno) {
      return String.format("%6d\t%s", lineno, line);
   }

   private static void print_usage() {
      String usage = String.join(
         newline,
         app_meta(),
         "-h, --help               display this help and exit",
         "-n, --number             number all output lines",
         "-b, --number-nonblank    number all nonempty output lines, overrides -n",
         "-s, --squeeze-nonblank   suppress repeated empty output line",
         "-V, --version            output version information and exit"
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
