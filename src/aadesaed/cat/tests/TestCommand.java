package aadesaed.cat.tests;

/* Defines a command to be run */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.Process;
import java.lang.ProcessBuilder.Redirect;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import static org.testng.Assert.*;

import aadesaed.cat.tests.TESTHelpers;
import static aadesaed.cat.tests.TESTHelpers.*;

public class TestCommand {
   String   command;
   String   out;
   String[] args;

   public
   TestCommand(String command, String arg) {
      this.command = command;
      this.args = new String[] { arg };
   }

   public
   TestCommand(String command, String[] args) {
      this.command = command;
      this.args = args;
   }

   private
   String cat_args() {
      String a = "";
      for (String arg : this.args) a += String.format("%s ", arg);

      // NOTE: Never forget to trim paths again!!!!!
      return a.trim();
   }

   public
   void exec() {
      String args = cat_args();
 
      System.out.printf("COMMAND: '%s', ARGS: '%s' \n", PRG, args);
      linebr();

      ProcessBuilder pb = new ProcessBuilder(PRG, args);
      pb.directory(new File(TESTDIR));
      pb.redirectErrorStream(true);
      try {
         Process process = pb.start();
         byte[] b = process.getInputStream().readAllBytes();
         this.out = new String(b);
      } catch (IOException e) {
         System.out.println("An error occurred: " + e.toString());
      }
   }

   public
   String get_output() {
      return this.out;
   }

   public
   void assert_output(String expected) {
      String msg = 
         String.format("Expected: \n<%s>\n", expected) + 
         String.format("Got: \n<%s>\n", this.out);
      assertEquals(this.out, expected);
   }
}
