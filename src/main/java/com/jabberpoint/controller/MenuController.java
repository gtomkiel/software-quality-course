package com.jabberpoint.controller;

import com.jabberpoint.Presentation;
import com.jabberpoint.accessor.XMLAccessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

import static com.jabberpoint.Constants.Error.*;
import static com.jabberpoint.Constants.Menu.*;
import static com.jabberpoint.Constants.SAVE_FILE;
import static com.jabberpoint.Constants.TEST_FILE;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class MenuController extends MenuBar {
    @Serial
    private static final long serialVersionUID = 227L;
    private final Frame parent;
    private final Presentation presentation;

    public MenuController(Frame frame, Presentation presentation) {
        this.parent = frame;
        this.presentation = presentation;

        add(createFileMenu());
        add(createViewMenu());
        setHelpMenu(createHelpMenu());
    }

    private Menu createFileMenu() {
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(createMenuItem(OPEN, this::openFile));
        fileMenu.add(createMenuItem(NEW, this::newFile));
        fileMenu.add(createMenuItem(SAVE, this::saveFile));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem(EXIT, _ -> presentation.exit(0)));

        return fileMenu;
    }

    private Menu createViewMenu() {
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(createMenuItem(NEXT, _ -> presentation.nextSlide()));
        viewMenu.add(createMenuItem(PREV, _ -> presentation.prevSlide()));
        viewMenu.add(createMenuItem(GOTO, this::gotoPage));

        return viewMenu;
    }

    private Menu createHelpMenu() {
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(createMenuItem(ABOUT, _ -> AboutBox.show(parent)));

        return helpMenu;
    }

    private MenuItem createMenuItem(String name, ActionListener actionListener) {
        MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
        menuItem.addActionListener(actionListener);

        return menuItem;
    }

    private void openFile(ActionEvent e) {
        presentation.clear();

        try {
            presentation.load(new XMLAccessor(), TEST_FILE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IO_EX + exc, LOAD_ERR, JOptionPane.ERROR_MESSAGE);
        }

        parent.repaint();
    }

    private void newFile(ActionEvent e) {
        presentation.clear();
        parent.repaint();
    }

    private void saveFile(ActionEvent e) {
        try {
            presentation.save(new XMLAccessor(), SAVE_FILE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IO_EX + exc, SAVE_ERR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gotoPage(ActionEvent e) {
        String pageNumberStr = JOptionPane.showInputDialog(PAGE_NR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        presentation.setSlideNumber(pageNumber - 1);
    }
}
