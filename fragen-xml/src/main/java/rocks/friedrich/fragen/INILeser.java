package rocks.friedrich.fragen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.nio.charset.Charset;

import org.ini4j.Ini;

public class INILeser {

  Ini ini;
  int anzahlFragen;
  Ini.Section thema;

  public INILeser(String dateiName) throws FileNotFoundException, IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File file = new File(classloader.getResource(dateiName).getFile());

    ini = new Ini(new FileReader(file, Charset.forName("ISO-8859-1")));
    thema = ini.get("Thema");
    anzahlFragen = Integer.parseInt(thema.get("Fragen"));
  }

  private String formatiereText(String text) {
    text = text.replaceAll("\"([^\"]*)\"", "„$1“");
    text = text.replaceAll("[ \t]+", " ");
    text = text.replaceAll(" \\?", "?");
    return text.trim();
  }

  public int gibAnzahlFragen() {
    return anzahlFragen;
  }

  public String gibThema() {
    return formatiereText(thema.get("Thema"));
  }

  public String gibAutor() {
    return formatiereText(thema.get("Autor"));
  }

  public Ini.Section gibFrage(int fragenNummer) {
    return ini.get(Integer.toString(fragenNummer));
  }

  private int konvertiereGewinnsumme(String gewinnSumme) {
    switch (Integer.parseInt(gewinnSumme)) {
      case 50:
        return 1;
      case 100:
        return 2;
      case 200:
        return 3;
      case 300:
        return 4;
      case 500:
        return 5;
      case 1000:
        return 6;
      case 2000:
        return 7;
      case 4000:
        return 8;
      case 8000:
        return 9;
      case 16000:
        return 10;
      case 32000:
        return 11;
      case 64000:
        return 12;
      case 125000:
        return 13;
      case 50000:
        return 14;
      case 1000000:
        return 15;
    }
    return 1;
  }

  public int[] leseSchwierigkeit(Ini.Section sec) {
    int min = konvertiereGewinnsumme(sec.get("Min"));
    int max = konvertiereGewinnsumme(sec.get("Max"));
    int mitte = Math.round((min + max) / 2);
    return new int[] { mitte, min, max };
  }

  public String leseFragenText(Ini.Section sec) {
    String fragenText = formatiereText(sec.get("FZ1") + " " + sec.get("FZ2") + " " + sec.get("FZ3"));
    return fragenText;
  }

  public String[] leseAntworten(Ini.Section sec) {
    int falscheIndex = 1;
    String[] antworten = new String[4];
    for (int i = 1; i <= 4; i++) {
      String antwort = sec.get("Antwort_" + i);
      int istRichtig = Integer.parseInt(antwort.substring(0, 1));
      antwort = formatiereText(antwort.substring(1));
      if (istRichtig == 1) {

        antworten[0] = antwort;
      } else {
        antworten[falscheIndex] = antwort;
        falscheIndex++;
      }
    }
    return antworten;
  }

}
