package com.jabberpoint.slide;

import com.jabberpoint.style.Style;
import com.jabberpoint.style.StyleManager;
import com.jabberpoint.slideitem.SlideItem;
import com.jabberpoint.slideitem.SlideItemFactory;
import com.jabberpoint.slideitem.SlideItemType;

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
    private final ArrayList<SlideItem> items;
    private String title;

    public Slide() {
        this.items = new ArrayList<>();
    }

    public void append(int level, String message, SlideItemType type) {
        append(SlideItemFactory.createSlideItem(level, message, type));
    }

    private void append(SlideItem item) {
        this.items.add(item);
    }

    public void draw(Graphics2D graphics, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;

        SlideItem title = SlideItemFactory.createSlideItem(0, getTitle(), SlideItemType.TEXT);
        y = drawSlideItem(title, area.x, y, scale, graphics, view);

        for (SlideItem item : getSlideItems()) {
            y = drawSlideItem(item, area.x, y, scale, graphics, view);
        }
    }

    private float getScale(Rectangle area) {
        return Math.min((float) area.width / WIDTH, (float) area.height / HEIGHT);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    private int drawSlideItem(SlideItem slideItem, int x, int y, float scale, Graphics2D graphics, ImageObserver view) {
        Style style = StyleManager.getStyle(slideItem.getLevel());
        slideItem.draw(x, y, scale, graphics, style, view);
        return y + slideItem.getBoundingBox(graphics, view, scale, style).height;
    }

    public ArrayList<SlideItem> getSlideItems() {
        return this.items;
    }
}
