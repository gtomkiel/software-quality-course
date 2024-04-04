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

    @Override
    public String toString() {
        return String.format("TextItem[%d,%s]", getLevel(), getText());
    }

    public String getText() {
        return text == null ? "" : text;
    }

    @Override
    public void draw(int x, int y, float scale, Graphics2D graphics, Style style, ImageObserver observer) {
        if (text == null || text.isEmpty()) {
            return;
        }

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<TextLayout> layouts = getLayouts(graphics, style, scale);
        drawTextLayouts(x, y, scale, graphics, style, layouts);
    }

    @Override
    public Rectangle getBoundingBox(Graphics2D graphics, ImageObserver observer, float scale, Style style) {
        List<TextLayout> layouts = getLayouts(graphics, style, scale);
        int scaledIndent = getScaledIndent(scale, style);
        double width = 0, height = getScaledLeading(scale, style);

        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > width) {
                width = bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                height += bounds.getHeight();
            }
            height += layout.getLeading() + layout.getDescent();
        }

        return new Rectangle(scaledIndent, 0, (int) width, (int) height);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    private List<TextLayout> getLayouts(Graphics2D graphics, Style style, float scale) {
        List<TextLayout> layouts = new ArrayList<>();
        AttributedString attributedString = getAttributedString(style, scale);
        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(), fontRenderContext);
        float wrappingWidth = (Slide.WIDTH - style.getIndent()) * scale;

        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    private void drawTextLayouts(int x, int y, float scale, Graphics2D graphics, Style style,
            List<TextLayout> layouts) {
        Point pen = new Point(x + getScaledIndent(scale, style), y + getScaledLeading(scale, style));
        graphics.setColor(style.getColor());

        for (TextLayout layout : layouts) {
            pen.y += (int) layout.getAscent();
            layout.draw(graphics, pen.x, pen.y);
            pen.y += (int) layout.getDescent();
        }
    }

    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());

        return attrStr;
    }

    private int getScaledIndent(float scale, Style style) {
        return (int) (style.getIndent() * scale);
    }

    private int getScaledLeading(float scale, Style style) {
        return (int) (style.getLeading() * scale);
    }
}
