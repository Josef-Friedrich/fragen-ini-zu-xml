package rocks.friedrich.fragen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerException;

public class Haupt {

  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, ParserConfigurationException, TransformerException, IOException {
    INILeser iniLeser = new INILeser("eisenbahn.txt");
    INILeser euro = new INILeser("wirtschf/euro.txt");
    INILeser internet = new INILeser("itg/internet.txt");

    XMLKonvertierer xml = new XMLKonvertierer();
    xml.setzteAutor(internet.gibAutor());
    xml.setzteThema(internet.gibThema());
    xml.schreibeInDatei("tmp.xml");
  }
}
