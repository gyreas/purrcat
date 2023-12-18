package aadesaed.cat.input;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.OpenOption;
import java.util.List;

public class ReadFile {
   /* Return a file in memory */
   /* TODO: Multiple read method for files: entire file, read functionals*/
   public static String read_entire_file(String path) {
      Charset charset = Charset.forName("US-ASCII");
      Path       file = Paths.get(path);
      String  content = null;

      try {
         content = Files.readString(file, charset);
      } catch (IOException x) {
         System.err.format("IOException: %s%n", x);
      }

      if (content == null) return ""     ;
      else                 return content; /* file */
   }
}
