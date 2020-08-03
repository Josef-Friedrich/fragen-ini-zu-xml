package rocks.friedrich.fragen;

import java.io.IOException;
import java.nio.file.Paths;

public class Haupt {

  public static void main(String[] args) throws IOException {

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    String quelle = classloader.getResource(".").getFile();

    StapelKonvertierer konvertierer = new StapelKonvertierer(Paths.get(quelle));
    konvertierer.konvertiere();
  }
}
