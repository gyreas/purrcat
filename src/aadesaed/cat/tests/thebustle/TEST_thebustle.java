package aadesaed.cat.tests.thebustle;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_thebustle {
  private final String BUSTLE = "thebustle/thebustle.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run(PRG, BUSTLE, "expected/thebustle.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumbers() {
    run(PRG, new String[] {"-b", BUSTLE}, "expected/thebustle_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", BUSTLE}, "expected/thebustle_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", BUSTLE}, "expected/thebustle_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretM() {
    run(PRG, new String[] {"-T", BUSTLE}, "expected/thebustle_T.out");
  }
}
// #[test]
// fn bustle_number() -> TestResult {
//    run_stdin(
//         BUSTLE,
//         &["-", "-E"],
//         "tests/expected/the-bustle_stdin_E.out",
//     )
// }
// #[test]
// fn bustle_stdin_nonblank() -> TestResult {
//     run_stdin(
//         BUSTLE,
//         &["-b", "-"],
//         "tests/expected/the-bustle_stdin_b.out",
//     )
// }

// #[test]
// fn bustle_stdin_nonblank_showends() -> TestResult {
//     run_stdin(
//         BUSTLE,
//         &["-b", "-E", "-"],
//         "tests/expected/the-bustle_stdin_bE.out",
//     )
// }

// #[test]
// fn bustle_stdin_number() -> TestResult {
//     run_stdin(
//         BUSTLE,
//         &["-n", "-"],
//         "tests/expected/the-bustle_stdin_n.out",
//     )
// }

// #[test]
// fn bustle_stdin_number_showends() -> TestResult {
//     run_stdin(
//         BUSTLE,
//         &["-n", "-E", "-"],
//         "tests/expected/the-bustle_stdin_nE.out",
//     )
// }
