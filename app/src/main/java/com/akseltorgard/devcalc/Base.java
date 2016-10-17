package com.akseltorgard.devcalc;

enum Base {
    BIN(2, 1),
    DEC(10),
    HEX(16, 4);

    private int mBase;
    private int mDigitSize;

    Base(int base) {
        mBase = base;
    }

    Base(int base, int digitSize) {
        mBase = base;
        mDigitSize = digitSize;
    }

    public int toInt() {
        return mBase;
    }

    public int getDigitSize() {
        return mDigitSize;
    }

    static Base fromIntBase(int base) {
        switch (base) {
            case 2:
                return BIN;
            case 10:
                return DEC;
            case 16:
                return HEX;
            default:
                throw new IllegalArgumentException("Invalid base: " + base);
        }
    }
}