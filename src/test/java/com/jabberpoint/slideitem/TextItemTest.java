package com.jabberpoint.slideitem;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.StyleManager;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

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
    public void testGetBoundingBox() {
        Assumptions.assumeTrue(!GraphicsEnvironment.isHeadless());
        
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        Style style = StyleManager.getStyle(1);
        float scale = 1.0f;

        TextItem item = new TextItem(1, "Hello, World!");
        assertEquals(20, item.getBoundingBox(graphics, null, scale, style).x);
        assertEquals(0, item.getBoundingBox(graphics, null, scale, style).y);
        assertEquals(249, item.getBoundingBox(graphics, null, scale, style).width);
        assertEquals(57, item.getBoundingBox(graphics, null, scale, style).height);
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
