package com.akseltorgard.devcalc;

public class Calculator {
    /**
     * Current calculator input.
     */
    private String mInput;

    /**
     * Operand. Is set to mInput when a operator is pressed.
     */
    private int mOperand;

    private Operator mOperator;

    /**
     * Appends number to mInput if possible. Returns false if max number of digits has been
     * reached for current input type (dec = 10, hex = 8, bin = 32). Otherwise returns true.
     * @param number Number to append.
     * @return Number was successfully appended.
     */
    public boolean inputNumber(String number) {
        //TODO: Verify number can be appended.
        mInput = mInput + number;
        return true;
    }

    public void setOperator(Operator operator) {
        mOperator = operator;
        mOperand = Integer.parseInt(mInput);
        mInput = "";
    }

    public String getInput() {
        return mInput;
    }
}