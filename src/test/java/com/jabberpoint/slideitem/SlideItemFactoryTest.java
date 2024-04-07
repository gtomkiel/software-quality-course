package com.jabberpoint.slideitem;

import org.junit.jupiter.api.Test;

import static com.jabberpoint.TestUtils.getTestImagePath;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class SlideItemFactoryTest {

    @Test
    void testCreateBitmapItem() {
        String image = getTestImagePath();
        SlideItem item = SlideItemFactory.createSlideItem(1, image, SlideItemType.IMAGE);

        assertThat(item).isInstanceOf(BitmapItem.class);
        assertEquals(1, item.getLevel());
        assertEquals(image, item.getText());
    }

    @Test
    void testCreateTextItem() {
        String image = getTestImagePath();
        SlideItem item = SlideItemFactory.createSlideItem(1, image, SlideItemType.TEXT);

        assertThat(item).isInstanceOf(TextItem.class);
        assertEquals(1, item.getLevel());
        assertEquals(image, item.getText());
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
        String image = getTestImagePath();
        SlideItem item = SlideItemFactory.createSlideItem(1, image, SlideItemType.IMAGE);
        assertThat(item).isInstanceOf(BitmapItem.class);
    }
}
