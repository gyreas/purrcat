package aadesaed.cat.app;

public class Output {
    public enum NumberingMode {
        None,
        All,
        NonBlank,
    }

    public static class State {
        public int lineNumber = 1;
        public boolean atLineStart = true;
        public boolean oneBlankKept = false;
        public boolean skippedCarriageReturn = false;
    }

    public static class Options {
        public NumberingMode number;
        public boolean squeezeBlankLines;
        public boolean showTabs;
        public boolean showEnds;
        public boolean showNonprint;

        public Options(
                NumberingMode number,
                boolean squeezeBlankLines,
                boolean showTabs,
                boolean showNonprint) {
            this.number = number;
            this.squeezeBlankLines = squeezeBlankLines;
            this.showTabs = showTabs;
            this.showNonprint = showNonprint;
        }

        public String tab() {
            if (this.showTabs) return "^I";
            else return "\t";
        }

        public String endOfLine() {
            if (this.showEnds) return "$\n";
            else return "\n";
        }
    }
}
