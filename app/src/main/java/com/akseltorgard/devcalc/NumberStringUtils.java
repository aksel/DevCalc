package com.akseltorgard.devcalc;

class NumberStringUtils {

    /**
     * Adds spaces between digits at multiples of @spacing.
     * @param string String to format.
     * @param spacing Where to insert spaces.
     * @return Formatted string.
     */
    private static String formatStringSpacing(String string, int spacing) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = string.length()-spacing; i > 0; i-=spacing) {
            sb.insert(i, " ");
        }

        return sb.toString();
    }

    /**
     * Converts i to String of number in mBase.
     * @param i Integer to convert.
     * @return String of number in mBase.
     */
    static String intToString(Integer i, Base base) {
        if (i == null) {
            return "0";
        }

        switch (base) {
            case BIN:
                String binaryString = Integer.toBinaryString(i);
                return formatStringSpacing(binaryString, 8);
            case DEC:
                return Integer.toString(i);
            case HEX:
                String hexString = Integer.toHexString(i);
                return formatStringSpacing(hexString, 2);
            default:
                throw new NumberFormatException("Improper base: " + base);
        }
    }

    /**
     * Returns i as 4 hex strings.
     * @param i Integer to convert.
     * @return i as hex string.
     */
    static String[] intToHexStringArray(Integer i) {
        if (i == null || i == 0) {
            return new String[] {
                    "00","00","00","00"
            };
        }

        String[] hexStrings = new String[4];

        hexStrings[0] = byteToHexString((byte) (i & 0xff));
        hexStrings[1] = byteToHexString((byte) ((i >> 8) & 0xff));
        hexStrings[2] = byteToHexString((byte) ((i >> 16) & 0xff));
        hexStrings[3] = byteToHexString((byte) ((i >> 24) & 0xff));

        return hexStrings;
    }

    /**
     * Returns b, padded with 0 if length < 2.
     * @param b Byte to convert.
     * @return b Byte.
     */
    private static String byteToHexString(byte b) {
        return String.format("%2s",Integer.toHexString(b)).replace(" ", "0");
    }
}
