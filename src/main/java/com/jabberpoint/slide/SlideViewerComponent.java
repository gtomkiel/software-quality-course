package com.jabberpoint.slide;

import com.jabberpoint.Presentation;

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
    private IndexedSlide slide;
    private final Font labelFont;
    private Presentation presentation;
    private final JFrame frame;
    private Subscription subscription;

    private static final long serialVersionUID = 227L;

    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONT_NAME = "Dialog";
    private static final int FONT_STYLE = Font.BOLD;
    private static final int FONT_HEIGHT = 10;
    private static final int Y_OFFSET = 20;

    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        setBackground(BGCOLOR);
        this.presentation = presentation;
        labelFont = new Font(FONT_NAME, FONT_STYLE, FONT_HEIGHT);
        this.frame = frame;
    }

    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    public void updateSlideNumber(int slideNumber) {
        subscription.request(slideNumber);
    }

    public void paintComponent(Graphics graphics) {
        initGraphics(graphics);

        if (this.slide != null && this.slide.index() >= 0) {
            drawSlideNumber(graphics);
            drawSlide(graphics);
        }
    }

    private void initGraphics(Graphics graphics) {
        graphics.setColor(BGCOLOR);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setFont(labelFont);
        graphics.setColor(COLOR);
    }

    private void drawSlideNumber(Graphics graphics) {
        String slideNumberText = String.format("Slide %d of %d", 1 + this.slide.index(), presentation.getSize());
        graphics.drawString(slideNumberText, getWidth() - 100, Y_OFFSET);
    }

    private void drawSlide(Graphics graphics) {
        Rectangle area = new Rectangle(0, Y_OFFSET, getWidth(), (getHeight() - Y_OFFSET));
        slide.slide().draw(graphics, area, this);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(IndexedSlide slide) {
        this.slide = slide;
        repaint();
        frame.setTitle(this.presentation.getTitle());
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
