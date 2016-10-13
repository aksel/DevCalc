package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

class Calculator implements Parcelable{

    static final int BIN = 2;
    static final int DEC = 10;
    static final int HEX = 16;

    /**
     * Current calculator input.
     */
    private int mInput;

    /**
     * Operand. Is set to mInput when a operator is pressed.
     */
    private int mOperand;

    private int mBase;

    private Operator mOperator;

    Calculator() {
        mInput = 0;
        mBase = DEC;
    }

    int getBase() {
        return mBase;
    }

    String getInput() {
        switch (mBase) {
            case BIN:
                return Integer.toBinaryString(mInput);
            case DEC:
                return Integer.toString(mInput);
            case HEX:
                return Integer.toHexString(mInput);
            default:
                throw new NumberFormatException("Improper base: " + mBase);
        }
    }

    /**
     * Appends digit to mInput.
     * mInput is multiplied by 10, and digit is added to it.
     * If the result would be greater than Integer.MAX_VALUE,
     * mInput instead becomes Integer.MAX_VALUE.
     * If mInput already is Integer.MAX_VALUE, nothing happens.
     * @param digitString Digit to append.
     * @return Number was successfully appended.
     */
    boolean inputNumber(String digitString) {

        int digit = Integer.parseInt(digitString, mBase);

        if (mInput == 0) {
            mInput = digit;
        }

        else {
            switch (mBase) {
                case BIN:
                    return inputBinary(digit);
                case DEC:
                    return inputDecimal(digit);
                case HEX:
                    return inputHexadecimal(digit);
            }
        }

        return true;
    }

    private boolean inputBinary(int digit) {
        //Count number of bits
        int val = mInput;
        int count;
        for (count = 0; val != 0; count++) {
            val >>>= 1;
        }

        //Max bits (32) have been input.
        if (count == 32) {
            return false;
        }

        //Shift mInput by 1, and OR digit
        mInput <<= 1;
        mInput |= digit;

        return true;
    }

    private boolean inputDecimal(int digit) {
        if (mInput == Integer.MAX_VALUE ||
                mInput > (Integer.MAX_VALUE / 10) ||
                digit > Integer.MAX_VALUE - mInput*10) {
            return false;
        }

        mInput = mInput * 10 + digit;

        return true;
    }

    private boolean inputHexadecimal(int digit) {
        //Count number of hex digits
        int val = mInput;
        int count;
        for (count = 0; val != 0; count++) {
            val >>>= 4;
        }

        //Has reached max hex digits
        if (count == 8) {
            return false;
        }

        mInput <<= 4;
        mInput |= digit;

        return true;
    }

    boolean setBase(int base) {
        if (base == mBase) {
            return false;
        }

        mBase = base;
        return true;
    }

    void setOperator(Operator operator) {
        mOperator = operator;
        mOperand = mInput;
    }

    /**
     * Below this line is the implementation of Parcelable.
     */

    private Calculator(Parcel in) {
        mInput = in.readInt();
        mOperand = in.readInt();
        mBase = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInput);
        dest.writeInt(mOperand);
        dest.writeInt(mBase);
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