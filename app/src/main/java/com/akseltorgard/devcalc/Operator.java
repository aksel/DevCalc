package com.akseltorgard.devcalc;

enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    INCREMENT("++"),
    DECREMENT("--"),
    OR("OR"),
    XOR("XOR"),
    AND("AND"),
    NOT("NOT"),
    LEFT_SHIFT("<<"),
    RIGHT_SHIFT(">>");

    String mSign;
    Operator(String sign) {
        mSign = sign;
    }
}