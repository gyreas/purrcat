package aadesaed.cat.tests;

/* Defines a command to be run */
import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestCommand {
  String command;
  String out;
  // TODO: Use array list here
  String[] args;

  public TestCommand(String command, String arg) {
    this.command = command;
    this.args = new String[] {arg};
  }

  public TestCommand(String command, String[] args) {
    this.command = command;
    this.args = args;
  }

  private String cat_args() {
    String a = "";
    for (String arg : this.args) a += String.format("%s ", arg);

    // NOTE: Never forget to trim paths again!!!!!
    return a.trim();
  }

  public void exec() {
    ArrayList<String> cmdline = new ArrayList<>(Arrays.asList(this.args));
    cmdline.add(0, PRG);

    System.out.printf("COMMAND: '%s', ARGS: '%s' \n", PRG, Arrays.toString(args));
    linebr();

    ProcessBuilder pb = new ProcessBuilder(cmdline);
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

  public String get_output() {
    return this.out;
  }

  public void assert_output(String expected) {
    String msg =
        String.format("Expected: \n<%s>\n", expected) + String.format("Got: \n<%s>\n", this.out);
    assertEquals(this.out, expected);
  }
}
