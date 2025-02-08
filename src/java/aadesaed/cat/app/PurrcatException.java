package aadesaed.cat.app;

public class PurrcatException extends Exception {
    public PurrcatException() {}

    public PurrcatException(String msg) {
        super(msg);
    }

    public static class UnknownFileType extends PurrcatException {
        public UnknownFileType(String file_Type) {
            super(file_Type);
        }
    }

    public static class IsDirectory extends PurrcatException {
        public IsDirectory(String dir) {
            super(dir);
        }
    }

    public static class InputIsOutput extends PurrcatException {
        public InputIsOutput() {
            super("input file is output file");
        }
    }

    public static class TooManySymlinks extends PurrcatException {
        public TooManySymlinks() {
            super("Too many levels of symbolic links");
        }
    }
}
