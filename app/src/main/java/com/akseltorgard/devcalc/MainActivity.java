package com.akseltorgard.devcalc;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.akseltorgard.devcalc.operator.BinaryOperator;
import com.akseltorgard.devcalc.operator.UnaryOperator;

import static com.akseltorgard.devcalc.Base.*;
import static com.akseltorgard.devcalc.operator.BinaryOperator.*;
import static com.akseltorgard.devcalc.operator.UnaryOperator.*;

public class MainActivity extends AppCompatActivity {

    final String TAG = "DevCalc.MainActivity";
    final String KEY_CALCULATOR = "Key mCalculator";

    Calculator mCalculator;

    //DISPLAY MEMBERS
    TextView[] mHexTextViews;
    ToggleButton[] mBitButtons;
    TextView mInput;
    TextView mOperation;
    //END DISPLAY MEMBERS

    Button[] mNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCalculator = savedInstanceState.getParcelable(KEY_CALCULATOR);
        }

        else {
            mCalculator = new Calculator();
        }

        findViewById(R.id.button_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspace();
            }
        });

        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        
        findViewById(R.id.button_clear_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearEntry();
            }
        });

        initDisplay();
        initBaseButtons();
        initNumberButtons();
        initOperatorButtons();

        updateDisplay();
    }

    private void initDisplay() {
        int[] byteLayoutIds = {
                R.id.byte_1,
                R.id.byte_2,
                R.id.byte_3,
                R.id.byte_4
        };

        mHexTextViews = new TextView[byteLayoutIds.length];

        for (int i = 0; i < byteLayoutIds.length; i++) {
            mHexTextViews[i] = (TextView) findViewById(byteLayoutIds[i]).findViewById(R.id.display_hex);
        }

        View.OnClickListener bitButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBit((ToggleButton) v);
            }
        };

        int[] bitButtonIds = {
                R.id.bit_1,
                R.id.bit_2,
                R.id.bit_3,
                R.id.bit_4,
                R.id.bit_5,
                R.id.bit_6,
                R.id.bit_7,
                R.id.bit_8,
        };

        mBitButtons = new ToggleButton[bitButtonIds.length * byteLayoutIds.length];

        int bitIndex = 0;
        for (int byteLayoutId : byteLayoutIds) {
            for (int i = 0; i < bitButtonIds.length; i++, bitIndex++) {
                ToggleButton bitButton = (ToggleButton) findViewById(byteLayoutId).findViewById(bitButtonIds[i]);
                bitButton.setTag(bitIndex);
                bitButton.setOnClickListener(bitButtonListener);
                mBitButtons[bitIndex] = bitButton;
            }
        }

        mOperation = (TextView) findViewById(R.id.text_view_operation);

        mInput = (TextView) findViewById(R.id.text_view_input);
    }

    /**
     * Finds base radio buttons, sets onClickListener -> changeBase(base)
     */
    private void initBaseButtons() {
        RadioButton binButton = (RadioButton) findViewById(R.id.radio_button_bin);
        binButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBase(BIN);
            }
        });

        RadioButton decButton = (RadioButton) findViewById(R.id.radio_button_dec);
        decButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBase(DEC);
            }
        });

        RadioButton hexButton = (RadioButton) findViewById(R.id.radio_button_hex);
        hexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBase(HEX);
            }
        });

        switch (mCalculator.getBase()) {
            case BIN:
                binButton.setChecked(true);
                break;
            case DEC:
                decButton.setChecked(true);
                break;
            case HEX:
                hexButton.setChecked(true);
                break;
        }
    }

    /**
     * Finds all number buttons, sets onClickListener -> pressedNumber(numberButton).
     * Calls enableButtonsInRange(base), enabling and disabling buttons based on whether they
     * are in range or not.
     */
    private void initNumberButtons() {
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                pressedNumber(b.getText().toString());
            }
        };

        mNumbers = new Button[16];

        mNumbers[0]  = (Button) findViewById(R.id.button_0);
        mNumbers[1]  = (Button) findViewById(R.id.button_1);
        mNumbers[2]  = (Button) findViewById(R.id.button_2);
        mNumbers[3]  = (Button) findViewById(R.id.button_3);
        mNumbers[4]  = (Button) findViewById(R.id.button_4);
        mNumbers[5]  = (Button) findViewById(R.id.button_5);
        mNumbers[6]  = (Button) findViewById(R.id.button_6);
        mNumbers[7]  = (Button) findViewById(R.id.button_7);
        mNumbers[8]  = (Button) findViewById(R.id.button_8);
        mNumbers[9]  = (Button) findViewById(R.id.button_9);
        mNumbers[10] = (Button) findViewById(R.id.button_a);
        mNumbers[11] = (Button) findViewById(R.id.button_b);
        mNumbers[12] = (Button) findViewById(R.id.button_c);
        mNumbers[13] = (Button) findViewById(R.id.button_d);
        mNumbers[14] = (Button) findViewById(R.id.button_e);
        mNumbers[15] = (Button) findViewById(R.id.button_f);

        for (Button b : mNumbers) {
            b.setOnClickListener(numberListener);
        }

        enableButtonsInRange(mCalculator.getBase());
    }

    /**
     * Finds all operator buttons, sets onClickListener -> operatorPressed(operator).
     */
    private void initOperatorButtons() {

        //UNARY OPERATIONS
        {
            int[] buttonIds = {
                    R.id.button_increment,
                    R.id.button_decrement,
                    R.id.button_not,
                    R.id.button_left_shift,
                    R.id.button_right_shift
            };

            UnaryOperator[] unaryOperators = {
                    INCREMENT,
                    DECREMENT,
                    NOT,
                    LEFT_SHIFT,
                    RIGHT_SHIFT
            };

            View.OnClickListener unaryOperatorListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pressedUnaryOperator((UnaryOperator) v.getTag());
                }
            };

            for (int i = 0; i < buttonIds.length; i++) {
                Button b = (Button) findViewById(buttonIds[i]);
                b.setTag(unaryOperators[i]);
                b.setOnClickListener(unaryOperatorListener);
            }
        }

        //BINARY OPERATIONS
        {
            int[] buttonIds = {
                    R.id.button_add,
                    R.id.button_subtract,
                    R.id.button_multiply,
                    R.id.button_divide,
                    R.id.button_or,
                    R.id.button_xor,
                    R.id.button_and
            };

            BinaryOperator[] binaryOperators = {
                    ADD,
                    SUBTRACT,
                    MULTIPLY,
                    DIVIDE,
                    OR,
                    XOR,
                    AND
            };

            View.OnClickListener binaryOperatorListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pressedBinaryOperator((BinaryOperator) v.getTag());
                }
            };

            for (int i = 0; i < buttonIds.length; i++) {
                Button b = (Button) findViewById(buttonIds[i]);
                b.setTag(binaryOperators[i]);
                b.setOnClickListener(binaryOperatorListener);
            }
        }

        Button equalButton = (Button) findViewById(R.id.button_equals);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalculator.calculateBinaryOperation();
                updateDisplay();
            }
        });

    }

    private void backspace() {
        if (mCalculator.backspace()) {
            updateDisplay();
        }
    }

    private void clear() {
        if (mCalculator.clear()) {
            updateDisplay();
        }
    }

    private void clearEntry() {
        if (mCalculator.clearEntry()) {
            updateDisplay();
        }
    }

    /**
     * Changes base in Calculator. If base is changed, calls enableButtonsInRange(base);
     * @param base Base to change to.
     */
    private void changeBase(Base base) {
        if (mCalculator.setBase(base)) {
            enableButtonsInRange(base);
            updateInputArea();
        }
    }

    /**
     * Runs through the number buttons, enabling them if they are in range, and disabling them
     * if they are not. For example, in base 2, only the first two buttons (0 and 1) are in range.
     * They get enabled, while the rest get disabled.
     * @param base Base 2, 10 or 16, i.e. binary, decimal or hexadecimal.
     */
    private void enableButtonsInRange(Base base) {
        for (int i = 0; i < mNumbers.length; i++) {
            mNumbers[i].setEnabled(i < base.toInt());
        }
    }

    private void pressedBinaryOperator(BinaryOperator binaryOperator) {
        Log.d(TAG, "PRESSED: " + binaryOperator.toString());

        mCalculator.setOperator(binaryOperator);
        updateDisplay();
    }

    private void pressedNumber(String number) {
        Log.d(TAG, "PRESSED:" + number);

        if (mCalculator.inputDigit(number)) {
            updateDisplay();
        }

        else {
            SoundPool soundPool = SoundPoolManager.getSoundPool();
            soundPool.play(soundPool.load(this, R.raw.boop, 1), 1, 1, 0, 0, 1);
        }
    }

    private void pressedUnaryOperator(UnaryOperator unaryOperator) {
        Log.d(TAG, "PRESSED: " + unaryOperator.toString());

        mCalculator.calculateUnaryOperation(unaryOperator);
        updateDisplay();
    }

    private void toggleBit(ToggleButton b) {
        mCalculator.toggleBit((int)b.getTag());
        updateInputArea();
        updateHexTextViews();
    }

    private void updateDisplay() {
        updateInputArea();
        updateBitButtons();
        updateHexTextViews();
    }

    private void updateBitButtons() {
        boolean[] bits = mCalculator.getBits();
        for (int i = 0; i < 32; i++) {
            mBitButtons[i].setChecked(bits[i]);
        }
    }

    private void updateHexTextViews() {
        String[] hexStrings = mCalculator.getHexStrings();

        for (int i = 0; i < 4; i++) {
            mHexTextViews[i].setText(hexStrings[i]);
        }
    }

    private void updateInputArea() {
        mInput.setText(mCalculator.getInputString());
        mOperation.setText(mCalculator.getCalculationString());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_CALCULATOR, mCalculator);
        super.onSaveInstanceState(savedInstanceState);
    }
}
