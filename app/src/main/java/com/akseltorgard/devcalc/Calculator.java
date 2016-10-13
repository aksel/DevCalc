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
    private int mMaxLength;

    private Operator mOperator;

    Calculator() {
        mInput = 0;
        mBase = DEC;
        mMaxLength = 10;
    }

    int getBase() {
        return mBase;
    }

    String getInput() {
        return Integer.toString(mInput, mBase);
    }

    /**
     * Appends number to mInput if possible. Returns false if max number of digits has been
     * reached for current input type (dec = 10, hex = 8, bin = 32). Otherwise returns true.
     * @param digitString Digit to append.
     * @return Number was successfully appended.
     */
    boolean inputNumber(String digitString) {

        int digit = Integer.parseInt(digitString, mBase);

        if (mInput == 0) {
            mInput = digit;
        }

        else {
            mInput = mInput * 10 + digit;
        }

        return true;
    }

    boolean setBase(int base) {
        if (base != mBase) {
            mBase = base;

            switch (base) {
                case BIN:
                    mMaxLength = 32;
                    break;
                case DEC:
                    mMaxLength = 10;
                    break;
                case HEX:
                    mMaxLength = 8;
                    break;
            }

            return true;
        }
        return false;
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