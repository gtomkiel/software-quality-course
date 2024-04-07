package com.jabberpoint.slideitem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class SlideItemFactoryTest {

    @Test
    void testCreateBitmapItem() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test.png", SlideItemType.IMAGE);

        assertThat(item).isInstanceOf(BitmapItem.class);
        assertEquals(1, item.getLevel());
        assertEquals("test.png", item.getText());
    }

    @Test
    void testCreateTextItem() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test", SlideItemType.TEXT);

        assertThat(item).isInstanceOf(TextItem.class);
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
        assertThat(item).isInstanceOf(TextItem.class);
    }

    @Test
    void testImageSlideItemType() {
        SlideItem item = SlideItemFactory.createSlideItem(1, "test.png", SlideItemType.IMAGE);
        assertThat(item).isInstanceOf(BitmapItem.class);
    }
}
