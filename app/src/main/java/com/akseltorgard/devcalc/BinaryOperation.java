package com.akseltorgard.devcalc;

class BinaryOperation {

    private Operator mOperator;
    private Integer mOperandA;
    private Integer mOperandB;

    int calculate() {
        int result = 0;
        switch (mOperator) {
            case ADD:
                result = mOperandA + mOperandB;
                break;
            case SUBTRACT:
                result = mOperandA - mOperandB;
                break;
            case MULTIPLY:
                result = mOperandA * mOperandB;
                break;
            case DIVIDE:
                result = mOperandA / mOperandB;
                break;
            case OR:
                result = mOperandA | mOperandB;
                break;
            case XOR:
                result = mOperandA ^ mOperandB;
                break;
            case AND:
                result = mOperandA & mOperandB;
                break;
        }

        reset();

        return result;
    }

    private void reset() {
        mOperator = null;
        mOperandA = null;
        mOperandB = null;
    }

    void setOperandA(int operandA) {
        mOperandA = operandA;
    }

    void setOperandB(int operandB) {
        mOperandB = operandB;
    }

    void setOperator(Operator operator) {
        mOperator = operator;
    }

    boolean hasOperator() {
        return mOperator != null;
    }

    @Override
    public String toString() {
        if (mOperandA != null && mOperator != null) {
            return mOperandA + "\n" + mOperator;
        }

        return "";
    }
}