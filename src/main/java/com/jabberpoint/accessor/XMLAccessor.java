package com.jabberpoint.accessor;

import com.jabberpoint.presentation.PresentationData;
import com.jabberpoint.slide.IndexedSlide;
import com.jabberpoint.slide.Slide;
import com.jabberpoint.slideitem.SlideItem;
import com.jabberpoint.slideitem.SlideItemType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.jabberpoint.Constants.Error.NFE;

/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor implements Accessor {
    public PresentationData loadFile(String filename) {
        try {
            Document document = createDocument(filename);
            Element documentElement = document.getDocumentElement();
            NodeList slides = getElementsByTagName(documentElement, XMLType.SLIDE);

            ArrayList<IndexedSlide> indexedSlides = loadSlides(slides);
            String title = getTitle(documentElement, XMLType.SHOWTITLE);

            return new PresentationData(title, indexedSlides);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void saveFile(String filename, ArrayList<IndexedSlide> slides, String title) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            writeHeader(out);
            writeTitle(out, title);
            writeSlides(out, slides);
            writeFooter(out);
        }
    }

    private void writeHeader(PrintWriter out) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
    }

    private void writeTitle(PrintWriter out, String title) {
        out.printf("<showtitle>%s</showtitle>\n", title);
    }

    private void writeSlides(PrintWriter out, ArrayList<IndexedSlide> slides) {
        for (IndexedSlide slide : slides) {
            writeSlide(out, slide.slide());
        }
    }

    private void writeFooter(PrintWriter out) {
        out.println("</presentation>");
    }

    private void writeSlide(PrintWriter out, Slide slide) {
        out.println("<slide>");
        out.printf("<title>%s</title>\n", slide.getTitle());
        writeSlideItems(out, slide.getSlideItems());
        out.println("</slide>");
    }

    private void writeSlideItems(PrintWriter out, ArrayList<SlideItem> slideItems) {
        for (SlideItem slideItem : slideItems) {
            writeSlideItem(out, slideItem);
        }
    }

    private void writeSlideItem(PrintWriter out, SlideItem slideItem) {
        String kind = slideItem.getType().toString().toLowerCase();
        int level = slideItem.getLevel();
        String text = slideItem.getText();

        out.printf("<item kind=\"%s\" level=\"%d\">%s</item>\n", kind, level, text);
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

    protected void loadSlideItem(Slide slide, Element item) {
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
