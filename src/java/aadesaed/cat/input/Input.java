package aadesaed.cat.input;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

public class Input {
    public enum Type {
        Directory,
        File,
        Stdin,
        SymLink,
    };

    public static class Handle implements ReadableByteChannel {
        public ReadableByteChannel reader;
        public boolean isInteractive = false;

        public Handle(ReadableByteChannel reader, boolean isInteractive) {
            this.reader = reader;
            this.isInteractive = isInteractive;
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
}
