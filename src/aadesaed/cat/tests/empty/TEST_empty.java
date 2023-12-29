package aadesaed.cat.tests.empty;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_empty {
  private final String EMPTY = "empty/empty.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run(PRG, EMPTY, "expected/empty_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenEmptyFile_whenNumberOnlyNonblankLines_thenFileRemainsEmpty() {
    run(PRG, new String[] {"-b", EMPTY}, "expected/empty_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", EMPTY}, "expected/empty_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", EMPTY}, "expected/empty_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretM() {
    run(PRG, new String[] {"-T", EMPTY}, "expected/empty_T.out");
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
