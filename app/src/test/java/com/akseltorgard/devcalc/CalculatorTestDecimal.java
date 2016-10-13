package com.akseltorgard.devcalc;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTestDecimal {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.inputNumber("1");

        assertEquals("1", calculator.getInput());

        calculator.inputNumber("1");

        assertEquals("11", calculator.getInput());
    }

    @Test
    public void testTooMuchInput() {
        Calculator calculator = new Calculator();

        for (int i = 0; i < 25; i++) {
            calculator.inputNumber("1");
        }

        assertEquals("1111111111", calculator.getInput());
    }

    @Test
    public void testInputMaxValue() {
        String maxValue = "2147483647";

        Calculator calculator = new Calculator();

        for (char c : maxValue.toCharArray()) {
            calculator.inputNumber(c + "");
        }

        assertEquals(maxValue, calculator.getInput());
    }

    @Test
    public void testInputMaxValuePlus1() {
        String inputValue = "2147483648";

        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputNumber(c + "");
        }

        assertEquals("214748364", calculator.getInput());
    }

    @Test
    public void testInputMaxValueMinus1() {
        String inputValue = "2147483646";
        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputNumber(c + "");
        }

        assertEquals("2147483646", calculator.getInput());
    }

    @Test
    public void testInputAboveMaxThenMax() {
        String inputValue = "2147483648";
        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputNumber(c + "");
        }

        assertEquals("214748364", calculator.getInput());

        calculator.inputNumber("7");

        assertEquals("2147483647", calculator.getInput());

        calculator.inputNumber("1");

        assertEquals("2147483647", calculator.getInput());
    }
}