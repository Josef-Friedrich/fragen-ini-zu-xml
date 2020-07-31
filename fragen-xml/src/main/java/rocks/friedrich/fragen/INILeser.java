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

  public INILeser(String dateiName) throws FileNotFoundException, IOException {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    File file = new File(classloader.getResource(dateiName).getFile());

    ini = new Ini(new FileReader(file, Charset.forName("ISO-8859-1")));
    Ini.Section thema = ini.get("Thema");
    anzahlFragen = Integer.parseInt(thema.get("Fragen"));
    for (int i = 1; i <= anzahlFragen; i++) {
      Ini.Section frage = gibFrage(i);
      System.out.println(leseFragenText(frage));
      String[] antworten = leseAntworten(frage);
      System.out.println(antworten[0]);
      System.out.println(antworten[1]);
      System.out.println(antworten[2]);
      System.out.println(antworten[3]);
    }
  }

  public Ini.Section gibFrage (int fragenNummer) {
    return ini.get(Integer.toString(fragenNummer));
  }

  public String formatiereText(String text) {
    text = text.replaceAll("\"([^\"]*)\"", "„$1“");
    text = text.replaceAll("[ \t]+", " ");
    text = text.replaceAll(" \\?", "?");
    return text.trim();
  }

  public String leseFragenText(Ini.Section sec) {
    String fragenText = formatiereText(
      sec.get("FZ1") + " " + sec.get("FZ2") + " " + sec.get("FZ3"));
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
