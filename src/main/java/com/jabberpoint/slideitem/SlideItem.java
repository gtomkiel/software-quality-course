package com.jabberpoint.slideitem;

import com.jabberpoint.style.Style;

import java.awt.*;
import java.awt.image.ImageObserver;

/** <p>The abstract class for an item on a slide<p>
 * <p>All SlideItems have drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public interface SlideItem {
    /** Draw the item
     * @param x The x-coordinate of the item
     * @param y The y-coordinate of the item
     * @param scale The scale of the item
     * @param graphics The graphics object to draw on
     * @param style The style of the item
     * @param observer The observer to notify when the image is ready
     */
    void draw(int x, int y, float scale, Graphics2D graphics, Style style, ImageObserver observer);

    /** Get the bounding box of the item
     * @param graphics The graphics object to draw on
     * @param observer The observer to notify when the image is ready
     * @param scale The scale of the item
     * @param style The style of the item
     * @return The bounding box of the item
     */
    Rectangle getBoundingBox(Graphics2D graphics, ImageObserver observer, float scale, Style style);

    /** Get the level of the item
     * @return The level of the item
     */
    int getLevel();

    /** Get the text of the item
     * @return The text of the item
     */
    String getText();

    /** Get the type of the item
     * @return The type of the item
     */
    SlideItemType getType();
}
