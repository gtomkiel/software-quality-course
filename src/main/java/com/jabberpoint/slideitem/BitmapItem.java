package com.jabberpoint.slideitem;

import com.jabberpoint.Style;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import static com.jabberpoint.Constants.Error.FILE_ERROR;
import static com.jabberpoint.Constants.Error.NOT_FOUND;

/** <p>The class for a Bitmap item</p>.
 * <p>Bitmap items have the responsibility to draw themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem implements SlideItem {
    private final String imageName;
    private final int level;
    private BufferedImage bufferedImage;

    public BitmapItem(int level, String name) {
        this.level = level;
        this.imageName = name;

        try {
            this.bufferedImage = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.err.println(FILE_ERROR + imageName + NOT_FOUND);
        }
    }

    public String getName() {
        return imageName;
    }

    @Override
    public String toString() {
        return String.format("BitmapItem[%d %s]", getLevel(), imageName);
    }

    @Override
    public void draw(int x, int y, float scale, Graphics2D graphics, Style style, ImageObserver observer) {
        int scaledIndent = getScaledIndent(scale, style);
        int scaledWidth = getScaledWidth(observer, scale);
        int scaledHeight = getScaledHeight(observer, scale, style);
        graphics.drawImage(bufferedImage, x + scaledIndent, y + scaledIndent, scaledWidth, scaledHeight, observer);
    }

    @Override
    public Rectangle getBoundingBox(Graphics2D graphics, ImageObserver observer, float scale, Style style) {
        int scaledIndent = getScaledIndent(scale, style);
        int scaledWidth = getScaledWidth(observer, scale);
        int scaledHeight = getScaledHeight(observer, scale, style);
        return new Rectangle(scaledIndent, 0, scaledWidth, scaledHeight);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    private int getScaledIndent(float scale, Style style) {
        return (int) (style.getIndent() * scale);
    }

    private int getScaledWidth(ImageObserver observer, float scale) {
        return (int) (bufferedImage.getWidth(observer) * scale);
    }

    private int getScaledHeight(ImageObserver observer, float scale, Style style) {
        return ((int) (style.getLeading() * scale)) + (int) (bufferedImage.getHeight(observer) * scale);
    }
}
