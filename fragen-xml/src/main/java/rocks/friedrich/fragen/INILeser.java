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
      System.out.println(leseFragenText(gibFrage(i)));
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

}
