package com.jabberpoint.accessor;

import com.jabberpoint.presentation.PresentationData;
import com.jabberpoint.slide.IndexedSlide;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>An Accessor allows you to read or write data for a presentation
 * read or write.</p>
 * <p>Non-abstract subclasses must implement the load and save methods.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public interface Accessor {
    /**
     * Load a presentation
     *
     * @param file The filename of the presentation
     * @return The presentation data
     */
    PresentationData loadFile(String file) throws IOException;

    /** Save the presentation
     * @param file The filename to save the presentation as
     * @param slides The slides to save
     * @param title The title of the presentation
     */
    void saveFile(String file, ArrayList<IndexedSlide> slides, String title) throws IOException;
}
