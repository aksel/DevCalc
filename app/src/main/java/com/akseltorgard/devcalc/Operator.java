package com.akseltorgard.devcalc;

enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    OR("OR"),
    XOR("XOR"),
    AND("AND");

    private String mSign;
    Operator(String sign) {
        mSign = sign;
    }

    /**
     * Returns Operator based on String sign
     * @param sign Sign of desired operator.
     * @return Operator.
     */
    static Operator fromStringSign(String sign) {
        switch (sign) {
            case "+":
                return ADD;
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "OR":
                return OR;
            case "XOR":
                return XOR;
            case "AND":
                return AND;
            default:
                throw new IllegalArgumentException("Unknown sign: " + sign);
        }
    }

    @Override
    public String toString() {
        return mSign;
    }
}