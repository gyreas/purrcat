package aadesaed.cat;

import java.io.*;

public class TESTHelpers {
  // TODO: Implement this via JVM
  public static final String PRG = "./app.sh";

  public static String readAsIs(String filename) throws IOException {
    try {
      String fname_canon = filename.trim();
      byte[] content = new FileInputStream(fname_canon).readAllBytes();

      return new String(content);
    } catch (IOException e) {
      System.out.printf("Failed to read: '%s' %s\n", filename, e.getMessage());
      System.exit(1);
    }
    return null;
  }

  public static void run(String cmd, String arg, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, arg);
      tc.exec();
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  public static void run(String cmd, String[] args, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, args);
      tc.exec();
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  public static void run(String cmd, String arg, String[] inputs, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, arg);
      tc.exec(inputs);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  // single input source
  public static void run_Stdin(String cmd, String input, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, "");
      tc.exec_In(input);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  // single input source single arg
  public static void run_Stdin(String cmd, String arg, String input, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, arg);
      tc.exec_In(input);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  // single input source multiple args
  public static void run_Stdin(String cmd, String args[], String input, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, args);
      tc.exec_In(input);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
    ;
  }

  // multiple input sources multiple args
  public static void run_Stdin(String cmd, String arg, String[] inputs, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, arg);
      tc.exec_In(inputs);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  // multiple input sources multiple args
  public static void run_Stdin(String cmd, String[] args, String[] inputs, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, args);
      tc.exec_In(inputs);
      tc.assert_output(out);
    } catch (IOException x) {
      System.out.printf("Unable to read file: %s\n", x.getMessage());
    }
  }

  public static void gen_bad_file() {
    // while (true) {
    //     String filename = rand::thread_rng()
    //         .sample_iter(&Alphanumeric)
    //         .take(7)
    //         .map(char::from)
    //         .collect();

    //     if fs::metadata(&filename).is_err() {
    //         return filename;
    //     }
    // }
  }

  public static void skips_bad_file() {
    // let bad = gen_bad_file();
    // let expected = format!("{}: .* [(]os error 2[)]", bad);
    // Command::cargo_bin(PRG)
    //     .arg(&bad)
    //     .assert()
    //     .success()
    //     .stderr(predicate::str::is_match(expected));
  }

  public static void usage() {
    // for flag in &["-h", "--help"] {
    //     Command::cargo_bin(PRG)
    //         .arg(flag)
    //         .assert()
    //         .stdout(predicate::str::contains("USAGE"));
    // }
  }
}
