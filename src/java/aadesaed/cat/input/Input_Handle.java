package aadesaed.cat.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class Input_Handle implements ReadableByteChannel {
  public ReadableByteChannel reader;
  public boolean is_Interactive = false;

  public Input_Handle(ReadableByteChannel reader, boolean is_Interactive) {
    this.reader = reader;
    this.is_Interactive = is_Interactive;
  }

  public void close() throws IOException {
    this.reader.close();
  }

  public boolean isOpen() {
    return this.reader.isOpen();
  }

  public int read(ByteBuffer buf) throws IOException {
    return this.reader.read(buf);
  }
}
