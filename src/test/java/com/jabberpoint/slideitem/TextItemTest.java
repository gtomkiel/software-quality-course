package com.jabberpoint.slideitem;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.StyleManager;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.jabberpoint.TestUtils.getPrivateMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextItemTest {
    static {
        StyleManager.createStyles("Helvetica", "Dialog");
    }

    @Test
    public void testTextItem() {
        TextItem textItem = new TextItem(1, "Hello, World!");
        assertEquals("TextItem[1,Hello, World!]", textItem.toString());
    }

    @Test
    public void testDraw() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        TextItem item = new TextItem(1, "Hello, World!");
        item.draw(0, 0, 1.0f, graphics, StyleManager.getStyle(1), null);
    }

    @Test
    public void testDrawTextNull() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        TextItem item = new TextItem(1, null);
        item.draw(0, 0, 1.0f, graphics, StyleManager.getStyle(1), null);
    }

    @Test
    public void testGetBoundingBox() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        Style style = StyleManager.getStyle(1);
        float scale = 1.0f;

        TextItem item = new TextItem(1, "Hello, World!");
        Method getLayouts = getPrivateMethod(item, "getLayouts", Graphics2D.class, Style.class, float.class);

        ArrayList<TextLayout> layouts = (ArrayList<TextLayout>) getLayouts.invoke(item, graphics, style, scale);
        TextLayout layout = layouts.getFirst();

        float leading = layout.getLeading();
        float indent = layout.getDescent();

        int x = 20;
        int y = 0;
        int width = (int) layout.getBounds().getWidth();
        double height = style.getLeading() + layout.getBounds().getHeight() + leading + (indent);

        assertEquals(x, item.getBoundingBox(graphics, null, scale, style).x);
        assertEquals(y, item.getBoundingBox(graphics, null, scale, style).y);
        assertEquals(width, item.getBoundingBox(graphics, null, scale, style).width);
        assertEquals((int) height, item.getBoundingBox(graphics, null, scale, style).height);
    }

    @Test
    public void testGetType() {
        TextItem item = new TextItem(1, "Hello, World!");
        assertEquals(SlideItemType.TEXT, item.getType());
    }

    @Test
    public void testGetLayouts() {

    }

    @Test
    public void testDrawTextLayouts() {

    }

    @Test
    public void testGetAttributedString() {

    }

    @Test
    public void testGetScaledIndent() {

    }

    @Test
    public void testGetScaledLeading() {

    }
}
