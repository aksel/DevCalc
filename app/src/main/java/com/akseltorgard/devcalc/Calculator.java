package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

import static com.akseltorgard.devcalc.Base.*;

class Calculator implements Parcelable{

    /**
     * Current calculator input.
     */
    private int mInput;

    /**
     * Operand. Is set to mInput when a operator is pressed.
     */
    private int mOperand;

    /**
     * Base to expect in inputDigit(), and to produce in getInputString().
     */
    private Base mBase;

    private Operator mOperator;

    Calculator() {
        mInput = 0;
        mBase = DEC;
    }

    /**
     * Deletes last digit of mInput, based on mBase.
     * @return Whether or not mInput changed.
     */
    boolean backspace() {
        if (mInput == 0) {
            return false;
        }

        switch (mBase) {
            case BIN: case HEX:
                mInput >>>= mBase.getDigitSize();
                break;
            case DEC:
                mInput /= 10;
                break;
        }

        return true;
    }

    /**
     * Clears input, i.e. sets it to 0.
     * @return Whether or not mInput changed.
     */
    boolean clear() {
        if (mInput == 0) {
            return false;
        }

        else {
            mInput = 0;
            return true;
        }
    }

    /**
     * Adds spaces between digits at multiples of @spacing.
     * @param string String to format.
     * @param spacing Where to insert spaces.
     * @return Formatted string.
     */
    private String formatStringSpacing(String string, int spacing) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = string.length()-spacing; i > 0; i-=spacing) {
            sb.insert(i, " ");
        }

        return sb.toString();
    }

    Base getBase() {
        return mBase;
    }

    /**
     * Returns boolean[32], with state of all bits in mInput. true = 1, false = 0.
     * LSB of 32 bit integer is at index 0, MSB is at index 31.
     * @return State of bits in mInput.
     */
    boolean[] getBits() {
        boolean[] bits = new boolean[32];

        int bitIndex = 0;

        int val = mInput;
        while (val != 0) {
            bits[bitIndex] = (val & 1) == 1;
            bitIndex++;
            val >>>= 1;
        }

        return bits;
    }

    /**
     * Returns mInput as hex string, padded with 0's until length == 8.
     * @return mInput as hex string.
     */
    String getHexString() {

        String hexString = Integer.toHexString(mInput).toUpperCase();

        while (hexString.length() < 8) {
            hexString = "0" + hexString;
        }

        return hexString;
    }

    /**
     * Returns string that represents mInput in base mBase, formatted if need be.
     * @return String of mInput in mBase.
     */
    String getInputString() {
        return intToString(mInput);
    }

    /**
     * Returns string that represents mOperand in base mBase, formatted if need be.
     * @return String of mOperand in mBase.
     */
    String getOperandString() {
        return intToString(mOperand);
    }

    String getOperatorString() {
        if (mOperator == null) {
            return "";
        }

        else {
            return mOperator.toString();
        }
    }

    /**
     * Appends digit to mInput.
     * @param digitString Digit to append.
     * @return Digit was appended to mInput.
     */
    boolean inputDigit(String digitString) {
        int digit = Integer.parseInt(digitString, mBase.toInt());

        if (mInput == 0) {
            mInput = digit;
        }

        else {
            switch (mBase) {
                case BIN: case HEX:
                    return inputInBase(digit, mBase.getDigitSize());
                case DEC:
                    return inputDecimal(digit);
            }
        }

        return true;
    }

    /**
     * Appends digit to mInput.
     * @param digit Digit to append.
     * @param digitSize Number of bits in digit.
     * @return Digit was appended to mInput.
     */
    private boolean inputInBase(int digit, int digitSize) {
        //Count number of bits already input in mInput.
        int val = mInput;
        int count;
        for (count = 0; val != 0; count++) {
            val >>>= digitSize;
        }

        //Max number of digits for 32 bit integer have been input.
        //Number of digits that fit into integer is 32 / digitSize,
        //e.g. 32 for bin (digit size 1), and 8 for hex (digit size 4).
        if (count == 32 / digitSize) {
            return false;
        }

        //Shift mInput by 1, and OR digit
        mInput <<= digitSize;
        mInput |= digit;

        return true;
    }

    /**
     * Appends digit to mInput.
     * @param digit Digit to append.
     * @return Digit was appended to mInput.
     */
    private boolean inputDecimal(int digit) {
        if (mInput == Integer.MAX_VALUE ||
                mInput > (Integer.MAX_VALUE / 10) ||
                digit > Integer.MAX_VALUE - mInput*10) {
            return false;
        }

        mInput = mInput * 10 + digit;

        return true;
    }

    /**
     * Converts i to String of number in mBase.
     * @param i Integer to convert.
     * @return String of number in mBase.
     */
    private String intToString(int i) {
        switch (mBase) {
            case BIN:
                String binaryString = Integer.toBinaryString(i);
                return formatStringSpacing(binaryString, 8);
            case DEC:
                return Integer.toString(i);
            case HEX:
                String hexString = Integer.toHexString(i);
                return formatStringSpacing(hexString, 2);
            default:
                throw new NumberFormatException("Improper base: " + mBase);
        }
    }

    boolean setBase(Base base) {
        if (base == mBase) {
            return false;
        }

        mBase = base;
        return true;
    }

    boolean setOperator(Operator operator) {
        mOperator = operator;
        mOperand = mInput;
        return false;
    }

    void toggleBit(int bitIndex) {
        mInput ^= (1 << bitIndex);
    }

    //Below this line is the implementation of Parcelable.

    private Calculator(Parcel in) {
        mInput = in.readInt();
        mOperand = in.readInt();
        mBase = Base.fromIntBase(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInput);
        dest.writeInt(mOperand);
        dest.writeInt(mBase.toInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };
}