package com.akseltorgard.devcalc;

class Operators {

    enum BinaryOperator {
        ADD("+") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 + operand2;
            }
        },
        SUBTRACT("-") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 - operand2;
            }
        },
        MULTIPLY("*") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 * operand2;
            }
        },
        DIVIDE("/") {
            @Override
            public int operate(int operand1, int operand2) {
                if (operand2 == 0) {
                    return 0;
                }
                return operand1 / operand2;
            }
        },
        OR("OR") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 | operand2;
            }
        },
        XOR("XOR") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 ^ operand2;
            }
        },
        AND("AND") {
            @Override
            public int operate(int operand1, int operand2) {
                return operand1 & operand2;
            }
        };

        private String mSign;

        BinaryOperator(String sign) {
            mSign = sign;
        }

        public abstract int operate(int operand1, int operand2);

        @Override
        public String toString() {
            return mSign;
        }
    }

    enum UnaryOperator {
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
}
