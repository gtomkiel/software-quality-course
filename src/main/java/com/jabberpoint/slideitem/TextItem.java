package com.jabberpoint.slideitem;

import com.jabberpoint.Style;
import com.jabberpoint.slide.Slide;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/** <p>A text item.</p>
 * <p>A TextItem has drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem implements SlideItem {
    private final String text;
    private final int level;

    public TextItem(int level, String string) {
        this.level = level;
        this.text = string;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());

        return attrStr;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);

        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }

        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
        if (text == null || text.isEmpty()) {
            return;
        }

        List<TextLayout> layouts = getLayouts(g, myStyle, scale);
        drawTextLayouts(x, y, scale, g, myStyle, layouts);
    }

    private void drawTextLayouts(int x, int y, float scale, Graphics g, Style myStyle, List<TextLayout> layouts) {
        Point pen = new Point(x + (int) (myStyle.getIndent() * scale), y + (int) (myStyle.getLeading() * scale));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(myStyle.getColor());

        for (TextLayout layout : layouts) {
            pen.y += layout.getAscent();
            layout.draw(g2d, pen.x, pen.y);
            pen.y += layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attrStr = getAttributedString(s, scale);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;

        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    @Override
    public String toString() {
        return String.format("TextItem[%d,%s]", getLevel(), getText());
    }
}
