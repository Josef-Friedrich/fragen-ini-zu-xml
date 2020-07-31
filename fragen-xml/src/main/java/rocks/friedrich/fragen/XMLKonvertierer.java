package rocks.friedrich.fragen;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLKonvertierer {

  Element wurzel;
  Document dokument;
  Element fragen;

  public XMLKonvertierer() throws ParserConfigurationException {
    DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    dokument = documentBuilder.newDocument();
    wurzel = dokument.createElement("fragenKatalog");
    dokument.appendChild(wurzel);
    fragen = dokument.createElement("Fragen");
  }

  private void setzeWurzelTextElement(String elementName, String text) {
    Element element = dokument.createElement(elementName);
    element.appendChild(dokument.createTextNode(text));
    wurzel.appendChild(element);
  }

  public void setzteAutor(String autor) {
    setzeWurzelTextElement("autor", autor);
  }

  public void setzteThema(String thema) {
    setzeWurzelTextElement("thema", thema);
  }

  public void setzteFrage(String fragenText) {
    Element elFrage = dokument.createElement("Frage");
    Element elFragenText = dokument.createElement("fragenText");
    elFragenText.appendChild(dokument.createTextNode(text));
    wurzel.appendChild(element);
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
