package com.jabberpoint.style;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StyleManager {
    private static final ArrayList<Style> styles = new ArrayList<>();
    private static final HashMap<ColorType, Color> colorMap = new HashMap<>();
    private static Font labelFont;

    public static void createStyles(String slideFontName, String labelFontName) {
        labelFont = new Font(labelFontName, Font.BOLD, 10);
        colorMap.put(ColorType.BACKGROUND, Color.white);
        colorMap.put(ColorType.FOREGROUND, Color.black);

        styles.add(new Style(0, Color.red, 48, 20, slideFontName, Font.BOLD));
        styles.add(new Style(20, Color.blue, 40, 10, slideFontName, Font.BOLD));
        styles.add(new Style(50, Color.black, 36, 10, slideFontName, Font.BOLD));
        styles.add(new Style(70, Color.black, 30, 10, slideFontName, Font.BOLD));
        styles.add(new Style(90, Color.black, 24, 10, slideFontName, Font.BOLD));
    }

    public static Style getStyle(int level) {
        if (level >= styles.size()) {
            level = styles.size() - 1;
        }
        return styles.get(level);
    }

    public static Font getLabelFont() {
        return labelFont;
    }

    public static Color getColor(ColorType colorName) {
        return colorMap.get(colorName);
    }
}
