package com.akseltorgard.devcalc;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable{
    /**
     * Current calculator input.
     */
    private String mInput;

    /**
     * Operand. Is set to mInput when a operator is pressed.
     */
    private int mOperand;

    private Operator mOperator;

    Calculator() {
        mInput = "0";
    }
    
    /**
     * Appends number to mInput if possible. Returns false if max number of digits has been
     * reached for current input type (dec = 10, hex = 8, bin = 32). Otherwise returns true.
     * @param number Number to append.
     * @return Number was successfully appended.
     */
    boolean inputNumber(String number) {
        //TODO: Verify number can be appended.

        if (mInput.equals("0")) {
            mInput = number;
        }

        else {
            mInput = mInput + number;
        }

        return true;
    }

    public void setOperator(Operator operator) {
        mOperator = operator;
        mOperand = Integer.parseInt(mInput);
        mInput = "";
    }

    String getInput() {
        return mInput;
    }

    private Calculator(Parcel p) {
        mInput = p.readString();
        mOperand = p.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mInput);
        dest.writeInt(mOperand);
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