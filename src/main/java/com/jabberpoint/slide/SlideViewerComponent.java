package com.jabberpoint.slide;

import com.jabberpoint.style.ColorType;
import com.jabberpoint.style.StyleManager;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/** <p>SlideViewerComponent is a graphical component that can show slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent implements Subscriber<IndexedSlide> {
    private final JFrame frame;
    private IndexedSlide slide;
    private Subscription subscription;
    private int numberOfSlides;

    public SlideViewerComponent(JFrame frame) {
        this.frame = frame;
    }

    public void updateSlideNumber(int slideNumber) {
        if (this.subscription != null) {
            this.subscription.request(slideNumber);
        }
    }

    public void loadSlides(int numberOfSlides, String title) {
        this.numberOfSlides = numberOfSlides;
        this.frame.setTitle(title);
    }

    public String getTitle() {
        return this.frame.getTitle();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        initGraphics(graphics);

        if (this.slide != null && this.slide.index() >= 0) {
            int y = drawSlideNumber((Graphics2D) graphics, 20);
            drawSlide(graphics, y);
        }
    }

    private void initGraphics(Graphics graphics) {
        graphics.setColor(StyleManager.getColor(ColorType.BACKGROUND));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setFont(StyleManager.getLabelFont());
        graphics.setColor(StyleManager.getColor(ColorType.FOREGROUND));
    }

    private int drawSlideNumber(Graphics2D graphics, int y_offset) {
        String slideNumberText = String.format("Slide %d of %d", 1 + this.slide.index(), this.numberOfSlides);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = graphics.getFontMetrics();
        int textWidth = metrics.stringWidth(slideNumberText) + 20;

        graphics.drawString(slideNumberText, getWidth() - textWidth, y_offset);

        return y_offset + metrics.getHeight();
    }

    private void drawSlide(Graphics graphics, int y_offset) {
        Rectangle area = new Rectangle(0, y_offset, getWidth(), (getHeight() - y_offset));
        slide.slide().draw((Graphics2D) graphics, area, this);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(IndexedSlide slide) {
        this.slide = slide;
        repaint();
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}
