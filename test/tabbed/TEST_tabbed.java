package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_tabbed {
  private final String TABBED = "test/tabbed/tabbed.txt";

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
