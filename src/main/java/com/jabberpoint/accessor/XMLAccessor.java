package com.jabberpoint.accessor;

import com.jabberpoint.Presentation;
import com.jabberpoint.slide.Slide;
import com.jabberpoint.slideitem.BitmapItem;
import com.jabberpoint.slideitem.SlideItem;
import com.jabberpoint.slideitem.SlideItemFactory;
import com.jabberpoint.slideitem.TextItem;
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

import static com.jabberpoint.Constants.*;

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
    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);

        return titles.item(0).getTextContent();
    }

    public void loadFile(Presentation presentation, String filename) throws IOException {
        try {
            Document document = createDocument(filename);
            Element documentElement = document.getDocumentElement();
            presentation.setTitle(getTitle(documentElement, SHOW_TITLE));

            NodeList slides = documentElement.getElementsByTagName(SLIDE);
            loadSlides(presentation, slides);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.err.println(e.getMessage());
        }
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
            slide.setTitle(getTitle(xmlSlide, SLIDE_TITLE));
            presentation.append(slide);

            NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
            loadSlideItems(slide, slideItems);
        }
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
        String type = item.getAttributes().getNamedItem(KIND).getTextContent();

        switch (type) {
            case TEXT -> slide.append(SlideItemFactory.createSlideItem(level, item.getTextContent(), TEXT));
            case IMAGE -> slide.append(SlideItemFactory.createSlideItem(level, item.getTextContent(), IMAGE));
            default -> System.err.println(UNKNOWN_TYPE);
        }
    }

    private int getLevel(Element item) {
        int level = 1;
        String levelText = item.getAttributes().getNamedItem(LEVEL).getTextContent();

        if (levelText != null) {
            try {
                level = Integer.parseInt(levelText);
            } catch (NumberFormatException x) {
                System.err.println(NFE);
            }
        }

        return level;
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
        out.println("<showtitle>");
        out.println(title);
        out.println("</showtitle>");
    }

    private void writeSlides(PrintWriter out, Presentation presentation) {
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            writeSlide(out, presentation.getSlide(slideNumber));
        }
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
        out.print("<item kind=");
        if (slideItem instanceof TextItem) {
            writeTextItem(out, (TextItem) slideItem);
        } else if (slideItem instanceof BitmapItem) {
            writeBitmapItem(out, (BitmapItem) slideItem);
        } else {
            System.out.printf("Ignoring %s%n", slideItem);
        }
        out.println("</item>");
    }

    private void writeTextItem(PrintWriter out, TextItem textItem) {
        out.print(String.format("\"text\" level=%s\">", textItem.getText()));
        out.print(textItem.getText());
    }

    private void writeBitmapItem(PrintWriter out, BitmapItem bitmapItem) {
        out.print(String.format("\"image\" level=\"%d\">", bitmapItem.getLevel()));
        out.print(bitmapItem.getName());
    }

    private void writeFooter(PrintWriter out) {
        out.println("</presentation>");
    }
}