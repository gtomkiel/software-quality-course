package com.jabberpoint.slideitem;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.StyleManager;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.jabberpoint.Constants.Error.FILE_ERROR;
import static com.jabberpoint.Constants.Error.NOT_FOUND;
import static com.jabberpoint.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BitmapItemTest {
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    static {
        StyleManager.createStyles("Helvetica", "Dialog");
    }

    @Test
    void testToString() {
        BitmapItem item = new BitmapItem(1, getTestImagePath());
        assertEquals(String.format("BitmapItem[1 %s]", item.getText()), item.toString());
    }

    @Test
    void testGetBoundingBox() throws NoSuchFieldException, IllegalAccessException {
        Style style = StyleManager.getStyle(0);

        BitmapItem item = new BitmapItem(1, getTestImagePath());

        BufferedImage bufferedImage = getPrivateField(item, "bufferedImage", BufferedImage.class);
        int y = style.getLeading();

        assertEquals(0, item.getBoundingBox(null, null, 1.0f, style).x);
        assertEquals(y, item.getBoundingBox(null, null, 1.0f, style).y);
        assertEquals(bufferedImage.getWidth(), item.getBoundingBox(null, null, 1.0f, style).width);
        assertEquals(bufferedImage.getHeight(), item.getBoundingBox(null, null, 1.0f, style).height - y);
    }

    @Test
    void getScaledLeading() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int level = 1;
        float scale = 0.5f;
        Style style = StyleManager.getStyle(level);
        BitmapItem item = new BitmapItem(level, getTestImagePath());

        int leading = (int) (style.getLeading() * scale);
        Method scaledLeading = getPrivateMethod(item, "getScaledLeading", float.class, Style.class);

        assertEquals(leading, scaledLeading.invoke(item, scale, style));
    }

    @Test
    void getScaledIndent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int level = 1;
        float scale = 0.5f;
        Style style = StyleManager.getStyle(level);
        BitmapItem item = new BitmapItem(level, getTestImagePath());

        int indent = (int) (style.getIndent() * scale);
        Method scaledLeading = getPrivateMethod(item, "getScaledIndent", float.class, Style.class);

        assertEquals(indent, scaledLeading.invoke(item, scale, style));
    }

    @Test
    void getScaledWidth()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        int level = 1;
        float scale = 0.5f;
        BitmapItem item = new BitmapItem(level, getTestImagePath());

        BufferedImage bufferedImage = getPrivateField(item, "bufferedImage", BufferedImage.class);
        int width = (int) (bufferedImage.getWidth() * scale);
        Method scaledWidth = getPrivateMethod(item, "getScaledWidth", ImageObserver.class, float.class);

        assertEquals(width, scaledWidth.invoke(item, null, scale));
    }

    @Test
    void getScaledHeight()
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int level = 1;
        float scale = 0.5f;
        Style style = StyleManager.getStyle(level);
        BitmapItem item = new BitmapItem(level, getTestImagePath());

        BufferedImage bufferedImage = getPrivateField(item, "bufferedImage", BufferedImage.class);
        int height = (int) (style.getLeading() * scale + (int) (bufferedImage.getHeight() * scale));
        Method scaledWidth = getPrivateMethod(item, "getScaledHeight", ImageObserver.class, float.class, Style.class);

        assertEquals(height, scaledWidth.invoke(item, null, scale, style));
    }

    @Test
    void testGetTypeName() {
        BitmapItem item = new BitmapItem(1, getTestImagePath());
        assertEquals(SlideItemType.IMAGE, item.getType());
    }

    @Test
    void testDraw() {
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        BitmapItem item = new BitmapItem(1, getTestImagePath());
        item.draw(0, 0, 1.0f, graphics, StyleManager.getStyle(0), null);
    }

    @Test
    public void testFileNotFound() {
        System.setErr(new PrintStream(errContent));
        String invalidFileName = "invalid.png";
        new BitmapItem(1, invalidFileName);
        assertEquals(String.format("%s%s%s\n", FILE_ERROR, invalidFileName, NOT_FOUND), errContent.toString());
        System.setErr(originalErr);
    }
}
