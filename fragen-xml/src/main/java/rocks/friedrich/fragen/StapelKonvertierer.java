package rocks.friedrich.fragen;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class StapelKonvertierer {
  Path quelle;

  public StapelKonvertierer (Path quelle) {
    this.quelle = quelle;
  }

  public void konvertiere () throws IOException {
    Files.walkFileTree(quelle, new SimpleFileVisitor<Path>() {
      public FileVisitResult visitFile(Path quelle, BasicFileAttributes attrs) throws IOException {
        Path ziel = Paths.get(quelle.toString().replaceAll("ini", "xml").replaceAll("\\.txt", ".xml"));
        konvertiereEineDatei(quelle, ziel);
        return FileVisitResult.CONTINUE;
      }
    });
  }

  private void konvertiereEineDatei (Path quelle, Path ziel) throws IOException {
    if (gibDateiErweiterung(quelle.toFile()).equals("txt")) {
      System.out.println(quelle);
      System.out.println(ziel);
      Files.createDirectories(ziel.getParent());
    }
  }

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
    String quelle = classloader.getResource(".").getFile();

    StapelKonvertierer konvertierer = new StapelKonvertierer(Paths.get(quelle));
    konvertierer.konvertiere();

  }
}
