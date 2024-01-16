package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_tabbed {
  private final String TABBED = "test/tabbed/tabbed.txt";

  @BeforeClass(groups = {"File", "Stdin"})
  public void announce() {
    System.out.println("[TEST]");
    System.out.println("[TEST] ------< tabbed >------");
  }

  @AfterClass(groups = {"File", "Stdin"})
  public void line_Break() {
    System.out.println("[TEST]");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithTabs_whenNoOptions_thenNoExternalCharactersInOutput()
      throws IOException {
    run(PRG, TABBED, "test/expected/tabbed.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithTabs_whenNumberOnlyNonblankLines_thenNumberOnlyNonblankLines() {
    run(PRG, new String[] {"-b", TABBED}, "test/expected/tabbed_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithTabs_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", TABBED}, "test/expected/tabbed_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithTabs_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", TABBED}, "test/expected/tabbed_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithTabs_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", TABBED}, "test/expected/tabbed_T.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run_Stdin(PRG, TABBED, "test/expected/tabbed_stdin.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumber() {
    run_Stdin(PRG, "-b", TABBED, "test/expected/tabbed_stdin_b.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run_Stdin(PRG, "-n", TABBED, "test/expected/tabbed_stdin_n.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run_Stdin(PRG, "-s", TABBED, "test/expected/tabbed_stdin_s.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run_Stdin(PRG, "-T", TABBED, "test/expected/tabbed_stdin_T.out");
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
