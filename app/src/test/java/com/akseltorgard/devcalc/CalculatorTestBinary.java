package com.akseltorgard.devcalc;

import org.junit.Test;

import static com.akseltorgard.devcalc.Calculator.BIN;
import static org.junit.Assert.assertEquals;

public class CalculatorTestBinary {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        calculator.inputNumber("1");

        assertEquals("1", calculator.getInput());

        calculator.inputNumber("0");

        assertEquals("10", calculator.getInput());
    }

    @Test
    public void testInput32Bits() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 32; i++) {
            calculator.inputNumber("1");
        }

        assertEquals("11111111 11111111 11111111 11111111", calculator.getInput());
    }

    @Test
    public void testInput33Bits() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 33; i++) {
            calculator.inputNumber("1");
        }

        assertEquals("11111111 11111111 11111111 11111111", calculator.getInput());
    }

    @Test
    public void testInputZerosThenOnes() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 10; i++) {
            calculator.inputNumber("0");
        }

        for (int i = 0; i < 10; i++) {
            calculator.inputNumber("1");
        }

        assertEquals("11 11111111", calculator.getInput());
    }

    @Test
    public void testInputFFThen00ThenFF() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 8; i++) {
            calculator.inputNumber("1");
        }

        for (int i = 0; i < 8; i++) {
            calculator.inputNumber("0");
        }

        for (int i = 0; i < 8; i++) {
            calculator.inputNumber("1");
        }

        assertEquals("11111111 00000000 11111111", calculator.getInput());
    }

    @Test
    public void testInputAlternating() {
        Calculator calculator = new Calculator();

        calculator.setBase(BIN);

        for (int i = 0; i < 32; i++) {
            if (i%2 == 0) {
                calculator.inputNumber("1");
            }

            else {
                calculator.inputNumber("0");
            }
        }

        assertEquals("10101010 10101010 10101010 10101010", calculator.getInput());
    }
}