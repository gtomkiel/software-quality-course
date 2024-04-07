package com.jabberpoint.presentation;

import com.jabberpoint.accessor.Accessor;
import com.jabberpoint.slide.IndexedSlide;
import com.jabberpoint.slide.SlideViewerComponent;
import com.jabberpoint.slide.SlideViewerFrame;

import java.io.IOException;
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
    private final SlideViewerComponent slideViewerComponent;
    private String title;
    private ArrayList<IndexedSlide> slideList;
    private int currentSlideNumber;
    private Accessor accessor;

    public Presentation(SlideViewerFrame slideViewerFrame) {
        this.slideViewerComponent = new SlideViewerComponent(slideViewerFrame);
        this.clear();
    }

    public void clear() {
        this.slideList = new ArrayList<>();
        setSlideNumber(-1);
    }

    public void setSlideNumber(int number) {
        this.currentSlideNumber = number;
        if (number >= 0) {
            slideViewerComponent.updateSlideNumber(currentSlideNumber);
        }
    }

    public void load(Accessor accessor, String filename) throws IOException {
        this.clear();
        this.accessor = accessor;
        PresentationData data = accessor.loadFile(filename);
        this.subscribe(slideViewerComponent);

        this.slideList = data.getSlides();
        this.slideViewerComponent.setNumberOfSlides(this.slideList.size());
        this.setTitle(data.getTitle());
        this.setSlideNumber(0);
    }

    @Override
    public void subscribe(Subscriber<? super IndexedSlide> subscriber) {
        subscriber.onSubscribe(new Subscription() {
            private boolean subscribed = true;

            @Override
            public void request(long l) {
                if (subscribed) {
                    subscriber.onNext(getSlide(l));
                }
            }

            @Override
            public void cancel() {
                subscribed = false;
            }
        });
    }

    private void setTitle(String title) {
        this.title = title;
        this.slideViewerComponent.setTitle(title);
    }

    public IndexedSlide getSlide(long number) {
        if (number < 0 || number >= getSize()) {
            return null;
        }

        return slideList.get((int) number);
    }

    public int getSize() {
        return slideList.size();
    }

    public void save(String filename) throws IOException, IllegalStateException {
        accessor.saveFile(filename, slideList, title);
    }

    public void exit(int status) {
        System.exit(status);
    }

    public SlideViewerComponent getSlideViewerComponent() {
        return slideViewerComponent;
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
}
