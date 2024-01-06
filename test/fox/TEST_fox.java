package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_fox {
  private final String FOX = "test/fox/fox.txt";

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run(PRG, FOX, "test/expected/fox.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenRegularFile_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumber() {
    run(PRG, new String[] {"-b", FOX}, "test/expected/fox_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", FOX}, "test/expected/fox_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", FOX}, "test/expected/fox_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", FOX}, "test/expected/fox_T.out");
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
