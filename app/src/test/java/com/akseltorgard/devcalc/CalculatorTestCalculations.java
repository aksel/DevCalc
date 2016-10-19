package com.akseltorgard.devcalc;

import org.junit.Test;

import static com.akseltorgard.devcalc.Base.HEX;
import static org.junit.Assert.assertEquals;
import static com.akseltorgard.devcalc.Operator.*;

public class CalculatorTestCalculations {

    @Test
    public void testAddition() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");

        calculator.setOperator(ADD);

        assertEquals("0", calculator.getInputString());

        calculator.inputDigit("2");

        calculator.calculate();

        assertEquals("4", calculator.getInputString());
    }

    @Test
    public void testSubtract() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");

        calculator.setOperator(SUBTRACT);

        calculator.inputDigit("4");

        calculator.calculate();

        assertEquals("-2", calculator.getInputString());
    }

    @Test
    public void testSwitchOperator() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");
        calculator.inputDigit("2");

        calculator.setOperator(ADD);

        assertEquals("22\n+", calculator.getCalculationString());

        calculator.setOperator(SUBTRACT);

        assertEquals("22\n-", calculator.getCalculationString());
        assertEquals("0", calculator.getInputString());

        calculator.inputDigit("2");

        calculator.calculate();

        assertEquals("", calculator.getCalculationString());
        assertEquals("20", calculator.getInputString());

        calculator.setOperator(ADD);

        calculator.inputDigit("2");

        calculator.setOperator(SUBTRACT);

        assertEquals("22\n-", calculator.getCalculationString());
        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testMultiply() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");

        calculator.setOperator(MULTIPLY);

        calculator.inputDigit("8");

        calculator.calculate();

        assertEquals("16", calculator.getInputString());

        calculator.setOperator(MULTIPLY);

        calculator.inputDigit("0");

        calculator.calculate();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testDivide() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("8");

        calculator.setOperator(DIVIDE);

        calculator.inputDigit("2");

        calculator.calculate();

        assertEquals("4", calculator.getInputString());

        calculator.setOperator(DIVIDE);

        calculator.inputDigit("8");

        calculator.calculate();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testOR() {
        Calculator calculator = new Calculator();
        calculator.setBase(HEX);

        calculator.inputDigit("F");
        calculator.inputDigit("F");

        calculator.setOperator(OR);

        calculator.inputDigit("A");
        calculator.inputDigit("A");
        calculator.inputDigit("5");
        calculator.inputDigit("A");
        calculator.inputDigit("0");
        calculator.inputDigit("0");

        calculator.calculate();

        assertEquals("aa 5a ff", calculator.getInputString());

        calculator.setOperator(OR);
        calculator.inputDigit("5");
        calculator.inputDigit("5");
        calculator.inputDigit("A");
        calculator.inputDigit("5");
        calculator.inputDigit("1");
        calculator.inputDigit("1");
        calculator.calculate();

        assertEquals("ff ff ff", calculator.getInputString());
    }

    @Test
    public void testXOR() {
        Calculator calculator = new Calculator();
        calculator.setBase(HEX);

        calculator.inputDigit("F");
        calculator.inputDigit("F");

        calculator.setOperator(XOR);

        calculator.inputDigit("F");
        calculator.inputDigit("F");
        calculator.inputDigit("C");
        calculator.inputDigit("3");

        calculator.calculate();

        assertEquals("ff 3c", calculator.getInputString());

        calculator.setOperator(XOR);

        calculator.inputDigit("F");
        calculator.inputDigit("0");
        calculator.inputDigit("0");

        calculator.calculate();

        assertEquals("f0 3c", calculator.getInputString());
    }

    @Test
    public void testAND() {
        Calculator calculator = new Calculator();
        calculator.setBase(HEX);

        calculator.inputDigit("F");
        calculator.inputDigit("F");

        calculator.setOperator(AND);

        calculator.inputDigit("C");
        calculator.inputDigit("3");

        calculator.calculate();

        assertEquals("c3", calculator.getInputString());

        calculator.setOperator(AND);

        calculator.inputDigit("2");

        calculator.calculate();

        assertEquals("2", calculator.getInputString());
    }
}