package com.jabberpoint;

import com.jabberpoint.accessor.Accessor;
import com.jabberpoint.accessor.AccessorFactory;
import com.jabberpoint.accessor.XMLAccessorType;
import com.jabberpoint.presentation.Presentation;
import com.jabberpoint.slide.SlideViewerFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/** JabberPoint Main Programme
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class JabberPoint {
    public static void main(String[] argv) {
        Style.createStyles();
        Dimension windowSize = new Dimension(1200, 800);
        SlideViewerFrame slideViewerFrame = new SlideViewerFrame(Constants.JAB_VERSION, windowSize);
        Presentation presentation = new Presentation(slideViewerFrame);

        slideViewerFrame.setupWindow(presentation.getSlideViewerComponent(), presentation);

        try {
            Accessor accessor = AccessorFactory.createAccessor(
                    argv.length == 0 ? XMLAccessorType.DEMO : XMLAccessorType.XML);
            presentation.load(accessor, argv.length == 0 ? "" : argv[0]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, Constants.Error.IO_ERR + ex, Constants.Error.JABBER,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
