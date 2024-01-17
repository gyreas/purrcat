package aadesaed.cat.app;

public class Purrcat_Exception extends Exception {
  public Purrcat_Exception() {}

  public Purrcat_Exception(String msg) {
    super(msg);
  }

  public static class Unknown_File_Type extends Purrcat_Exception {
    public Unknown_File_Type(String file_Type) {
      super(file_Type);
    }
  }

  public static class Is_Directory extends Purrcat_Exception {
    public Is_Directory(String dir) {
      super(dir);
    }
  }

  public static class Output_Is_Input extends Purrcat_Exception {
    public Output_Is_Input() {
      super("input file is output file");
    }
  }
}
