package rocks.friedrich.fragen;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class StapelKonvertierer {

  private static String gibDateiErweiterung(File file) {
    String name = file.getName();
    int lastIndexOf = name.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return ""; // empty extension
    }
    return name.substring(lastIndexOf + 1);
  }

  public static void main(String... args) throws IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    String wurzelname = classloader.getResource(".").getFile();
    System.out.println(wurzelname);
    Path path = Paths.get(wurzelname);
    System.out.println(path.toAbsolutePath());

    Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
      public FileVisitResult visitFile(Path quelle, BasicFileAttributes attrs) throws IOException {
        if (gibDateiErweiterung(quelle.toFile()).equals("txt")) {
          System.out.println(quelle);
          Path ziel = Paths.get(quelle.toString().replaceAll("ini", "xml"))
          System.out.println(ziel);
          Files.createDirectories(ziel.getParent());
        }
        return FileVisitResult.CONTINUE;
      }
    });
  }
}
