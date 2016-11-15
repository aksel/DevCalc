package com.akseltorgard.devcalc;

enum Base {
    BIN(2, 1) {
        @Override
        public int backspace(int i) {
            return i >>> this.getDigitSize();
        }
    },
    DEC(10) {
        @Override
        public int backspace(int i) {
            return i / 10;
        }
    },
    HEX(16, 4) {
        @Override
        public int backspace(int i) {
            return i >>> this.getDigitSize();
        }
    };

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

    public abstract int backspace(int i);
}
