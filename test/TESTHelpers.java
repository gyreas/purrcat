package aadesaed.cat;

import java.io.*;

public class TESTHelpers {
  public static final String PRG = "./app.sh";

  public final String EMPTY = "empty.txt";
  public final String FOX = "fox.txt";
  public final String SPIDERS = "spiders.txt";
  public final String THREE = "three.txt";

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

  public static void run(String cmd, String args, String expected) {
    try {
      String out = readAsIs(expected);
      TestCommand tc = new TestCommand(cmd, args);
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

  public static void run_stdin(String input_file, String[] args, String expected_file) {
    // new TestCommand(blah).exec();

    // let input = fs::read_to_string(input_file);
    // let expected = fs::read_to_string(expected_file);
    // Command::cargo_bin(PRG)
    //     .args(args)
    //     .write_stdin(input)
    //     .assert()
    //     .success()
    //     .stdout(expected);

  }

  public static void usage() {
    // for flag in &["-h", "--help"] {
    //     Command::cargo_bin(PRG)
    //         .arg(flag)
    //         .assert()
    //         .stdout(predicate::str::contains("USAGE"));
    // }
  }

  public static void linebr() {
    char ch = '-';
    int n = 15;
    System.out.printf("%s\n", Character.toString(ch).repeat(n));
  }

  public static void linebr(char ch, int n) {
    System.out.printf("%s\n", Character.toString(ch).repeat(n));
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
}
