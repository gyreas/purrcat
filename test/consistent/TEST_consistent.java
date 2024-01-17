package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import org.testng.annotations.*;

public class TEST_consistent {
  private final String CONSISTENT = "test/consistent/consistent.txt";

  @BeforeClass(groups = {"File", "Stdin"})
  public void announce() {
    System.out.println("[TEST]");
    System.out.println("[TEST] ------< consistent >------");
  }

  @AfterClass(groups = {"File", "Stdin"})
  public void line_Break() {
    System.out.println("[TEST]");
    System.out.println("[TEST]");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() {
    run(PRG, CONSISTENT, "test/expected/consistent.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNumberOnlyNonblankLines_thenNumberOnlyNonblankLines() {
    run(PRG, new String[] {"-b", CONSISTENT}, "test/expected/consistent_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", CONSISTENT}, "test/expected/consistent_n.out");
  }

  @Test(groups = "File", enabled = false)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", CONSISTENT}, "test/expected/consistent_s.out");
  }

  @Test(groups = "File", enabled = false)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", CONSISTENT}, "test/expected/consistent_T.out");
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
