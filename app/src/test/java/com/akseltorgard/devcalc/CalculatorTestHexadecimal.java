package com.akseltorgard.devcalc;

import org.junit.Test;

import static com.akseltorgard.devcalc.Base.HEX;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CalculatorTestHexadecimal {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        calculator.inputDigit("f");

        assertEquals("f", calculator.getInputString());

        calculator.inputDigit("f");

        assertEquals("ff", calculator.getInputString());
    }

    @Test
    public void testInputFFFFFFFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 8; i++) {
            calculator.inputDigit("f");
        }

        assertEquals("ff ff ff ff", calculator.getInputString());
    }

    @Test
    public void testInputFFFFFFFFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 9; i++) {
            calculator.inputDigit("f");
        }

        assertEquals("ff ff ff ff", calculator.getInputString());
    }

    @Test
    public void testInput00FF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 2; i++) {
            calculator.inputDigit("0");
        }

        for (int i = 0; i < 2; i++) {
            calculator.inputDigit("f");
        }

        assertEquals("ff", calculator.getInputString());
    }

    @Test
    public void testInputFFThen00ThenFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        calculator.inputDigit("f");
        calculator.inputDigit("f");

        calculator.inputDigit("0");
        calculator.inputDigit("0");

        calculator.inputDigit("f");
        calculator.inputDigit("f");

        assertEquals("ff 00 ff", calculator.getInputString());
    }

    @Test
    public void testInputAlternating() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                calculator.inputDigit("f");
            }

            else {
                calculator.inputDigit("0");
            }
        }

        assertEquals("f0 f0 f0 f0", calculator.getInputString());
    }

    @Test
    public void testInputFto8() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        String input = "fedcba98";

        for (char h : input.toCharArray()) {
            calculator.inputDigit(h + "");
        }

        assertEquals("fe dc ba 98", calculator.getInputString());
    }

    @Test
    public void testInput7to0() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        String input = "76543210";

        for (char h : input.toCharArray()) {
            calculator.inputDigit(h + "");
        }

        assertEquals("76 54 32 10", calculator.getInputString());
    }

    @Test
    public void testBackspace() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        calculator.inputDigit("f");
        calculator.inputDigit("f");
        calculator.inputDigit("f");

        assertEquals("f ff", calculator.getInputString());

        calculator.backspace();

        assertEquals("ff", calculator.getInputString());

        calculator.backspace();
        calculator.backspace();

        assertEquals("0", calculator.getInputString());

        calculator.backspace();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testHexStrings() {
        Calculator calculator = new Calculator();
        calculator.setBase(HEX);

        String input = "4FEFF";
        for (int i = 0, size = input.length(); i < size; i++) {
            calculator.inputDigit(String.valueOf(input.charAt(i)));
        }

        assertArrayEquals(new String[]{"FF", "FE", "04", "00"}, calculator.getHexStrings());
    }
}
