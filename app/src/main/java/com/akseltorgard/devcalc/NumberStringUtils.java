package com.akseltorgard.devcalc;

class NumberStringUtils {

    /**
     * Returns i as 4 hex strings.
     * @param i Int to convert.
     * @return i as hex string.
     */
    static String[] intToHexStringArray(int i) {
        String[] hexStrings = new String[4];

        hexStrings[0] = intToHexString(i & 0xff);
        hexStrings[1] = intToHexString((i >> 8) & 0xff);
        hexStrings[2] = intToHexString((i >> 16) & 0xff);
        hexStrings[3] = intToHexString((i >> 24) & 0xff);

        return hexStrings;
    }

    /**
     * Returns i, padded with 0 if length < 2.
     * @param i Int to convert.
     * @return i Int as string.
     */
    private static String intToHexString(int i) {
        return String.format("%2s",Integer.toHexString(i)).replace(" ", "0");
    }
}
