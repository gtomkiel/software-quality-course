package com.jabberpoint.slideitem;

public class SlideItemFactory {
    public static SlideItem createSlideItem(int level, String text, String type) {
        return switch (type) {
            case "image" -> new BitmapItem(level, text);
            case "text" -> new TextItem(level, text);
            default -> {
                System.out.printf("Unknown type: %s\n", type);
                yield null;
            }
        };
    }
}
