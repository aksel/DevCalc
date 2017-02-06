package com.akseltorgard.devcalc;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class NumberStringUtilsTest {

    @Test
    public void testIntToBinaryString() {
        assertEquals("0", Base.BIN.toString(0));
        assertEquals("11111111", Base.BIN.toString(0xff));
        assertEquals("11111111 00000000", Base.BIN.toString(0xff00));
        assertEquals("11111111 00000000 00000000", Base.BIN.toString(0xff0000));
        assertEquals("11111111 00000000 00000000 00000000", Base.BIN.toString(0xff000000));
    }

    @Test
    public void testIntToHexString() {
        assertEquals("0", Base.HEX.toString(0));
        assertEquals("ff", Base.HEX.toString(0xff));
        assertEquals("ff 00", Base.HEX.toString(0xff00));
        assertEquals("ff 00 00", Base.HEX.toString(0xff0000));
        assertEquals("ff 00 00 00", Base.HEX.toString(0xff000000));
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
