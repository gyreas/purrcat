package aadesaed.cat.tests.empty;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_empty {
  private final String empty = "empty/empty.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = false)
  public void works() throws IOException {
    run(PRG, empty, "expected/empty.out");
  }

  @Test(groups = "File", enabled = true)
  public void empty_nonblank() {
    run(PRG, new String[] {"-b", empty}, "expected/empty_b.out");
  }

  @Test(groups = "File") // , enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", empty}, "expected/empty_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    String args = String.format("%s %s", empty, "" /* options */);
    String expected = readAsIs(args);

    TestCommand empty = new TestCommand(PRG, args);

    empty.exec();
    empty.assert_output(expected);
  }
}
// #[test]
// fn empty_number() -> TestResult {
//    run_stdin(
//         empty,
//         &["-", "-E"],
//         "tests/expected/the-empty_stdin_E.out",
//     )
// }
// #[test]
// fn empty_stdin_nonblank() -> TestResult {
//     run_stdin(
//         empty,
//         &["-b", "-"],
//         "tests/expected/the-empty_stdin_b.out",
//     )
// }

// #[test]
// fn empty_stdin_nonblank_showends() -> TestResult {
//     run_stdin(
//         empty,
//         &["-b", "-E", "-"],
//         "tests/expected/the-empty_stdin_bE.out",
//     )
// }

// #[test]
// fn empty_stdin_number() -> TestResult {
//     run_stdin(
//         empty,
//         &["-n", "-"],
//         "tests/expected/the-empty_stdin_n.out",
//     )
// }

// #[test]
// fn empty_stdin_number_showends() -> TestResult {
//     run_stdin(
//         empty,
//         &["-n", "-E", "-"],
//         "tests/expected/the-empty_stdin_nE.out",
//     )
// }
