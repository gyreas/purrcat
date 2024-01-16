package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_three {
  private final String THREE = "test/three/three.txt";

  @BeforeClass(groups = {"File", "Stdin"})
  public void announce() {
    System.out.println("[TEST]");
    System.out.println("[TEST] ------< three >------");
  }

  @AfterClass(groups = {"File", "Stdin"})
  public void line_Break() {
    System.out.println("[TEST]");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFileWithDifferentEOLs_whenNoOptions_thenPreserveEOLs() throws IOException {
    run(PRG, THREE, "test/expected/three.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumbers() {
    run(PRG, new String[] {"-b", THREE}, "test/expected/three_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void
      givenAFileWithDifferentEOLs_whenShowLineNumber_thenPrependLinesWithLineNumbersPreserveEOLs() {
    run(PRG, new String[] {"-n", THREE}, "test/expected/three_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run(PRG, new String[] {"-s", THREE}, "test/expected/three_s.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowNonprintingChars_thenAllNonprintingCharsShown() {
    run(PRG, new String[] {"-v", THREE}, "test/expected/three_v.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run(PRG, new String[] {"-T", THREE}, "test/expected/three_T.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    run_Stdin(PRG, THREE, "test/expected/three_stdin.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumber() {
    run_Stdin(PRG, "-b", THREE, "test/expected/three_stdin_b.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run_Stdin(PRG, "-n", THREE, "test/expected/three_stdin_n.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
    run_Stdin(PRG, "-s", THREE, "test/expected/three_stdin_s.out");
  }

  @Test(groups = "Stdin", enabled = true)
  public void givenStdin_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
    run_Stdin(PRG, "-T", THREE, "test/expected/three_stdin_T.out");
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
