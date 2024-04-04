package com.jabberpoint;

import com.jabberpoint.slide.IndexedSlide;
import com.jabberpoint.slide.Slide;
import com.jabberpoint.slide.SlideViewerComponent;

import java.util.ArrayList;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation implements Publisher<IndexedSlide> {
    private String title;
    private ArrayList<IndexedSlide> slideList = null;
    private int currentSlideNumber = 0;
    private SlideViewerComponent slideViewComponent;

    public Presentation() {
        slideViewComponent = null;
        clear();
    }

    public int getSize() {
        return slideList.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlideNumber(int number) {
        currentSlideNumber = number;
        if (slideViewComponent != null) {
            slideViewComponent.updateSlideNumber(currentSlideNumber);
        }
    }

    public void prevSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    public void nextSlide() {
        if (currentSlideNumber < (slideList.size() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    public void clear() {
        slideList = new ArrayList<>();
        setSlideNumber(-1);
    }

    public void append(Slide slide) {
        slideList.add(new IndexedSlide(slide, slideList.size()));
    }

    public Slide getSlide(int number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }

        return slideList.get(number).slide();
    }

    public IndexedSlide getIndexedSlide(long number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }

        return slideList.get((int) number);
    }

    public void exit(int n) {
        System.exit(n);
    }

    @Override
    public void subscribe(Subscriber<? super IndexedSlide> subscriber) {
        if (slideViewComponent == null) {
            this.slideViewComponent = (SlideViewerComponent) subscriber;
        }

        subscriber.onSubscribe(new Subscription() {
            private boolean subscribed = true;

            @Override
            public void request(long l) {
                if (subscribed) {
                    subscriber.onNext(getIndexedSlide(l));
                }
            }

            @Override
            public void cancel() {
                subscribed = false;
            }
        });
    }
}
