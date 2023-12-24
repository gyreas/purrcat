package aadesaed.cat.tests.thebustle;

import static aadesaed.cat.tests.TESTHelpers.*;
import static org.testng.Assert.*;

import aadesaed.cat.tests.TestCommand;
import java.io.IOException;
import org.testng.annotations.*;

public class TEST_thebustle {
  private final String BUSTLE = "thebustle/thebustle.txt";
  private String testdir = TESTDIR;

  @Test(groups = "File", enabled = false)
  public void works() throws IOException {
    String p = String.format("%s/%<s.txt", BUSTLE);
    String expected = readAsIs(p);

    TestCommand bustle = new TestCommand(PRG, p);

    bustle.exec();
    bustle.assert_output(expected);
  }

  @Test(groups = "File")
  public void givenRegularInput_whenNoOptions_thenNoExternalCharactersInOutput()
      throws IOException {
    String args = String.format("%s %s", BUSTLE, "" /* options */);
    String expected = readAsIs(args);

    TestCommand bustle = new TestCommand(PRG, args);

    bustle.exec();
    bustle.assert_output(expected);
  }
}
// #[test]
// fn bustle_nonblank() -> TestResult {
//     run(&["-b", BUSTLE], "tests/expected/the-bustle_b.out")
// }

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
