package aadesaed.cat;

import static aadesaed.cat.TESTHelpers.*;
import static org.testng.Assert.*;

import org.testng.annotations.*;

public class TEST_multiple {
  String[] args =
      new String[] {
        null, "test/thebustle/thebustle.txt", "test/fox/fox.txt", "test/three/three.txt",
      };

  @Test(groups = "File", enabled = true)
  public void givenMultipleFiles_whenShowLineNumberNonblankLines_thenNumberEachNonblankLineConsecutively() {
    args[0] = "-b";
    run(PRG, args, "test/expected/multiple_b.out");
  }

  @Test(groups = "File", enabled = true)
  public void givenMultipleFiles_whenShowLineNumber_thenNumberEachLineConsecutively() {
    args[0] = "-n";
    run(PRG, args, "test/expected/multiple_n.out");
  }
}
