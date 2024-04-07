package com.jabberpoint.accessor.xml;

import com.jabberpoint.slide.IndexedSlide;
import com.jabberpoint.slide.Slide;
import com.jabberpoint.slideitem.SlideItem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class XMLSaver {
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

}
