package com.jabberpoint.accessor.xml;

import com.jabberpoint.accessor.Accessor;
import com.jabberpoint.presentation.PresentationData;
import com.jabberpoint.slide.IndexedSlide;

import java.io.IOException;
import java.util.ArrayList;

/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor implements Accessor {
    private final XMLLoader loader;
    private final XMLSaver saver;

    public XMLAccessor() {
        this.loader = new XMLLoader();
        this.saver = new XMLSaver();
    }

    public PresentationData loadFile(String filename) {
        return this.loader.loadFile(filename);
    }

    public void saveFile(String filename, ArrayList<IndexedSlide> slides, String title) throws IOException {
        this.saver.saveFile(filename, slides, title);
    }
}
