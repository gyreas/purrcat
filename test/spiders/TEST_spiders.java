package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_spiders {
  private final String SPIDERS = "test/spiders/spiders.txt";

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run(PRG, SPIDERS, "test/expected/spiders.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumbers() {
    run(PRG, new String[] {"-b", SPIDERS}, "test/expected/spiders_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", SPIDERS}, "test/expected/spiders_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", SPIDERS}, "test/expected/spiders_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", SPIDERS}, "test/expected/spiders_T.out");
  }
}
// #[test]
// fn spiders_number() -> TestResult {
//    run_stdin(
//         spiders,
//         &["-", "-E"],
//         "tests/expected/the-spiders_stdin_E.out",
//     )
// }
// #[test]
// fn spiders_stdin_nonblank() -> TestResult {
//     run_stdin(
//         spiders,
//         &["-b", "-"],
//         "tests/expected/the-spiders_stdin_b.out",
//     )
// }

// #[test]
// fn spiders_stdin_nonblank_showends() -> TestResult {
//     run_stdin(
//         spiders,
//         &["-b", "-E", "-"],
//         "tests/expected/the-spiders_stdin_bE.out",
//     )
// }

// #[test]
// fn spiders_stdin_number() -> TestResult {
//     run_stdin(
//         spiders,
//         &["-n", "-"],
//         "tests/expected/the-spiders_stdin_n.out",
//     )
// }

// #[test]
// fn spiders_stdin_number_showends() -> TestResult {
//     run_stdin(
//         spiders,
//         &["-n", "-E", "-"],
//         "tests/expected/the-spiders_stdin_nE.out",
//     )
// }
