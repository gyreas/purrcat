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

  public static class Input_Is_Output extends Purrcat_Exception {
    public Input_Is_Output() {
      super("input file is output file");
    }
  }

  public static class Too_Many_Symlinks extends Purrcat_Exception {
    public Too_Many_Symlinks() {
      super("Too many levels of symbolic links");
    }
  }
}
