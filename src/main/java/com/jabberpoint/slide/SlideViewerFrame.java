package com.jabberpoint.slide;

import com.jabberpoint.presentation.Presentation;
import com.jabberpoint.controller.KeyController;
import com.jabberpoint.controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.jabberpoint.Constants.JAB_VERSION;

/**
 * <p>The application window for a slide view component</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame {
    private final Dimension dimension;
    private final SlideViewerComponent slideViewerComponent;

    public SlideViewerFrame(String title, Dimension dimension) {
        super(title);
        this.dimension = dimension;
        this.slideViewerComponent = new SlideViewerComponent(this);
    }

    public void setupWindow(Presentation presentation) {
        presentation.subscribe(this.slideViewerComponent);
        setTitle(JAB_VERSION);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                presentation.exit(0);
            }
        });
        getContentPane().add(this.slideViewerComponent);
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
        setSize(dimension);
        setVisible(true);
    }
}
