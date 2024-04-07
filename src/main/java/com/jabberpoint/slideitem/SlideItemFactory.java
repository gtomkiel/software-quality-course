package com.jabberpoint.slideitem;

public class SlideItemFactory {
    public static SlideItem createSlideItem(int level, String text, SlideItemType type) {
        return switch (type) {
            case SlideItemType.IMAGE -> new BitmapItem(level, text);
            case SlideItemType.TEXT -> new TextItem(level, text);
        };
    }
}
