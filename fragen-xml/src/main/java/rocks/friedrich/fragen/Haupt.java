package rocks.friedrich.fragen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerException;
import org.ini4j.Ini;

public class Haupt {


  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException,
      ParserConfigurationException, TransformerException, IOException {


    INILeser internet = new INILeser("itg/internet.txt");

    XMLKonvertierer xml = new XMLKonvertierer();
    xml.setzteAutor(internet.gibAutor());
    xml.setzteThema(internet.gibThema());

    for (int i = 1; i <= internet.anzahlFragen; i++) {
      Ini.Section frage = internet.gibFrage(i);
      String[] antworten = internet.leseAntworten(frage);
      int[] schwierigkeit = internet.leseSchwierigkeit(frage);
      xml.setzteFrage(internet.leseFragenText(frage), antworten[0], antworten[1], antworten[2], antworten[3],
          schwierigkeit[0], schwierigkeit[1], schwierigkeit[2]);
    }

    xml.schreibeInDatei("tmp.xml");
  }
}
