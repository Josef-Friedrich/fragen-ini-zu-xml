package rocks.friedrich.fragen;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLKonvertierer {

  Element wurzel;
  Document dokument;
  Element fragen;
  Element thema;
  Element autor;
  Element anzahlFragen;
  Element fach;

  public XMLKonvertierer() throws ParserConfigurationException {
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    dokument = documentBuilder.newDocument();
    wurzel = dokument.createElement("fragenKatalog");
    dokument.appendChild(wurzel);

    fach = dokument.createElement("fach");
    wurzel.appendChild(fach);

    thema = dokument.createElement("thema");
    wurzel.appendChild(thema);

    autor = dokument.createElement("autor");
    wurzel.appendChild(autor);

    anzahlFragen = dokument.createElement("anzahlFragen");
    wurzel.appendChild(anzahlFragen);

    fragen = dokument.createElement("fragen");
    wurzel.appendChild(fragen);
  }

  private void erzeugeText(Element element, String text) {
    element.appendChild(dokument.createTextNode(text));
  }

  private Element erzeugeElementMitText(String elementName, String text) {
    Element element = dokument.createElement(elementName);
    erzeugeText(element, text);
    return element;
  }

  private void hängeTextElementAn(Element element, String elementName, String text) {
    element.appendChild(erzeugeElementMitText(elementName, text));
  }

  public void setzteAutor(String autor) {
    erzeugeText(this.autor, autor);
  }

  public void setzteThema(String thema) {
    erzeugeText(this.thema, thema);
  }

  public void setzteAnzahFragen(String anzahlFragen) {
    erzeugeText(this.anzahlFragen, anzahlFragen);
  }

  public void setzteFach(String fach) {
    erzeugeText(this.fach, fach);
  }

  public void setzteFrage(String fragenText, String richtigeAntwort, String falscheAntwort1, String falscheAntwort2,
      String falscheAntwort3, int schwierikeit, int schwierigkeitMin, int schwierigkeitMax) {
    Element frage = dokument.createElement("frage");
    fragen.appendChild(frage);
    hängeTextElementAn(frage, "fragenText", fragenText);
    hängeTextElementAn(frage, "richtigeAntwort", richtigeAntwort);
    hängeTextElementAn(frage, "falscheAntwort1", falscheAntwort1);
    hängeTextElementAn(frage, "falscheAntwort2", falscheAntwort2);
    hängeTextElementAn(frage, "falscheAntwort3", falscheAntwort3);
    hängeTextElementAn(frage, "schwierikeit", Integer.toString(schwierikeit));
    hängeTextElementAn(frage, "schwierigkeitMin", Integer.toString(schwierigkeitMin));
    hängeTextElementAn(frage, "schwierigkeitMax", Integer.toString(schwierigkeitMax));

  }

  public void schreibeInDatei(String dateiName) throws TransformerConfigurationException, TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    DOMSource domSource = new DOMSource(dokument);
    StreamResult streamResult = new StreamResult(new File(dateiName));
    transformer.transform(domSource, streamResult);
  }
}
