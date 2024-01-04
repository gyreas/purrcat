package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_three {
  private final String THREE = "three/three.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = true)
  public void givenAFileWithDifferentEOLs_whenNoOptions_thenPreserveEOLs() throws IOException {
    run(PRG, THREE, "expected/three.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumbers() {
    run(PRG, new String[] {"-b", THREE}, "expected/three_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenShowLineNumber_thenPrependLinesWithLineNumbersPreserveEOLs() {
    run(PRG, new String[] {"-n", THREE}, "expected/three_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", THREE}, "expected/three_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", THREE}, "expected/three_T.out");
  }
}
// #[test]
// fn three_number() -> TestResult {
//    run_stdin(
//         three,
//         &["-", "-E"],
//         "tests/expected/the-three_stdin_E.out",
//     )
// }
// #[test]
// fn three_stdin_nonblank() -> TestResult {
//     run_stdin(
//         three,
//         &["-b", "-"],
//         "tests/expected/the-three_stdin_b.out",
//     )
// }

// #[test]
// fn three_stdin_nonblank_showends() -> TestResult {
//     run_stdin(
//         three,
//         &["-b", "-E", "-"],
//         "tests/expected/the-three_stdin_bE.out",
//     )
// }

// #[test]
// fn three_stdin_number() -> TestResult {
//     run_stdin(
//         three,
//         &["-n", "-"],
//         "tests/expected/the-three_stdin_n.out",
//     )
// }

// #[test]
// fn three_stdin_number_showends() -> TestResult {
//     run_stdin(
//         three,
//         &["-n", "-E", "-"],
//         "tests/expected/the-three_stdin_nE.out",
//     )
// }
