package com.akseltorgard.devcalc;

enum Base {
    BIN(2, 1) {
        @Override
        public int backspace(int i) {
            return i >>> this.getDigitSize();
        }

        @Override
        public String toString(int i) {
            String binaryString = Integer.toBinaryString(i);
            return formatStringSpacing(binaryString, 8);
        }
    },
    DEC(10) {
        @Override
        public int backspace(int i) {
            return i / 10;
        }

        @Override
        public String toString(int i) {
            return Integer.toString(i);
        }
    },
    HEX(16, 4) {
        @Override
        public int backspace(int i) {
            return i >>> this.getDigitSize();
        }

        @Override
        public String toString(int i) {
            String hexString = Integer.toHexString(i);
            return formatStringSpacing(hexString, 2);
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

    /**
     * Converts i to String of number in given base.
     * @param i Int to convert.
     * @return String of number in given base.
     */
    public abstract String toString(int i);

    /**
     * Adds spaces between digits at multiples of @spacing.
     * @param string  String to format.
     * @param spacing Where to insert spaces.
     * @return Formatted string.
     */
    private static String formatStringSpacing(String string, int spacing) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = string.length() - spacing; i > 0; i -= spacing) {
            sb.insert(i, " ");
        }

        return sb.toString();
    }
}
