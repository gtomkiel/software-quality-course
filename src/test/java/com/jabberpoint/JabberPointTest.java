package com.jabberpoint;

import org.junit.jupiter.api.Test;

import static com.jabberpoint.TestUtils.getTestXmlPath;

public class JabberPointTest {
    @Test
    public void testMain() {
        JabberPoint.main(new String[] { getTestXmlPath() });
    }

    @Test
    public void testMainNoArgs() {
        JabberPoint.main(new String[] {});
    }

    @Test
    public void testMainInvalidArgs() {
        JabberPoint.main(new String[] { "invalid" });
    }
}
