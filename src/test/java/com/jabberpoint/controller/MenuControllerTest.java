package com.jabberpoint.controller;

import com.jabberpoint.presentation.Presentation;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

class MenuControllerTest {
    private Presentation presentation;
    private MenuController controller;

    @BeforeEach
    void setUp() {
        Frame frame = mock(Frame.class);
        presentation = mock(Presentation.class);
        controller = new MenuController(frame, presentation);
    }

    @Test
    void testOpenFile() throws IOException {
        MenuItem openMenuItem = controller.getMenu(0).getItem(0);
        openMenuItem.getActionListeners()[0].actionPerformed(null);

        verify(presentation).load(any(), anyString());
    }

    @Test
    void testNewFile() {
        MenuItem newMenuItem = controller.getMenu(0).getItem(1);
        newMenuItem.getActionListeners()[0].actionPerformed(null);

        verify(presentation).clear();
    }

    @Test
    void testSaveFile() throws IOException {
        MenuItem saveMenuItem = controller.getMenu(0).getItem(2);
        saveMenuItem.getActionListeners()[0].actionPerformed(null);

        verify(presentation).save(anyString());
    }

    @Test
    void testGotoPage() {
        Assumptions.assumeTrue(!GraphicsEnvironment.isHeadless());

        MenuItem gotoMenuItem = controller.getMenu(1).getItem(2);
        gotoMenuItem.getActionListeners()[0].actionPerformed(null);

        verify(presentation).setSlideNumber(anyInt());
    }
}
