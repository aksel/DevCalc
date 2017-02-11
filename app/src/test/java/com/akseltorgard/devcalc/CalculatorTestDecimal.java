package com.akseltorgard.devcalc;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTestDecimal {

    @Test
    public void testInput() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("1");

        assertEquals("1", calculator.getInputString());

        calculator.inputDigit("1");

        assertEquals("11", calculator.getInputString());
    }

    @Test
    public void testTooMuchInput() {
        Calculator calculator = new Calculator();

        for (int i = 0; i < 25; i++) {
            calculator.inputDigit("1");
        }

        assertEquals("1111111111", calculator.getInputString());
    }

    @Test
    public void testInputMaxValue() {
        String maxValue = "2147483647";

        Calculator calculator = new Calculator();

        for (char c : maxValue.toCharArray()) {
            calculator.inputDigit(c + "");
        }

        assertEquals(maxValue, calculator.getInputString());
    }

    @Test
    public void testInputMaxValuePlus1() {
        String inputValue = "2147483648";

        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputDigit(c + "");
        }

        assertEquals("214748364", calculator.getInputString());
    }

    @Test
    public void testInputMaxValueMinus1() {
        String inputValue = "2147483646";
        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputDigit(c + "");
        }

        assertEquals("2147483646", calculator.getInputString());
    }

    @Test
    public void testInputAboveMaxThenMax() {
        String inputValue = "2147483648";
        Calculator calculator = new Calculator();

        for (char c : inputValue.toCharArray()) {
            calculator.inputDigit(c + "");
        }

        assertEquals("214748364", calculator.getInputString());

        calculator.inputDigit("7");

        assertEquals("2147483647", calculator.getInputString());

        calculator.inputDigit("1");

        assertEquals("2147483647", calculator.getInputString());
    }

    @Test
    public void testBackspace() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("9");
        calculator.inputDigit("8");
        calculator.inputDigit("7");

        assertEquals("987", calculator.getInputString());

        calculator.backspace();

        assertEquals("98", calculator.getInputString());

        calculator.backspace();
        calculator.backspace();

        assertEquals("0", calculator.getInputString());

        calculator.backspace();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testHexStrings() {
        Calculator calculator = new Calculator();

        String input = "327423";
        for (int i = 0, size = input.length(); i < size; i++) {
            calculator.inputDigit(String.valueOf(input.charAt(i)));
        }

        assertArrayEquals(new String[]{"FF", "FE", "04", "00"}, calculator.getHexStrings());
    }
}
