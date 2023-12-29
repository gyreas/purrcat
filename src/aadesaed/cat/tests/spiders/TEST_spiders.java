package aadesaed.cat.tests.spiders;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import java.io.IOException;
import org.testng.annotations.*;

public class TEST_spiders {
  private final String spiders = "spiders/spiders.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = false)
  public void works() throws IOException {
    run(PRG, spiders, "expected/spiders.out");
  }

  @Test(groups = "File", enabled = true)
  public void spiders_nonblank() {
    run(PRG, new String[] {"-b", spiders}, "expected/spiders_b.out");
  }

  @Test(groups = "File") // , enabled = true)
  public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
    run(PRG, new String[] {"-n", spiders}, "expected/spiders_n.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
    String args = String.format("%s %s", spiders, "" /* options */);
    String expected = readAsIs(args);

    TestCommand spiders = new TestCommand(PRG, args);

    spiders.exec();
    spiders.assert_output(expected);
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
