package com.akseltorgard.devcalc;

class Operators {
    enum BinaryOperator {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        DIVIDE("/"),
        OR("OR"),
        XOR("XOR"),
        AND("AND");

        private String mSign;
        BinaryOperator(String sign) {
            mSign = sign;
        }

        /**
         * Returns BinaryOperator based on String sign
         * @param sign Sign of desired operator.
         * @return BinaryOperator.
         */
        static BinaryOperator fromStringSign(String sign) {
            for (BinaryOperator binaryOperator : BinaryOperator.values()) {
                if (binaryOperator.mSign.equals(sign)) {
                    return binaryOperator;
                }
            }
            throw new IllegalArgumentException("Unknown sign: " + sign);
        }

        @Override
        public String toString() {
            return mSign;
        }
    }

    enum UnaryOperator {
        INCREMENT("++"),
        DECREMENT("--"),
        NOT("NOT"),
        LEFT_SHIFT("<<"),
        RIGHT_SHIFT(">>");

        private String mSign;
        UnaryOperator(String sign) {
            mSign = sign;
        }

        @Override
        public String toString() {
            return mSign;
        }
    }
}
