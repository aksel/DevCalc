package com.akseltorgard.devcalc;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;

import static com.akseltorgard.devcalc.Base.*;
import static com.akseltorgard.devcalc.Operator.*;

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

        Button backspaceButton = (Button) findViewById(R.id.button_backspace);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspace();
            }
        });

        Button clearButton = (Button) findViewById(R.id.button_clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        initDisplay();
        initBaseButtons();
        initNumberButtons();
        initOperatorButtons();

        updateDisplay();
    }

    private void initDisplay() {
        InputFilter[] allCapsFilters = new InputFilter[] {new InputFilter.AllCaps()};

        mOperation = (TextView) findViewById(R.id.text_view_operation);
        mOperation.setFilters(allCapsFilters);

        mInput = (TextView) findViewById(R.id.text_view_input);
        mInput.setFilters(allCapsFilters);

        int[] byteLayoutIds = {
                R.id.byte_1,
                R.id.byte_2,
                R.id.byte_3,
                R.id.byte_4
        };

        mHexTextViews = new TextView[byteLayoutIds.length];

        for (int i = 0; i < byteLayoutIds.length; i++) {
            mHexTextViews[i] = (TextView) findViewById(byteLayoutIds[i]).findViewById(R.id.display_hex);
            mHexTextViews[i].setFilters(allCapsFilters);
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
     * Finds all number buttons, sets onClickListener -> numberPressed(numberButton).
     * Calls enableButtonsInRange(base), enabling and disabling buttons based on whether they
     * are in range or not.
     */
    private void initNumberButtons() {
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                numberPressed(b.getText().toString());
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
            //TODO: Add listeners for unary operators, i.e. increment, decrement, not and shifts
        }

        //BINARY OPERATIONS
        {
            final HashMap<Button, Operator> binaryOperations = new HashMap<>();

            binaryOperations.put((Button) findViewById(R.id.button_add), ADD);
            binaryOperations.put((Button) findViewById(R.id.button_subtract), SUBTRACT);
            binaryOperations.put((Button) findViewById(R.id.button_multiply), MULTIPLY);
            binaryOperations.put((Button) findViewById(R.id.button_divide), DIVIDE);
            binaryOperations.put((Button) findViewById(R.id.button_or), OR);
            binaryOperations.put((Button) findViewById(R.id.button_xor), XOR);
            binaryOperations.put((Button) findViewById(R.id.button_and), AND);

            View.OnClickListener binaryOperationListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    binaryOperation(binaryOperations.get(b));
                }
            };

            for (Button b : binaryOperations.keySet()) {
                b.setOnClickListener(binaryOperationListener);
            }
        }

        Button equalButton = (Button) findViewById(R.id.button_equals);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalculator.calculate();
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

    private void numberPressed(String number) {
        Log.d(TAG, "PRESSED:" + number);

        if (mCalculator.inputDigit(number)) {
            updateDisplay();
        }

        else {
            SoundPool soundPool = SoundPoolManager.getSoundPool();
            soundPool.play(soundPool.load(this, R.raw.boop, 1), 1, 1, 0, 0, 1);
        }
    }

    private void binaryOperation(Operator operator) {
        Log.d(TAG, "PRESSED: " + operator.toString());

        mCalculator.setOperator(operator);
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

    /**
     * Updates mInput.
     */
    private void updateInputArea() {
        mInput.setText(mCalculator.getInputString());
        mOperation.setText(mCalculator.getCalculationString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_CALCULATOR, mCalculator);
        super.onSaveInstanceState(savedInstanceState);
    }
}