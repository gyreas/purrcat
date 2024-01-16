package aadesaed.cat.input;

import static org.testng.Assert.assertNotSame;

import aadesaed.cat.app.Output_Options;
import aadesaed.cat.app.Output_Options.Numbering_Mode;
import aadesaed.cat.app.Output_State;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import org.testng.annotations.Test;

public class Utils {

  public static void cat_Handle(Input_Handle handle, Output_State state, Output_Options options)
      throws IOException {
    write_Lines(handle, state, options);
  }

  public static void cat_Path(String path, Output_State state, Output_Options options)
      throws IOException {
    if (get_Input_Type(path) == Input_Type.Std_In) {
      ReadableByteChannel stdin = Channels.newChannel(System.in);
      Input_Handle handle = new Input_Handle(stdin, false);
      cat_Handle(handle, state, options);
    } else {
      FileChannel file = new FileInputStream(path).getChannel();
      Input_Handle handle = new Input_Handle(file, false);
      cat_Handle(handle, state, options);
    }
  }

  public static Input_Type get_Input_Type(String path) {
    return path.equals("-") ? Input_Type.Std_In : Input_Type.File;
  }

  public static void write_Lines(Input_Handle handle, Output_State state, Output_Options options)
      throws IOException {
    int buf_Size = 1024 * 31; // TODO: 31kb? increase it mate!
    PrintStream out = System.out;
    WritableByteChannel writer = Channels.newChannel(out);
    ByteBuffer in_Buf = ByteBuffer.allocate(buf_Size);

    // each read
    int amt_Read = handle.read(in_Buf);
    while (amt_Read != -1) {
      // go through the buffer
      int pos = 0; // position of the current char
      while (pos < amt_Read) {
        byte b = in_Buf.get(pos);
        // skip empty line_Number, enumerating them if possible
        if (state.skipped_Carriage_Return) {
          out.write('\r');
          state.skipped_Carriage_Return = false;
          state.at_Line_Start = false;
        }

        if (b == '\n') {
          write_Newline(out, state, options);
          pos++;
          state.at_Line_Start = true;
          continue;
        }

        state.one_Blank_Kept = false;
        if (state.at_Line_Start && options.number != Numbering_Mode.None) {
          write_Line_Number(out, state.line_Number);
          state.line_Number++;
        }

        // dump everything else
        int len = amt_Read - pos;
        // slicing is relative to zero index of the buffer, not relative to the current
        // position
        int offset = write_End(writer, in_Buf.slice(pos, len), options);
        // end of buffer?
        if (pos + offset == amt_Read) {
          state.at_Line_Start = false;
          break;
        }
        if (in_Buf.get(pos + offset) == '\r') {
          state.skipped_Carriage_Return = true;
        } else {
          write_End_Of_Line(out, options.end_Of_Line().getBytes());
          state.at_Line_Start = true;
        }
        pos += offset + 1;
      }
      in_Buf.clear();
      amt_Read = handle.read(in_Buf);
      out.flush();
    }
    handle.close();
  }

  public static void write_End_Of_Line(PrintStream writer, byte[] end_Of_Line) throws IOException {
    writer.write(end_Of_Line);
  }

  public static int write_End(WritableByteChannel writer, ByteBuffer buf, Output_Options options)
      throws IOException {
    int amt;
    if (options.show_Nonprint) {
      amt = write_Nonprint_To_End(buf, options.tab().getBytes());
    } else if (options.show_Tabs) {
      amt = write_Tab_To_End(writer, buf);
    } else {
      amt = write_To_End(writer, buf);
    }
    return amt;
  }

  public static int write_To_End(WritableByteChannel writer, ByteBuffer buf) throws IOException {
    int len = buf.remaining();
    for (int i = 0; i < len; i++) {
      byte b = buf.get(i);
      if (b == '\n' || b == '\r') {
        writer.write(buf.slice(0, i));
        return i;
      }
    }
    writer.write(buf.slice(0, len));
    return len;
  }

  public static int write_Nonprint_To_End(ByteBuffer buf, byte[] tab) throws IOException {
    PrintStream writer = System.out;
    int count = 0;
    int len = buf.remaining();
    for (int i = 0; i < len; ++i) {
      int b = Byte.toUnsignedInt(buf.get(i));
      if (b == (byte) '\n') break;
      if (b == 9) writer.write(tab);
      else if ((0 < b && b <= 8) || (10 <= b && b <= 31))
        writer.write(new byte[] {'^', (byte) (b + 64)});
      else if (32 <= b && b <= 126) {
        writer.write(new byte[] {(byte) b});
      } else if (b == 127) writer.write(new byte[] {'^', '?'});
      else if (128 <= (int) b && (int) b <= 159) {
        writer.write(new byte[] {'M', '-', '^', (byte) (b - 64)});
      } else if (160 <= (int) b && (int) b <= 254) {
        writer.write(new byte[] {'M', '-', (byte) (b - 128)});
      } else writer.write(new byte[] {'M', '-', '^', '?'});

      count++;
    }
    return count;
  }

  public static void write_Newline(PrintStream writer, Output_State state, Output_Options options)
      throws IOException {
    if (state.skipped_Carriage_Return && options.show_Ends) {
      writer.write("^M".getBytes());
      state.skipped_Carriage_Return = false;
    }

    if (!state.at_Line_Start || !options.squeeze_Blank_Lines || !state.one_Blank_Kept) {
      state.one_Blank_Kept = true;
      if (state.at_Line_Start && options.number == Numbering_Mode.All) {
        write_Line_Number(writer, state.line_Number);
        state.line_Number++;
      }
      writer.write(options.end_Of_Line().getBytes());
    }
  }

  public static void write_Line_Number(PrintStream writer, int lineno) throws IOException {
    String line = String.format("%6d\t", lineno);
    writer.write(line.getBytes());
  }

  public static int write_Tab_To_End(WritableByteChannel writer, ByteBuffer in_Buf)
      throws IOException {
    int count = 0;
    int len = in_Buf.remaining();
    while (true) {
      int pos = find_Tab_In_Buffer(in_Buf);
      if (pos != -1) {
        byte b = in_Buf.get(pos);
        writer.write(in_Buf.slice(0, pos));
        if (b == '\t') {
          System.out.write("^I".getBytes());
          count += pos + 1;
        } else {
          // 10 ('\n') or 13 ('\r')
          return count + pos;
        }
        // Update in_Buf to the remaining part
        len -= pos + 1;
        in_Buf = in_Buf.slice(pos + 1, len);
      } else {
        // No newline or tab found, write the entire buffer
        writer.write(in_Buf);
        return len;
      }
    }
  }

  private static int find_Tab_In_Buffer(ByteBuffer buffer) {
    int len = buffer.remaining();
    for (int i = 0; i < len; i++) {
      byte b = buffer.get(i);
      if (b == '\n' || b == '\t' || b == '\r') {
        return i;
      }
    }
    return -1;
  }

  @Test
  public void can_Find_Tab_In_Buffer() {
    System.out.println("[TEST] [ Can find tab in buffer ]");
    ByteBuffer buf = ByteBuffer.wrap("A\ttab".getBytes());
    assertNotSame(find_Tab_In_Buffer(buf), -1, "There's a tab in there.");
  }
}