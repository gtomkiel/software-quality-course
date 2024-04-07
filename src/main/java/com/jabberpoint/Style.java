package com.jabberpoint;

import java.awt.*;
import java.util.ArrayList;

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
    private static final String FONT_NAME = "Helvetica";
    private static final ArrayList<Style> styles = new ArrayList<>();
    private final int indent, fontSize, leading;
    private final Color color;
    private final Font font;

    public Style(int indent, Color color, int points, int leading) {
        this.indent = indent;
        this.color = color;
        font = new Font(FONT_NAME, Font.BOLD, fontSize = points);
        this.leading = leading;
    }

    public static void createStyles() {
        styles.add(new Style(0, Color.red, 48, 20));    // style for item-level 0
        styles.add(new Style(20, Color.blue, 40, 10));  // style for item-level 1
        styles.add(new Style(50, Color.black, 36, 10)); // style for item-level 2
        styles.add(new Style(70, Color.black, 30, 10)); // style for item-level 3
        styles.add(new Style(90, Color.black, 24, 10)); // style for item-level 4
    }

    public static Style getStyle(int level) {
        if (level >= styles.size()) {
            level = styles.size() - 1;
        }
        return styles.get(level);
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
