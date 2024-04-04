package com.jabberpoint.accessor;

import com.jabberpoint.Presentation;
import com.jabberpoint.slide.Slide;

import static com.jabberpoint.slideitem.SlideItemType.IMAGE;
import static com.jabberpoint.slideitem.SlideItemType.TEXT;

/** A built-in demo-presentation
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class DemoPresentation implements Accessor {
    public String loadFile(Presentation presentation, String _unusedFilename) {
        presentation.append(createFirstSlide());
        presentation.append(createSecondSlide());
        presentation.append(createThirdSlide());

        return "Demo Presentation";
    }

    private Slide createFirstSlide() {
        Slide slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.append(1, "The Java Presentation Tool", TEXT);
        slide.append(2, "Copyright (c) 1996-2000: Ian Darwin", TEXT);
        slide.append(2, "Copyright (c) 2000-now:", TEXT);
        slide.append(2, "Gert Florijn and Sylvia Stuurman", TEXT);
        slide.append(4, "Starting JabberPoint without a filename", TEXT);
        slide.append(4, "shows this presentation", TEXT);
        slide.append(1, "Navigate:", TEXT);
        slide.append(3, "Next slide: PgDn or Enter", TEXT);
        slide.append(3, "Previous slide: PgUp or up-arrow", TEXT);
        slide.append(3, "Quit: q or Q", TEXT);
        return slide;
    }

    private Slide createSecondSlide() {
        Slide slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.append(1, "Level 1", TEXT);
        slide.append(2, "Level 2", TEXT);
        slide.append(1, "Again level 1", TEXT);
        slide.append(1, "Level 1 has style number 1", TEXT);
        slide.append(2, "Level 2 has style number  2", TEXT);
        slide.append(3, "This is how level 3 looks like", TEXT);
        slide.append(4, "And this is level 4", TEXT);
        return slide;
    }

    private Slide createThirdSlide() {
        Slide slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(1, "To open a new presentation,", TEXT);
        slide.append(2, "use File->Open from the menu.", TEXT);
        slide.append(1, " ", TEXT);
        slide.append(1, "This is the end of the presentation.", TEXT);
        slide.append(1, "JabberPoint.gif", IMAGE);
        return slide;
    }

    public void saveFile(Presentation presentation, String unusedFilename) {
        throw new IllegalStateException("Save As->Demo! called");
    }
}
