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

    @Override
    public String toString() {
        return mSign;
    }
}