package se.msoto.civilizationdiscordintegration.quote;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Random;

@Component
public class QuoteParser {

    private static final String QUOTE_FILE_PATH = "quotes.xml";
    private static final String NEWLINE_PLACEHOLDER = "\\[NEWLINE\\]";

    public Quote getRandomQuote() {

        Document document = readDocument(QUOTE_FILE_PATH);
        NodeList textElements = document.getElementsByTagName("Text");
        return getRandomQuote(textElements);

    }

    private Document readDocument(String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(getClass().getClassLoader().getResourceAsStream(QUOTE_FILE_PATH));
            doc.getDocumentElement().normalize();
            return doc;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Could not parse XML file");
        }

    }

    private Quote getRandomQuote(NodeList quotes) {
        Random random = new Random();
        int index = random.nextInt(quotes.getLength() - 1);
        String[] quote = quotes.item(index).getTextContent().split(NEWLINE_PLACEHOLDER);
        if (hasAuthor(quote)) {
            return Quote.newBuilder()
                    .withAuthor(stripPrefix(quote[1]))
                    .withQuote(quote[0])
                    .build();
        } else {
            return Quote.newBuilder()
                    .withAuthor("Unknown")
                    .withQuote(quotes.item(index).getTextContent().replaceAll(NEWLINE_PLACEHOLDER, " "))
                    .build();
        }
    }

    private boolean hasAuthor(String[] quote) {
        return quote.length == 2;
    }

    private String stripPrefix(String s) {
        return s.replace("– ", "").replace("- ", "");
    }

}
