package com.akseltorgard.devcalc;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NumberStringUtilsTest {

    @Test
    public void testIntToBinaryString() {
        assertEquals("0", NumberStringUtils.intToString(0, Base.BIN));
        assertEquals("11111111", NumberStringUtils.intToString(0xff, Base.BIN));
        assertEquals("11111111 00000000", NumberStringUtils.intToString(0xff00, Base.BIN));
        assertEquals("11111111 00000000 00000000", NumberStringUtils.intToString(0xff0000, Base.BIN));
        assertEquals("11111111 00000000 00000000 00000000", NumberStringUtils.intToString(0xff000000, Base.BIN));
    }

    @Test
    public void testIntToHexString() {
        assertEquals("0", NumberStringUtils.intToString(0, Base.HEX));
        assertEquals("ff", NumberStringUtils.intToString(0xff, Base.HEX));
        assertEquals("ff 00", NumberStringUtils.intToString(0xff00, Base.HEX));
        assertEquals("ff 00 00", NumberStringUtils.intToString(0xff0000, Base.HEX));
        assertEquals("ff 00 00 00", NumberStringUtils.intToString(0xff000000, Base.HEX));
    }

    @Test
    public void testIntToHexStringArray() {
        assertArrayEquals(new String[]{"00", "00", "00", "00"}, NumberStringUtils.intToHexStringArray(0));
        assertArrayEquals(new String[]{"ff", "00", "00", "00"}, NumberStringUtils.intToHexStringArray(0xff));
        assertArrayEquals(new String[]{"00", "ff", "00", "00"}, NumberStringUtils.intToHexStringArray(0xff00));
        assertArrayEquals(new String[]{"00", "00", "ff", "00"}, NumberStringUtils.intToHexStringArray(0xff0000));
        assertArrayEquals(new String[]{"00", "00", "00", "ff"}, NumberStringUtils.intToHexStringArray(0xff000000));
        assertArrayEquals(new String[]{"ff", "ff", "ff", "ff"}, NumberStringUtils.intToHexStringArray(0xffffffff));
    }
}