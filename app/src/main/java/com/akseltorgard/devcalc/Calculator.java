package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

import static com.akseltorgard.devcalc.Base.*;

class Calculator implements Parcelable{

    private BinaryOperation mBinaryOperation;

    /**
     * Current calculator input.
     */
    private Integer mInput;

    /**
     * Base to expect in inputDigit(), and to produce in getInputString().
     */
    private Base mBase;

    Calculator() {
        mBinaryOperation = new BinaryOperation();
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
        mBinaryOperation.setOperandB(mInput);
        mInput = mBinaryOperation.calculate();
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
        return mBinaryOperation.toString();
    }

    String[] getHexStrings() {
        return NumberStringUtils.intToHexStringArray(mInput);
    }


    /**
     * Returns string that represents mInput in base mBase, formatted if need be.
     * @return String of mInput in mBase.
     */
    String getInputString() {
        return NumberStringUtils.intToString(mInput, mBase);
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
        if (!mBinaryOperation.hasOperator()) {
            mBinaryOperation.setOperator(operator);
            mBinaryOperation.setOperandA(mInput);
            mInput = null;
            return true;
        }

        else if (mInput == null) {
            mBinaryOperation.setOperator(operator);
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