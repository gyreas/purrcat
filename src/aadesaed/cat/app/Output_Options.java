package aadesaed.cat.app;

public class Output_Options {
  public enum Numbering_Mode {
    None,
    All,
    Non_Blank,
  }

  public Numbering_Mode number;
  public boolean squeeze_Blank_Lines;
  public boolean show_Tabs;
  public boolean show_Ends;
  public boolean show_Nonprint;

  public Output_Options(
      Numbering_Mode number,
      boolean squeeze_Blank_Lines,
      boolean show_Tabs,
      boolean show_Nonprint) {
    this.number = number;
    this.squeeze_Blank_Lines = squeeze_Blank_Lines;
    this.show_Tabs = show_Tabs;
    this.show_Nonprint = show_Nonprint;
  }

  public String tab() {
    if (this.show_Tabs) return "^I";
    else return "\t";
  }

  public String end_Of_Line() {
    if (this.show_Ends) return "$\n";
    else return "\n";
  }
}
