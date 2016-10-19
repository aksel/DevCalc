package com.akseltorgard.devcalc;

import org.junit.Test;

import static com.akseltorgard.devcalc.Base.HEX;
import static org.junit.Assert.assertEquals;
import static com.akseltorgard.devcalc.Operators.BinaryOperator.*;
import static com.akseltorgard.devcalc.Operators.UnaryOperator.*;

public class CalculatorTestCalculations {

    @Test
    public void testAddition() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");

        calculator.setOperator(ADD);

        assertEquals("0", calculator.getInputString());

        calculator.inputDigit("2");

        calculator.calculateBinaryOperation();

        assertEquals("4", calculator.getInputString());

        calculator.clear();

        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(ADD);

        calculator.inputDigit("1");
        calculator.inputDigit("0");

        calculator.calculateBinaryOperation();

        assertEquals("5", calculator.getInputString());
    }

    @Test
    public void testAddPositiveToNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(ADD);

        calculator.inputDigit("1");
        calculator.inputDigit("0");

        calculator.calculateBinaryOperation();

        assertEquals("5", calculator.getInputString());
    }

    @Test
    public void testAddNegativeToPositive() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("1");
        calculator.inputDigit("0");

        calculator.setOperator(ADD);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("5", calculator.getInputString());
    }

    @Test
    public void testAddNegativeToNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(ADD);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("-10", calculator.getInputString());
    }

    @Test
    public void testSubtract() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("2");

        calculator.setOperator(SUBTRACT);

        calculator.inputDigit("4");

        calculator.calculateBinaryOperation();

        assertEquals("-2", calculator.getInputString());
    }

    @Test
    public void testSubtractPositiveFromNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(SUBTRACT);

        calculator.inputDigit("1");
        calculator.inputDigit("0");

        calculator.calculateBinaryOperation();

        assertEquals("-15", calculator.getInputString());
    }

    @Test
    public void testSubtractNegativeFromPositive() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("1");
        calculator.inputDigit("0");

        calculator.setOperator(SUBTRACT);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("15", calculator.getInputString());
    }

    @Test
    public void testSubtractNegativeFromNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(SUBTRACT);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("0", calculator.getInputString());
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

        calculator.calculateBinaryOperation();

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

        calculator.calculateBinaryOperation();

        assertEquals("16", calculator.getInputString());

        calculator.setOperator(MULTIPLY);

        calculator.inputDigit("0");

        calculator.calculateBinaryOperation();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testMultiplyNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(MULTIPLY);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("25", calculator.getInputString());
    }

    @Test
    public void testMultiplyPositiveAndNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(MULTIPLY);

        calculator.inputDigit("5");

        calculator.calculateBinaryOperation();

        assertEquals("-25", calculator.getInputString());

        calculator.clear();

        calculator.inputDigit("5");

        calculator.setOperator(MULTIPLY);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("-25", calculator.getInputString());
    }

    @Test
    public void testDivide() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("8");

        calculator.setOperator(DIVIDE);

        calculator.inputDigit("2");

        calculator.calculateBinaryOperation();

        assertEquals("4", calculator.getInputString());

        calculator.setOperator(DIVIDE);

        calculator.inputDigit("8");

        calculator.calculateBinaryOperation();

        assertEquals("0", calculator.getInputString());
    }

    @Test
    public void testDividePositiveWithNegative() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("5");

        calculator.setOperator(DIVIDE);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("-1", calculator.getInputString());
    }

    @Test
    public void testDivideNegativeWithPositive() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(DIVIDE);

        calculator.inputDigit("5");

        calculator.calculateBinaryOperation();

        assertEquals("-1", calculator.getInputString());
    }

    @Test
    public void testDivideNegativeWithNegative() {
        Calculator calculator = new Calculator();

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.setOperator(DIVIDE);

        //Set input to -5
        calculator.calculateUnaryOperation(NOT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);
        calculator.calculateUnaryOperation(DECREMENT);

        calculator.calculateBinaryOperation();

        assertEquals("1", calculator.getInputString());
    }

    @Test
    public void testDivideZero() {
        Calculator calculator = new Calculator();

        calculator.inputDigit("1");

        calculator.setOperator(DIVIDE);

        calculator.calculateBinaryOperation();

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

        calculator.calculateBinaryOperation();

        assertEquals("aa 5a ff", calculator.getInputString());

        calculator.setOperator(OR);
        calculator.inputDigit("5");
        calculator.inputDigit("5");
        calculator.inputDigit("A");
        calculator.inputDigit("5");
        calculator.inputDigit("1");
        calculator.inputDigit("1");
        calculator.calculateBinaryOperation();

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

        calculator.calculateBinaryOperation();

        assertEquals("ff 3c", calculator.getInputString());

        calculator.setOperator(XOR);

        calculator.inputDigit("F");
        calculator.inputDigit("0");
        calculator.inputDigit("0");

        calculator.calculateBinaryOperation();

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

        calculator.calculateBinaryOperation();

        assertEquals("c3", calculator.getInputString());

        calculator.setOperator(AND);

        calculator.inputDigit("2");

        calculator.calculateBinaryOperation();

        assertEquals("2", calculator.getInputString());
    }
}