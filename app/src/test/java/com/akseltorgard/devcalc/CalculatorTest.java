package com.akseltorgard.devcalc;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void testInputDecimal() {
        Calculator calculator = new Calculator();

        calculator.inputNumber("1");

        assertEquals("1", calculator.getInput());

        calculator.inputNumber("1");

        assertEquals("11", calculator.getInput());
    }
}
