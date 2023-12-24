package aadesaed.cat.tests.fox;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import aadesaed.cat.tests.TestCommand;
import java.io.IOException;
import org.testng.annotations.*;

public class TEST_fox {
  private final String fox = "fox/fox.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = false)
  public void works() throws IOException {
    run(PRG, fox, "expected/fox.out");
  }

  @Test(groups = "File", enabled = true)
  public void fox_nonblank() {
    run(PRG, new String[] {"-b", fox}, "expected/fox_b.out");
  }

  @Test(groups = "File") // , enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", fox}, "expected/fox_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    String args = String.format("%s %s", fox, "" /* options */);
    String expected = readAsIs(args);

    TestCommand fox = new TestCommand(PRG, args);

    fox.exec();
    fox.assert_output(expected);
  }
}
// #[test]
// fn fox_number() -> TestResult {
//    run_stdin(
//         fox,
//         &["-", "-E"],
//         "tests/expected/the-fox_stdin_E.out",
//     )
// }
// #[test]
// fn fox_stdin_nonblank() -> TestResult {
//     run_stdin(
//         fox,
//         &["-b", "-"],
//         "tests/expected/the-fox_stdin_b.out",
//     )
// }

// #[test]
// fn fox_stdin_nonblank_showends() -> TestResult {
//     run_stdin(
//         fox,
//         &["-b", "-E", "-"],
//         "tests/expected/the-fox_stdin_bE.out",
//     )
// }

// #[test]
// fn fox_stdin_number() -> TestResult {
//     run_stdin(
//         fox,
//         &["-n", "-"],
//         "tests/expected/the-fox_stdin_n.out",
//     )
// }

// #[test]
// fn fox_stdin_number_showends() -> TestResult {
//     run_stdin(
//         fox,
//         &["-n", "-E", "-"],
//         "tests/expected/the-fox_stdin_nE.out",
//     )
// }
