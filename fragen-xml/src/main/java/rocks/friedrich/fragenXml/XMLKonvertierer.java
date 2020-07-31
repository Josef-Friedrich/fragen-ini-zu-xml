import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLKonvertierer {
  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, ParserConfigurationException, TransformerException{
    Scanner scan = new Scanner(new File("eisenbahn.txt"), "ISO-8859-1");

    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    Document document = documentBuilder.newDocument();

    // root element
    Element root = document.createElement("fragenKatalog");
    document.appendChild(root);


    // [270]
    // FZ1=
    // FZ2=Zwischen welchen beiden Städten wurde 1835 die erste Eisenbahnstrecke
    // FZ3=in Deutschland eröffnet ?
    // Min=16000
    // Max=250000
    // Antwort_1=1Nürnberg - Fürth
    // Antwort_2=0Dortmund - Bochum
    // Antwort_3=0Leipzig - Dresden
    // Antwort_4=0Berlin - Potsdam
    while (scan.hasNext()) {
      String zeile = new String(scan.nextLine().getBytes(), "UTF-8");
      if (zeile.matches("^\\[\\d+\\]")) {
        System.out.println(zeile);
        for (int i = 1; i <= 9; i++) {
          scan.nextLine();
        }
      }
    }
    scan.close();

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource domSource = new DOMSource(document);
    StreamResult streamResult = new StreamResult(new File("eisenbahn.xml"));
    transformer.transform(domSource, streamResult);
  }
}
