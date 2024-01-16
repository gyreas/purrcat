package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;

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

    return a;
  }

  public void exec() {
    ArrayList<String> cmdline = new ArrayList<>();
    cmdline.add(PRG);
    cmdline.addAll(List.of(this.args));

    System.out.printf("[TEST] '%s'\n", Arrays.toString(args));

    ProcessBuilder pb = new ProcessBuilder(cmdline);
    pb.redirectErrorStream(true);
    try {
      Process process = pb.start();
      byte[] b = process.getInputStream().readAllBytes();
      this.out = new String(b);
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.toString());
    }
  }

  public void exec(String[] inputs) {
    ArrayList<String> cmdline = new ArrayList<>();
    cmdline.add(PRG);
    cmdline.addAll(List.of(this.args));
    cmdline.addAll(List.of(inputs));

    System.out.print("[TEST] '[");
      for (String arg : this.args) {
          System.out.printf("%s, ", arg);
      }
    for (int i = 0; i < inputs.length; i++) {
      System.out.printf("%s%s", inputs[i], i == inputs.length - 1 ? "]'\n" : ", ");
    }

    ProcessBuilder pb = new ProcessBuilder(cmdline);
    pb.redirectErrorStream(true);
    try {
      Process process = pb.start();
      byte[] b = process.getInputStream().readAllBytes();
      this.out = new String(b);
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.toString());
    }
  }

  public void exec_In(String input) {
    ArrayList<String> cmdline = new ArrayList<>();
    cmdline.add(PRG);
    if (!Arrays.equals(this.args, new String[] {""})) cmdline.addAll(List.of(this.args));

    System.out.print("[TEST] (stdin) '[");
    for (int i = 0; i < this.args.length; i++) {
      System.out.printf("%s%s", this.args[i], i == this.args.length - 1 ? "]'\n" : ", ");
    }

    ProcessBuilder pb = new ProcessBuilder(cmdline);
    pb.redirectErrorStream(true);
    pb.redirectInput(new File(input));
    try {
      Process process = pb.start();
      byte[] b = process.getInputStream().readAllBytes();
      this.out = new String(b);
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.toString());
    }
  }

  public void exec_In(String[] inputs) {
    ArrayList<String> cmdline = new ArrayList<>(Arrays.asList(this.args));
    cmdline.add(0, PRG);

    System.out.print("[TEST] (stdin) '[");
    for (String a : this.args) {
      System.out.printf("%s, ", a);
    }
    for (int i = 0; i < inputs.length; i++) {
      System.out.printf("%s%s", inputs[i], i == inputs.length - 1 ? "]'\n" : ", ");
    }

    ProcessBuilder pb = new ProcessBuilder(cmdline);
    pb.redirectErrorStream(true);
    try {
      Process process = pb.start();
      for (String input : inputs) {
        process.getOutputStream().write(Files.readAllBytes(java.nio.file.Paths.get(input)));
        process.getOutputStream().flush();
      }
      process.getOutputStream().close();
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
    Assert.assertEquals(this.out, expected, msg);
  }
}
