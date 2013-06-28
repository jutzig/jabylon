package de.jutzig.jabylon.ui.components;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StaticProgressIndicatorTest extends StaticProgressIndicator {

    @Test
    public void testCalculateBackgroundColor() {
        assertEquals(0xff0000, calculateBackgroundColor(0));
        assertEquals(0xffff00, calculateBackgroundColor(50));
        assertEquals(0xfbff00, calculateBackgroundColor(51));
        assertEquals(0x06ff00, calculateBackgroundColor(99));
        assertEquals(0x00ff00, calculateBackgroundColor(100));
    }

}
