package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

import static com.akseltorgard.devcalc.Base.*;

class Calculator implements Parcelable{

    private Calculation mCalculation;

    /**
     * Current calculator input.
     */
    private Integer mInput;

    /**
     * Base to expect in inputDigit(), and to produce in getInputString().
     */
    private Base mBase;

    Calculator() {
        mCalculation = new Calculation();
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

    void calculate() {
        mCalculation.setOperandB(mInput);
        mInput = mCalculation.calculate();
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

    String getCalculationString() {
        return mCalculation.toString();
    }

    /**
     * Returns mInput as hex string, padded with 0's until length == 8.
     * @return mInput as hex string.
     */
    String[] getHexStrings() {

        String[] hexStrings = new String[4];

        hexStrings[0] = String.format("%2s",Integer.toHexString(mInput & 0xff)).replace(" ", "0");
        hexStrings[1] = String.format("%2s",Integer.toHexString((mInput >> 8) & 0xff)).replace(" ", "0");
        hexStrings[2] = String.format("%2s",Integer.toHexString((mInput >> 16)& 0xff)).replace(" ", "0");
        hexStrings[3] = String.format("%2s",Integer.toHexString((mInput >> 24)& 0xff)).replace(" ", "0");

        return hexStrings;
    }


    /**
     * Returns string that represents mInput in base mBase, formatted if need be.
     * @return String of mInput in mBase.
     */
    String getInputString() {
        if (mInput == null) {
            return "0";
        }
        return intToString(mInput);
    }

    /**
     * Appends digit to mInput.
     * @param digitString Digit to append.
     * @return Digit was appended to mInput.
     */
    boolean inputDigit(String digitString) {
        int digit = Integer.parseInt(digitString, mBase.toInt());

        if (mInput == null || mInput == 0) {
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

    /**
     * Sets operator. Returns whether display needs to be updated.
     * @param operator Operator.
     * @return Display needs to be updated.
     */
    boolean setOperator(Operator operator) {
        if (!mCalculation.hasOperator()) {
            mCalculation.setOperator(operator);
            mCalculation.setOperandA(mInput);
            mInput = null;
            return true;
        }

        else if (mInput == null) {
            mCalculation.setOperator(operator);
            return false;
        }

        else {
            calculate();
            return setOperator(operator);
        }
    }

    void toggleBit(int bitIndex) {
        mInput ^= (1 << bitIndex);
    }

    //Below this line is the implementation of Parcelable.

    private Calculator(Parcel in) {
        mInput = in.readInt();
        mBase = Base.fromIntBase(in.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInput);
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