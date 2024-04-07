package com.jabberpoint.style;

import java.awt.*;

/** <p>Style is for Indent, Color, Font and Leading.</p>
 * <p>Direct relation between style-number and item-level:
 * in Slide style if fetched for an item
 * with style-number as item-level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
    private final int indent, fontSize, leading;
    private final Color color;
    private final Font font;

    public Style(int indent, Color color, int points, int leading, String fontName, int fontStyle) {
        this.indent = indent;
        this.fontSize = points;
        this.leading = leading;
        this.color = color;
        this.font = new Font(fontName, fontStyle, points);
    }

    public String toString() {
        return String.format("[%d,%s; %d on %d]", indent, color, fontSize, leading);
    }

    public Font getFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }

    public int getIndent() {
        return indent;
    }

    public Color getColor() {
        return color;
    }

    public int getLeading() {
        return leading;
    }
}
