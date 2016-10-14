package com.akseltorgard.devcalc;

import org.junit.Test;

import java.util.Arrays;

import static com.akseltorgard.devcalc.Calculator.BIN;
import static com.akseltorgard.devcalc.Calculator.HEX;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CalculatorTestBinary {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        calculator.inputDigit("1");

        assertEquals("1", calculator.getInputString());

        calculator.inputDigit("0");

        assertEquals("10", calculator.getInputString());
    }

    @Test
    public void testInput32Bits() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 32; i++) {
            calculator.inputDigit("1");
        }

        assertEquals("11111111 11111111 11111111 11111111", calculator.getInputString());
    }

    @Test
    public void testInput33Bits() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 33; i++) {
            calculator.inputDigit("1");
        }

        assertEquals("11111111 11111111 11111111 11111111", calculator.getInputString());
    }

    @Test
    public void testInputZerosThenOnes() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 10; i++) {
            calculator.inputDigit("0");
        }

        for (int i = 0; i < 10; i++) {
            calculator.inputDigit("1");
        }

        assertEquals("11 11111111", calculator.getInputString());
    }

    @Test
    public void testInputFFThen00ThenFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 8; i++) {
            calculator.inputDigit("1");
        }

        for (int i = 0; i < 8; i++) {
            calculator.inputDigit("0");
        }

        for (int i = 0; i < 8; i++) {
            calculator.inputDigit("1");
        }

        assertEquals("11111111 00000000 11111111", calculator.getInputString());
    }

    @Test
    public void testInputAlternating() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 32; i++) {
            if (i%2 == 0) {
                calculator.inputDigit("1");
            }

            else {
                calculator.inputDigit("0");
            }
        }

        assertEquals("10101010 10101010 10101010 10101010", calculator.getInputString());
    }

    @Test
    public void testBackspace() {
        Calculator calculator = new Calculator();
        calculator.setBase(BIN);

        calculator.inputDigit("1");
        calculator.inputDigit("0");
        calculator.inputDigit("1");

        assertEquals("101", calculator.getInputString());

        calculator.backspace();

        assertEquals("10", calculator.getInputString());

        calculator.backspace();
        calculator.backspace();

        assertEquals("0", calculator.getInputString());

        calculator.backspace();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testGetBits() {
        Calculator calculator = new Calculator();
        calculator.setBase(HEX);

        calculator.inputDigit("f");
        calculator.inputDigit("f");
        calculator.inputDigit("0");
        calculator.inputDigit("0");
        calculator.inputDigit("f");
        calculator.inputDigit("f");
        calculator.inputDigit("0");
        calculator.inputDigit("0");

        boolean[] bits = calculator.getBits();

        boolean[] expected1 = {
                false, false, false, false, //0
                false, false, false, false, //0
                true, true, true, true,     //F
                true, true, true, true,     //F
                false, false, false, false, //0
                false, false, false, false,  //0
                true, true, true, true,     //F
                true, true, true, true     //F
        };

        assertArrayEquals(expected1, bits);

        calculator.backspace();
        calculator.backspace();
        calculator.backspace();
        calculator.backspace();

        boolean[] expected2 = {
                false, false, false, false, //0
                false, false, false, false, //0
                true, true, true, true,     //F
                true, true, true, true,     //F
                false, false, false, false, //0
                false, false, false, false, //0
                false, false, false, false, //0
                false, false, false, false  //0
        };

        bits = calculator.getBits();

        assertArrayEquals(expected2, bits);
    }
}