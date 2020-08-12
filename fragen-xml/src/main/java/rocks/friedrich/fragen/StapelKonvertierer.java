package rocks.friedrich.fragen;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

import org.ini4j.Ini;

public class StapelKonvertierer {
  Path quelle;

  public StapelKonvertierer(Path quelle) {
    this.quelle = quelle;
  }

  public void konvertiere() throws IOException {
    Files.walkFileTree(quelle, new SimpleFileVisitor<Path>() {
      public FileVisitResult visitFile(Path quelle, BasicFileAttributes attrs) throws IOException {
        Path ziel = Paths.get(quelle.toString().replaceAll("ini", "xml").replaceAll("\\.txt", ".xml"));
        try {
          konvertiereEineDatei(quelle, ziel);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return FileVisitResult.CONTINUE;
      }
    });
  }

  private void konvertiereEineDatei(Path quelle, Path ziel) throws IOException {
    if (gibDateiErweiterung(quelle.toFile()).equals("txt")) {
      System.out.println(quelle);
      Files.createDirectories(ziel.getParent());

      try {
        INILeser ini = new INILeser(quelle.toFile());

        XMLKonvertierer xml = new XMLKonvertierer();
        // System.out.println(ini.gibAutor());

        xml.setzteAutor(ini.gibAutor());
        xml.setzteThema(ini.gibThema());
        xml.setzteAnzahFragen(String.valueOf(ini.anzahlFragen));

        for (int i = 1; i <= ini.anzahlFragen; i++) {
          Ini.Section frage = ini.gibFrage(i);
          // System.out.println(ini.leseFragenText(frage));
          String[] antworten = ini.leseAntworten(frage);
          int[] schwierigkeit = ini.leseSchwierigkeit(frage);
          xml.setzteFrage(ini.leseFragenText(frage), antworten[0], antworten[1], antworten[2], antworten[3],
              schwierigkeit[0], schwierigkeit[1], schwierigkeit[2]);
        }

        xml.schreibeInDatei(ziel.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
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

}
