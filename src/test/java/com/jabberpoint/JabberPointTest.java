package com.jabberpoint;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static com.jabberpoint.TestUtils.getTestXmlPath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JabberPointTest {
    @Test
    public void testMain() {
        if (!GraphicsEnvironment.isHeadless()) {
            JabberPoint.main(new String[] { getTestXmlPath() });
        } else {
            assertThat(GraphicsEnvironment.isHeadless()).isTrue();
        }
    }

    @Test
    public void testMainNoArgs() {
        if (!GraphicsEnvironment.isHeadless()) {
            JabberPoint.main(new String[] {});
        } else {
            assertThat(GraphicsEnvironment.isHeadless()).isTrue();
        }
    }

    @Test
    public void testMainInvalidArgs() {
        if (!GraphicsEnvironment.isHeadless()) {
            JabberPoint.main(new String[] { "invalid" });
        } else {
            assertThat(GraphicsEnvironment.isHeadless()).isTrue();
        }
    }
}
