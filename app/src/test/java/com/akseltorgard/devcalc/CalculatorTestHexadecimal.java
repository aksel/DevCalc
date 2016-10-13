package com.akseltorgard.devcalc;

import org.junit.Test;

import static com.akseltorgard.devcalc.Calculator.HEX;
import static org.junit.Assert.assertEquals;

public class CalculatorTestHexadecimal {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        calculator.inputNumber("f");

        assertEquals("f", calculator.getInput());

        calculator.inputNumber("f");

        assertEquals("ff", calculator.getInput());
    }

    @Test
    public void testInputFFFFFFFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 8; i++) {
            calculator.inputNumber("f");
        }

        assertEquals("ffffffff", calculator.getInput());
    }

    @Test
    public void testInputFFFFFFFFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 9; i++) {
            calculator.inputNumber("f");
        }

        assertEquals("ffffffff", calculator.getInput());
    }

    @Test
    public void testInput00FF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 2; i++) {
            calculator.inputNumber("0");
        }

        for (int i = 0; i < 2; i++) {
            calculator.inputNumber("f");
        }

        assertEquals("ff", calculator.getInput());
    }

    @Test
    public void testInputFFThen00ThenFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        calculator.inputNumber("f");
        calculator.inputNumber("f");

        calculator.inputNumber("0");
        calculator.inputNumber("0");

        calculator.inputNumber("f");
        calculator.inputNumber("f");

        assertEquals("ff00ff", calculator.getInput());
    }

    @Test
    public void testInputAlternating() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                calculator.inputNumber("f");
            }

            else {
                calculator.inputNumber("0");
            }
        }

        assertEquals("f0f0f0f0", calculator.getInput());
    }

    @Test
    public void testInputFto8() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        String input = "fedcba98";

        for (char h : input.toCharArray()) {
            calculator.inputNumber(h + "");
        }

        assertEquals("fedcba98", calculator.getInput());
    }

    @Test
    public void testInput7to0() {
        Calculator calculator = new Calculator();

        calculator.setBase(HEX);

        String input = "76543210";

        for (char h : input.toCharArray()) {
            calculator.inputNumber(h + "");
        }

        assertEquals("76543210", calculator.getInput());
    }
}