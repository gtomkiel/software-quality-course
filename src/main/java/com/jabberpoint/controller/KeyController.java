package com.jabberpoint.controller;

import com.jabberpoint.Presentation;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

/** <p>This is the KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter {
    private final Presentation presentation;
    private final Map<Integer, Runnable> keyEventsMap;

    public KeyController(Presentation presentation) {
        this.presentation = presentation;
        keyEventsMap = new HashMap<>();
        setupKeyEventsMap();
    }

    private void setupKeyEventsMap() {
        keyEventsMap.put(KeyEvent.VK_PAGE_DOWN, presentation::nextSlide);
        keyEventsMap.put(KeyEvent.VK_DOWN, presentation::nextSlide);
        keyEventsMap.put(KeyEvent.VK_ENTER, presentation::nextSlide);
        keyEventsMap.put((int) '+', presentation::nextSlide);

        keyEventsMap.put(KeyEvent.VK_PAGE_UP, presentation::prevSlide);
        keyEventsMap.put(KeyEvent.VK_UP, presentation::prevSlide);
        keyEventsMap.put((int) '-', presentation::prevSlide);

        keyEventsMap.put((int) 'q', () -> System.exit(0));
        keyEventsMap.put((int) 'Q', () -> System.exit(0));
    }

    public void keyPressed(KeyEvent keyEvent) {
        Runnable action = keyEventsMap.get(keyEvent.getKeyCode());
        if (action != null) {
            action.run();
        }
    }
}
