package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

import com.akseltorgard.devcalc.operator.BinaryOperator;
import com.akseltorgard.devcalc.operator.UnaryOperator;

import static com.akseltorgard.devcalc.Base.DEC;

class Calculator implements Parcelable{

    private int mOperand;
    private BinaryOperator mOperator;

    /**
     * Current calculator input.
     */
    private int mInput;

    /**
     * Base to expect in inputDigit(), and to produce in getInputString().
     */
    private Base mBase;

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

        mInput = mBase.backspace(mInput);

        return true;
    }

    void calculateBinaryOperation() {
        if (mOperator == null) {
            return;
        }

        mInput = mOperator.operate(mOperand, mInput);

        mOperand = 0;
        mOperator = null;
    }

    void calculateUnaryOperation(UnaryOperator operator) {
        mInput = operator.operate(mInput);
    }

    /**
     * Completely resets calculator state. Returns false if state is already clean.
     * @return Whether or not anything changed.
     */
    boolean clear() {
        if (mInput == 0 && mOperand == 0 && mOperator == null) {
            return false;
        }

        mInput = 0;
        mOperand = 0;
        mOperator = null;
        return true;
    }

    /**
     * Clears input by setting mInput to 0. Returns false if mInput is null or 0.
     * @return Whether mInput changed.
     */
    boolean clearEntry() {
        if (mInput == 0) {
            return false;
        }

        mInput = 0;
        return true;
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

        if (mInput == 0) {
            return bits;
        }

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
        if (mOperator != null) {
            return NumberStringUtils.intToString(mOperand, mBase) + "\n" + mOperator;
        }

        return "";
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

    boolean setBase(Base base) {
        if (base == mBase) {
            return false;
        }

        mBase = base;
        return true;
    }

    void setOperator(BinaryOperator operator) {
        if (mOperator == null) {
            mOperator = operator;
            mOperand = mInput;
            mInput = 0;
        }

        //Operator pressed before any digit has been input
        else if (mInput == 0) {
            mOperator = operator;
        }

        else {
            calculateBinaryOperation();
            setOperator(operator);
        }
    }

    void toggleBit(int bitIndex) {
        mInput ^= (1 << bitIndex);
    }

    //Below this line is the implementation of Parcelable.

    private Calculator(Parcel in) {
        mInput = in.readInt();
        mBase = Base.valueOf(in.readString());

        if (in.dataAvail() > 0) {
            mOperand = in.readInt();
            mOperator = BinaryOperator.valueOf(in.readString());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInput);
        dest.writeString(mBase.name());

        if (mOperand != 0) {
            dest.writeInt(mOperand);
            dest.writeString(mOperator.name());
        }
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
