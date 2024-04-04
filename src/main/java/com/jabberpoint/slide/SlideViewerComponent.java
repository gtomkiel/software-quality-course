package com.jabberpoint.slide;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
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
    @Serial
    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private final Font labelFont = new Font("Dialog", Font.BOLD, 10);
    private final int Y_OFFSET = 20;
    private final JFrame frame;
    private IndexedSlide slide;
    private Subscription subscription;
    private int numberOfSlides;

    public SlideViewerComponent(JFrame frame) {
        setBackground(BGCOLOR);
        this.frame = frame;
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public void setNumberOfSlides(int numberOfSlides) {
        this.numberOfSlides = numberOfSlides;
    }

    public void updateSlideNumber(int slideNumber) {
        if (this.subscription != null) {
            this.subscription.request(slideNumber);
        }
    }

    public void paintComponent(Graphics graphics) {
        initGraphics(graphics);

        if (this.slide != null && this.slide.index() >= 0) {
            drawSlideNumber((Graphics2D) graphics);
            drawSlide(graphics);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    private void initGraphics(Graphics graphics) {
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setFont(labelFont);
        graphics.setColor(COLOR);
    }

    private void drawSlideNumber(Graphics2D graphics) {
        String slideNumberText = String.format("Slide %d of %d", 1 + this.slide.index(), this.numberOfSlides);

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = graphics.getFontMetrics();
        int textWidth = metrics.stringWidth(slideNumberText) + 20;

        graphics.drawString(slideNumberText, getWidth() - textWidth, Y_OFFSET);
    }

    private void drawSlide(Graphics graphics) {
        Rectangle area = new Rectangle(0, Y_OFFSET, getWidth(), (getHeight() - Y_OFFSET));
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
