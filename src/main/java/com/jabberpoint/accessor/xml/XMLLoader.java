package com.jabberpoint.accessor.xml;

import com.jabberpoint.presentation.PresentationData;
import com.jabberpoint.slide.IndexedSlide;
import com.jabberpoint.slide.Slide;
import com.jabberpoint.slideitem.SlideItemType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.jabberpoint.Constants.Error.NFE;

public class XMLLoader {
    public PresentationData loadFile(String filename) throws IOException {
        try {
            Document document = createDocument(filename);
            Element documentElement = document.getDocumentElement();
            NodeList slides = getElementsByTagName(documentElement, XMLType.SLIDE);

            ArrayList<IndexedSlide> indexedSlides = loadSlides(slides);
            String title = getTitle(documentElement, XMLType.SHOWTITLE);

            return new PresentationData(title, indexedSlides);
        } catch (SAXException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private Document createDocument(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        return builder.parse(new File(filename));
    }

    private ArrayList<IndexedSlide> loadSlides(NodeList slides) {
        ArrayList<IndexedSlide> indexedSlides = new ArrayList<>();

        for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);
            Slide slide = createSlide(xmlSlide);
            indexedSlides.add(new IndexedSlide(slideNumber, slide));
        }

        return indexedSlides;
    }

    private Slide createSlide(Element xmlSlide) {
        Slide slide = new Slide();
        slide.setTitle(getTitle(xmlSlide, XMLType.TITLE));

        NodeList slideItems = getElementsByTagName(xmlSlide, XMLType.ITEM);
        loadSlideItems(slide, slideItems);

        return slide;
    }

    private String getTitle(Element element, XMLType tagName) {
        NodeList titles = getElementsByTagName(element, tagName);

        return titles.item(0).getTextContent();
    }

    private NodeList getElementsByTagName(Element element, XMLType tagName) {
        return element.getElementsByTagName(tagName.name().toLowerCase());
    }

    private String getTextContent(Element element, XMLType tagName) {
        return element.getAttributes().getNamedItem(tagName.name().toLowerCase()).getTextContent();
    }

    private void loadSlideItems(Slide slide, NodeList slideItems) {
        int maxItems = slideItems.getLength();

        for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
            Element item = (Element) slideItems.item(itemNumber);
            loadSlideItem(slide, item);
        }
    }

    private void loadSlideItem(Slide slide, Element item) {
        int level = getLevel(item);
        String text = getTextContent(item, XMLType.KIND);

        try {
            SlideItemType type = SlideItemType.valueOf(text.toUpperCase());
            slide.append(level, item.getTextContent(), type);
        } catch (IllegalArgumentException e) {
            System.err.printf("Ignoring %s\n", text);
        }
    }

    private int getLevel(Element item) {
        int level = 1;
        String levelText = getTextContent(item, XMLType.LEVEL);

        if (levelText != null) {
            try {
                level = Integer.parseInt(levelText);
            } catch (NumberFormatException x) {
                System.err.println(NFE);
            }
        }

        return level;
    }
}
