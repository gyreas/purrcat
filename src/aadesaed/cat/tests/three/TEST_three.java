package aadesaed.cat.tests.three;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_three {
  private final String three = "three/three.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = false)
  public void works() throws IOException {
    run(PRG, three, "expected/three.out");
  }

  @Test(groups = "File", enabled = true)
  public void three_nonblank() {
    run(PRG, new String[] {"-b", three}, "expected/three_b.out");
  }

  @Test(groups = "File") // , enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", three}, "expected/three_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    String args = String.format("%s %s", three, "" /* options */);
    String expected = readAsIs(args);

    TestCommand three = new TestCommand(PRG, args);

    three.exec();
    three.assert_output(expected);
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
