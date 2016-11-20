package com.akseltorgard.devcalc.operator;

public enum UnaryOperator {
    INCREMENT("++") {
        @Override
        public int operate(int operand) {
            return operand + 1;
        }
    },
    DECREMENT("--") {
        @Override
        public int operate(int operand) {
            return operand - 1;
        }
    },
    NOT("NOT") {
        @Override
        public int operate(int operand) {
            return ~operand;
        }
    },
    LEFT_SHIFT("<<") {
        @Override
        public int operate(int operand) {
            operand <<= 1;
            return operand;
        }
    },
    RIGHT_SHIFT(">>") {
        @Override
        public int operate(int operand) {
            operand >>>= 1;
            return operand;
        }
    };

    private String mSign;

    UnaryOperator(String sign) {
        mSign = sign;
    }

    public abstract int operate(int operand);

    @Override
    public String toString() {
        return mSign;
    }
}
