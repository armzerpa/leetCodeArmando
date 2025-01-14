import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;

public class SSMLParser {
    private Document ssmlDocument;

    // Constructor that takes an SSML string and parses it into a Document
    public SSMLParser(String ssml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(ssml));
        this.ssmlDocument = builder.parse(inputSource);
    }

    // Method to extract the text content
    public String getTextContent() {
        NodeList speakNodes = ssmlDocument.getElementsByTagName("speak");
        if (speakNodes.getLength() > 0) {
            return speakNodes.item(0).getTextContent().trim();
        }
        return "";
    }

    // Method to extract information from specific tags
    public void parseTags() {
        NodeList nodes = ssmlDocument.getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("Tag: " + element.getTagName());
                NamedNodeMap attributes = element.getAttributes();
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attribute = attributes.item(j);
                    System.out.println("    Attribute: " + attribute.getNodeName() + " = " + attribute.getNodeValue());
                }
                if (element.hasChildNodes()) {
                    System.out.println("    Content: " + element.getTextContent().trim());
                }
            }
        }
    }

    public static void main(String[] args) {
        String ssml = """
                <speak>
                    <voice name="en-US-AriaNeural">
                        Hello, welcome to the world of SSML!
                    </voice>
                    <break time="500ms"/>
                    <prosody pitch="+10Hz">This is a test.</prosody>
                </speak>
                """;

        try {
            SSMLParser parser = new SSMLParser(ssml);
            System.out.println("Text Content: " + parser.getTextContent());
            System.out.println("\nParsed Tags:");
            parser.parseTags();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
