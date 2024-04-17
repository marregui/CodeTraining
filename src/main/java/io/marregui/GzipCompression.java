package io.marregui;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static java.util.zip.Deflater.DEFAULT_COMPRESSION;


public class GzipCompression {

  public static class GzipCompressor implements Closeable {

    private final Deflater gzipCompressor = new Deflater(DEFAULT_COMPRESSION, true);

    public int compress(ByteBuffer inUncompressed, ByteBuffer outCompressed)
        throws IOException {
      gzipCompressor.reset();
      gzipCompressor.setInput(inUncompressed);
      gzipCompressor.finish();
      gzipCompressor.deflate(outCompressed);
      outCompressed.flip();
      return outCompressed.limit();
    }

    public int maxCompressedSize(int uncompressedSize) {
      // https://github.com/luvit/zlib/blob/8de57bce969eb9dafc1f1f5c256ac608d0a73ec4/compress.c#L75
      return uncompressedSize + (uncompressedSize >> 12) + (uncompressedSize >> 14) + (uncompressedSize >> 25) + 13;
    }

    @Override
    public void close()
        throws IOException {
      gzipCompressor.end();
    }
  }

  public static class GzipDecompressor implements Closeable {
    private final Inflater gzipDecompressor = new Inflater();

    public int decompress(ByteBuffer compressedInput, ByteBuffer decompressedOutput)
        throws IOException {
      gzipDecompressor.reset();
      gzipDecompressor.setInput(compressedInput);
      try {
        gzipDecompressor.inflate(decompressedOutput);
      } catch (DataFormatException e) {
        throw new IOException(e);
      }
      decompressedOutput.flip();
      return decompressedOutput.limit();
    }

    public int decompressedLength(ByteBuffer compressedInput) {
      int idx = compressedInput.limit() - 4;
      System.out.printf("IDX: %d%n", idx);
      if (idx > 0) {
        int b3 = compressedInput.get(idx++);
        int b2 = compressedInput.get(idx++);
        int b1 = compressedInput.get(idx++);
        int b0 = compressedInput.get(idx);
        int val = (b3 << 24) | (b2 << 16) | (b1 << 8) | b0;
        return val;
      }




      return -1;
    }

    @Override
    public void close()
        throws IOException {
      gzipDecompressor.end();
    }
  }

  static ByteBuffer readResource(String resource)
      throws IOException {
    URL url = GzipCompression.class.getResource(resource);
    ByteBuffer buff;
    try (RandomAccessFile aFile = new RandomAccessFile(url.getFile(), "r");
        FileChannel inChannel = aFile.getChannel()) {
      buff = ByteBuffer.allocate((int) inChannel.size());
      inChannel.read(buff);
      buff.flip();
    }
    return buff;
  }

  static ByteBuffer readFile(String file)
      throws IOException {
    ByteBuffer buff;
    try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
        FileChannel inChannel = aFile.getChannel()) {
      buff = ByteBuffer.allocate((int) inChannel.size());
      inChannel.read(buff);
      buff.flip();
    }
    return buff;
  }

  public static void main(String[] args)
      throws Exception {
    ByteBuffer orig = readResource("/gzip.txt");
    System.out.printf("Read %d bytes%n", orig.limit());

    try (GzipCompressor c = new GzipCompressor()) {
      int maxSize = c.maxCompressedSize(orig.limit());
      System.out.printf("Max %d bytes%n", maxSize);
      ByteBuffer out = ByteBuffer.allocate(maxSize);
      int compressed = c.compress(orig, out);
      System.out.printf("Compressed %d bytes%n", compressed);
      try (FileOutputStream fos = new FileOutputStream("compressed.gzip"); FileChannel oChan = fos.getChannel()) {
        oChan.write(out);
      }
    }

    ByteBuffer comp = readFile("compressed.gzip");
    System.out.printf("Read %d bytes%n", comp.limit());

    try (GzipDecompressor d = new GzipDecompressor()) {
      int l = d.decompressedLength(comp);
      System.out.printf("PO %d bytes%n", l);

    }

  }
}
