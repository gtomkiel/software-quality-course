package com.jabberpoint.slide;

import com.jabberpoint.Style;
import com.jabberpoint.slideitem.SlideItem;
import com.jabberpoint.slideitem.SlideItemFactory;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/** <p>A slide. This class has a drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    private String title;
    private final ArrayList<SlideItem> items;

    public Slide() {
        this.items = new ArrayList<>();
    }

    public void append(SlideItem item) {
        this.items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void append(int level, String message) {
        append(SlideItemFactory.createSlideItem(level, message, "text"));
    }

    public ArrayList<SlideItem> getSlideItems() {
        return this.items;
    }

    public int getSize() {
        return this.items.size();
    }

    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;

        SlideItem title = SlideItemFactory.createSlideItem(0, getTitle(), "text");
        y = drawSlideItem(title, area.x, y, scale, g, view);

        for (SlideItem item : getSlideItems()) {
            y = drawSlideItem(item, area.x, y, scale, g, view);
        }
    }

    private int drawSlideItem(SlideItem slideItem, int x, int y, float scale, Graphics g, ImageObserver view) {
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(x, y, scale, g, style, view);
        return y + slideItem.getBoundingBox(g, view, scale, style).height;
    }

    private float getScale(Rectangle area) {
        float scale = Math.min((float) area.width / WIDTH, (float) area.height / HEIGHT);
        // at smaller scales, the application becomes unusable
        if (scale <= 0.5f) {
            scale = 0.5f;
        }
        return scale;
    }
}
