package com.jabberpoint.accessor;

import com.jabberpoint.Presentation;
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
    public String loadFile(Presentation presentation, String filename) {
        try {
            Document document = createDocument(filename);
            Element documentElement = document.getDocumentElement();
            NodeList slides = getElementsByTagName(documentElement, XMLType.SLIDE);

            loadSlides(presentation, slides);

            return getTitle(documentElement, XMLType.SHOWTITLE);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
        return filename;
    }

    public void saveFile(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            writeHeader(out);
            writeTitle(out, presentation.getTitle());
            writeSlides(out, presentation);
            writeFooter(out);
        }
    }

    private void writeHeader(PrintWriter out) {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
        out.println("<presentation>");
    }

    private void writeTitle(PrintWriter out, String title) {
        out.println(String.format("<showtitle>%s</showtitle>", title));
    }

    private void writeSlides(PrintWriter out, Presentation presentation) {
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            writeSlide(out, presentation.getSlide(slideNumber).slide()); // TODO: find if slides can be used directly
        }
    }

    private void writeFooter(PrintWriter out) {
        out.println("</presentation>");
    }

    private void writeSlide(PrintWriter out, Slide slide) {
        out.println("<slide>");
        out.println(String.format("<title>%s</title>", slide.getTitle()));
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

        out.println(String.format("<item kind=\"%s\" level=\"%d\">%s</item>", kind, level, text));
    }

    private Document createDocument(String filename) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        return builder.parse(new File(filename));
    }

    private void loadSlides(Presentation presentation, NodeList slides) {
        int max = slides.getLength();

        for (int slideNumber = 0; slideNumber < max; slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);
            Slide slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, XMLType.TITLE));
            presentation.append(slide);

            NodeList slideItems = getElementsByTagName(xmlSlide, XMLType.ITEM);
            loadSlideItems(slide, slideItems);
        }
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
