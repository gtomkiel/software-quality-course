package com.jabberpoint.slideitem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlideItemFactoryTest {

    @Test
    void testCreateBitmapItem() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test.png", SlideItemType.IMAGE);

        assertInstanceOf(BitmapItem.class, item);
        assertEquals(1, item.getLevel());
        assertEquals("test.png", item.getText());
    }

    @Test
    void testCreateTextItem() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test", SlideItemType.TEXT);

        assertInstanceOf(TextItem.class, item);
        assertEquals(1, item.getLevel());
        assertEquals("test", item.getText());
    }

    @Test
    void testInvalidSlideItemType() {
        assertThrows(NullPointerException.class, () -> {
            SlideItemFactory.createSlideItem(1, "test", null);
        });
    }

    @Test
    void testNullSlideItemType() {
        assertThrows(NullPointerException.class, () -> {
            SlideItemFactory.createSlideItem(1, "test", null);
        });
    }

    @Test
    void testTextSlideItemType() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test", SlideItemType.TEXT);
        assertInstanceOf(TextItem.class, item);
    }

    @Test
    void testImageSlideItemType() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test.png", SlideItemType.IMAGE);
        assertInstanceOf(BitmapItem.class, item);
    }
}
