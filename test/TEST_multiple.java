package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;

import static org.testng.Assert.*;

import org.testng.annotations.*;

public class TEST_multiple {
    @BeforeClass(groups = {"File", "Stdin"})
    public void announce() {
        System.out.println("[TEST]");
        System.out.println("[TEST] ------< multiple >------");
    }

    @AfterClass(groups = {"File", "Stdin"})
    public void line_Break() {
        System.out.println("[TEST]");
        System.out.println("[TEST]");
    }

    String[] MULTIPLE =
            new String[] {
                "test/dummy/dummy.txt",
                "test/empty/empty.txt",
                "test/tabbed/tabbed.txt",
                "test/three/three.txt",
            };

    @Test(groups = "File", enabled = true)
    public void
            givenMultipleFiles_whenShowLineNumberNonblankLines_thenNumberEachNonblankLineConsecutively() {
        run(PRG, "-b", MULTIPLE, "test/expected/multiple_b.out");
    }

    @Test(groups = "File", enabled = true)
    public void givenMultipleFiles_whenShowLineNumber_thenNumberEachLineConsecutively() {
        run(PRG, "-v", MULTIPLE, "test/expected/multiple_v.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void
            givenMultipleFilesOnStdin_whenShowLineNumberNonblankLines_thenNumberEachNonblankLineConsecutively() {
        run_Stdin(PRG, "-b", MULTIPLE, "test/expected/multiple_stdin_b.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void
            givenMultipleFilesOnStdin_whenShowNonprinting_thenAllNonprintingCharsBecomePrintingChars() {
        run_Stdin(PRG, "-v", MULTIPLE, "test/expected/multiple_stdin_v.out");
    }

    @Test(groups = "Stdin", enabled = true)
    public void givenMultipleFilesOnStdin_whenShowTabs_thenAllNonprintingTabsBecomeCaretI() {
        run_Stdin(PRG, "-T", MULTIPLE, "test/expected/multiple_stdin_T.out");
    }
}
