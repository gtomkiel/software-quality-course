package com.jabberpoint.presentation;

import com.jabberpoint.slide.IndexedSlide;

import java.util.ArrayList;

public class PresentationData {
    private final String title;
    private final ArrayList<IndexedSlide> slides;

    public PresentationData(String title, ArrayList<IndexedSlide> slideList) {
        this.title = title;
        this.slides = slideList;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<IndexedSlide> getSlides() {
        return slides;
    }
}
