import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.StringReader;
import org.xml.sax.InputSource;
import java.util.*;

public class SSMLParserClaude {
    private static final Set<String> VALID_PROSODY_RATES = new HashSet<>(Arrays.asList(
            "x-slow", "slow", "medium", "fast", "x-fast"
    ));

    private static final Set<String> VALID_PROSODY_PITCHES = new HashSet<>(Arrays.asList(
            "x-low", "low", "medium", "high", "x-high"
    ));

    private static final Set<String> VALID_BREAK_STRENGTHS = new HashSet<>(Arrays.asList(
            "none", "x-weak", "weak", "medium", "strong", "x-strong"
    ));

    /**
     * Parse and validate SSML content
     * @param ssmlContent The SSML string to parse
     * @return Validated and normalized SSML string
     * @throws Exception if the SSML is invalid
     */
    public String parseSSML(String ssmlContent) throws Exception {
        // Validate basic structure
        if (!ssmlContent.trim().startsWith("<speak>") || !ssmlContent.trim().endsWith("</speak>")) {
            throw new IllegalArgumentException("SSML must be wrapped in <speak> tags");
        }

        // Parse XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(ssmlContent)));

        // Validate and normalize the document
        validateNode(doc.getDocumentElement());

        // Convert back to string
        return nodeToString(doc.getDocumentElement());
    }

    /**
     * Recursively validate SSML nodes
     * @param node The node to validate
     * @throws IllegalArgumentException if the SSML is invalid
     */
    private void validateNode(Node node) {
        String nodeName = node.getNodeName().toLowerCase();

        switch (nodeName) {
            case "speak":
                validateSpeakNode(node);
                break;
            case "prosody":
                validateProsodyNode(node);
                break;
            case "break":
                validateBreakNode(node);
                break;
            case "emphasis":
                validateEmphasisNode(node);
                break;
            case "say-as":
                validateSayAsNode(node);
                break;
        }

        // Recursively validate child nodes
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                validateNode(child);
            }
        }
    }

    /**
     * Validate the speak root node
     * @param node The speak node
     */
    private void validateSpeakNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Node xmlLang = attributes.getNamedItem("xml:lang");
            if (xmlLang != null) {
                // Validate language code format (e.g., "en-US")
                String lang = xmlLang.getNodeValue();
                if (!lang.matches("[a-zA-Z]{2}(-[a-zA-Z]{2})?")) {
                    throw new IllegalArgumentException("Invalid language code format: " + lang);
                }
            }
        }
    }

    /**
     * Validate prosody node
     * @param node The prosody node
     */
    private void validateProsodyNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Node rate = attributes.getNamedItem("rate");
            Node pitch = attributes.getNamedItem("pitch");
            Node volume = attributes.getNamedItem("volume");

            if (rate != null) {
                String rateValue = rate.getNodeValue().toLowerCase();
                if (!VALID_PROSODY_RATES.contains(rateValue) && !isValidPercentage(rateValue)) {
                    throw new IllegalArgumentException("Invalid prosody rate: " + rateValue);
                }
            }

            if (pitch != null) {
                String pitchValue = pitch.getNodeValue().toLowerCase();
                if (!VALID_PROSODY_PITCHES.contains(pitchValue) && !isValidHertz(pitchValue) && !isValidSemitones(pitchValue)) {
                    throw new IllegalArgumentException("Invalid prosody pitch: " + pitchValue);
                }
            }

            if (volume != null) {
                String volumeValue = volume.getNodeValue();
                if (!isValidDecibel(volumeValue)) {
                    throw new IllegalArgumentException("Invalid prosody volume: " + volumeValue);
                }
            }
        }
    }

    /**
     * Validate break node
     * @param node The break node
     */
    private void validateBreakNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Node strength = attributes.getNamedItem("strength");
            Node time = attributes.getNamedItem("time");

            if (strength != null) {
                String strengthValue = strength.getNodeValue().toLowerCase();
                if (!VALID_BREAK_STRENGTHS.contains(strengthValue)) {
                    throw new IllegalArgumentException("Invalid break strength: " + strengthValue);
                }
            }

            if (time != null) {
                String timeValue = time.getNodeValue();
                if (!isValidTime(timeValue)) {
                    throw new IllegalArgumentException("Invalid break time: " + timeValue);
                }
            }
        }
    }

    /**
     * Validate emphasis node
     * @param node The emphasis node
     */
    private void validateEmphasisNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            Node level = attributes.getNamedItem("level");
            if (level != null) {
                String levelValue = level.getNodeValue().toLowerCase();
                if (!Arrays.asList("strong", "moderate", "reduced").contains(levelValue)) {
                    throw new IllegalArgumentException("Invalid emphasis level: " + levelValue);
                }
            }
        }
    }

    /**
     * Validate say-as node
     * @param node The say-as node
     */
    private void validateSayAsNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes == null || attributes.getNamedItem("interpret-as") == null) {
            throw new IllegalArgumentException("say-as element must have interpret-as attribute");
        }

        String interpretAs = attributes.getNamedItem("interpret-as").getNodeValue();
        // Add validation for specific interpret-as values based on your needs
        if (!Arrays.asList("characters", "cardinal", "ordinal", "digits", "fraction", "unit",
                "date", "time", "telephone", "address").contains(interpretAs)) {
            throw new IllegalArgumentException("Invalid interpret-as value: " + interpretAs);
        }
    }

    // Utility validation methods

    private boolean isValidPercentage(String value) {
        return value.matches("\\d+%");
    }

    private boolean isValidHertz(String value) {
        return value.matches("\\d+Hz");
    }

    private boolean isValidSemitones(String value) {
        return value.matches("[+-]\\d+st");
    }

    private boolean isValidDecibel(String value) {
        return value.matches("[+-]\\d+dB");
    }

    private boolean isValidTime(String value) {
        return value.matches("\\d+m?s");
    }

    /**
     * Convert Node back to string representation
     * @param node The node to convert
     * @return String representation of the node
     */
    private String nodeToString(Node node) {
        StringBuilder sb = new StringBuilder();
        processNode(node, sb);
        return sb.toString();
    }

    private void processNode(Node node, StringBuilder sb) {
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                sb.append("<").append(node.getNodeName());

                // Add attributes
                NamedNodeMap attributes = node.getAttributes();
                if (attributes != null) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        Node attr = attributes.item(i);
                        sb.append(" ")
                                .append(attr.getNodeName())
                                .append("=\"")
                                .append(attr.getNodeValue())
                                .append("\"");
                    }
                }

                sb.append(">");

                // Process children
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    processNode(children.item(i), sb);
                }

                sb.append("</").append(node.getNodeName()).append(">");
                break;

            case Node.TEXT_NODE:
                sb.append(node.getTextContent());
                break;
        }
    }

    public static void main(String[] args) {
        SSMLParserClaude parser = new SSMLParserClaude();

        // Example SSML content
        String ssml = "<speak version=\"1.0\" xml:lang=\"en-US\">"
                + "<prosody rate=\"slow\" pitch=\"low\">This is a test"
                + "<break strength=\"strong\"/> with a pause.</prosody>"
                + "</speak>";

        String ssml2 = """
                <speak>
                    <voice name="en-US-AriaNeural">
                        Hello, welcome to the world of SSML!
                    </voice>
                    <break time="500ms"/>
                    <prosody pitch="+10Hz">This is a test.</prosody>
                </speak>
                """;

        try {
            String validatedSSML = parser.parseSSML(ssml);
            System.out.println("Validated SSML: " + validatedSSML);
        } catch (Exception e) {
            System.err.println("Invalid SSML: " + e.getMessage());
        }
    }
}