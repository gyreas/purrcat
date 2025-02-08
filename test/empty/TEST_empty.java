package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;

import static org.testng.Assert.*;

import org.testng.annotations.*;

import java.io.IOException;

public class TEST_empty {
    private final String EMPTY = "test/empty/empty.txt";

    @BeforeClass(groups = {"File", "Stdin"})
    public void announce() {
        System.out.println("[TEST]");
        System.out.println("[TEST] ------< empty >------");
    }

    @AfterClass(groups = {"File", "Stdin"})
    public void line_Break() {
        System.out.println("[TEST]");
    }

    @Test(groups = "File", enabled = true)
    public void givenAFile_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
        run(PRG, EMPTY, "test/expected/empty.out");
    }

    @Test(groups = "File", enabled = true)
    public void givenEmptyFile_whenNumberOnlyNonblankLines_thenFileRemainsEmpty() {
        run(PRG, new String[] {"-b", EMPTY}, "test/expected/empty_b.out");
    }

    @Test(groups = "File", enabled = true)
    public void givenAFile_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
        run(PRG, new String[] {"-n", EMPTY}, "test/expected/empty_n.out");
    }

    @Test(groups = "File", enabled = true)
    public void givenAFile_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
        run(PRG, new String[] {"-s", EMPTY}, "test/expected/empty_s.out");
    }

    @Test(groups = "File", enabled = true)
    public void givenAFile_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
        run(PRG, new String[] {"-T", EMPTY}, "test/expected/empty_T.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenStdin_whenNoOptions_thenNoExternalCharactersInOutput() throws IOException {
        run_Stdin(PRG, EMPTY, "test/expected/empty_stdin.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenStdin_whenNumberOnlyNonblankLines_thenPrependNonblankLinesWithLineNumber() {
        run_Stdin(PRG, "-b", EMPTY, "test/expected/empty_stdin_b.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenStdin_whenShowLineNumber_thenPrependLinesWithLineNumbers() {
        run_Stdin(PRG, "-n", EMPTY, "test/expected/empty_stdin_n.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenStdin_whenSqueezeBlankLine_thenConsecutiveBlankLinesBecomeSingleLine() {
        run_Stdin(PRG, "-s", EMPTY, "test/expected/empty_stdin_s.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenStdin_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
        run_Stdin(PRG, "-T", EMPTY, "test/expected/empty_stdin_T.out");
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
