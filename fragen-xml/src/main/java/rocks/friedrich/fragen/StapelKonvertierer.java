package rocks.friedrich.fragen;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class StapelKonvertierer {
  public static void main(String... args) throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    String wurzelname = classloader.getResource(".").getFile();
    System.out.println(wurzelname);
    Path path = Paths.get(wurzelname);
    System.out.println(path.toAbsolutePath());

    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file);
        return FileVisitResult.CONTINUE;
      }
    });
  }
}
