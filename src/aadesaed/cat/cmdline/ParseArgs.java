package aadesaed.cat.cmdline;
/*
 * Parse the arguments passed to the program.
 */
public class ParseArgs {
   public static Args parse_args(String[] argv) {
      Args args = new Args(argv);
      return args;
   }
}

class Args {
   private String first;

   Args(String[] argv) {
      first = argv[0];
   }
}
