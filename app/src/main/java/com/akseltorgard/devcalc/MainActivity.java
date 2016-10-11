package com.akseltorgard.devcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import static com.akseltorgard.devcalc.Operator.*;

public class MainActivity extends AppCompatActivity {

    final String TAG = "DevCalc.MainActivity";
    final String KEY_CALCULATOR = "Key mCalculator";

    Calculator mCalculator;

    Button[] mNumbers;

    TextView mDisplayInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCalculator = savedInstanceState.getParcelable(KEY_CALCULATOR);
            Log.d(TAG, mCalculator.getInput());
        }

        else {
            mCalculator = new Calculator();
        }

        mDisplayInput = (TextView) findViewById(R.id.textView_input);
        mDisplayInput.setText(mCalculator.getInput());

        initNumberButtons();
        initOperatorButtons();
    }

    /**
     * Finds all number buttons, sets an onClickListener -> numberPressed(numberButton);
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

        mNumbers[0] = (Button) findViewById(R.id.button_0);
        mNumbers[1] = (Button) findViewById(R.id.button_1);
        mNumbers[2] = (Button) findViewById(R.id.button_2);
        mNumbers[3] = (Button) findViewById(R.id.button_3);
        mNumbers[4] = (Button) findViewById(R.id.button_4);
        mNumbers[5] = (Button) findViewById(R.id.button_5);
        mNumbers[6] = (Button) findViewById(R.id.button_6);
        mNumbers[7] = (Button) findViewById(R.id.button_7);
        mNumbers[8] = (Button) findViewById(R.id.button_8);
        mNumbers[9] = (Button) findViewById(R.id.button_9);
        mNumbers[10] = (Button) findViewById(R.id.button_a);
        mNumbers[11] = (Button) findViewById(R.id.button_b);
        mNumbers[12] = (Button) findViewById(R.id.button_c);
        mNumbers[13] = (Button) findViewById(R.id.button_d);
        mNumbers[14] = (Button) findViewById(R.id.button_e);
        mNumbers[15] = (Button) findViewById(R.id.button_f);

        for (Button b : mNumbers) {
            b.setOnClickListener(numberListener);
        }
    }

    /**
     * Finds all operator buttons, and gives them an onClickListener -> operatorPressed(operator).
     */
    private void initOperatorButtons() {

        final HashMap<Button, Operator> operators = new HashMap<>();

        operators.put((Button) findViewById(R.id.button_add),         ADD);
        operators.put((Button) findViewById(R.id.button_subtract),    SUBTRACT);
        operators.put((Button) findViewById(R.id.button_multiply),    MULTIPLY);
        operators.put((Button) findViewById(R.id.button_divide),      DIVIDE);
        operators.put((Button) findViewById(R.id.button_increment),   INCREMENT);
        operators.put((Button) findViewById(R.id.button_decrement),   DECREMENT);
        operators.put((Button) findViewById(R.id.button_or),          OR);
        operators.put((Button) findViewById(R.id.button_xor),         XOR);
        operators.put((Button) findViewById(R.id.button_and),         AND);
        operators.put((Button) findViewById(R.id.button_not),         NOT);
        operators.put((Button) findViewById(R.id.button_left_shift),  LEFT_SHIFT);
        operators.put((Button) findViewById(R.id.button_right_shift), RIGHT_SHIFT);

        View.OnClickListener operatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                operatorPressed(operators.get(b));
            }
        };

        for (Button b : operators.keySet()) {
            b.setOnClickListener(operatorListener);
        }
    }

    private void numberPressed(String number) {
        Log.d(TAG, "PRESSED:" + number);

        mCalculator.inputNumber(number);
        mDisplayInput.setText(mCalculator.getInput());
    }

    private void operatorPressed(Operator operator) {
        Log.d(TAG, "PRESSED: " + operator.name());
    }

    private void changeInputMode() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Put mOperators and mNumbers in Bundle.
     * @param savedInstanceState Bundle.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(KEY_CALCULATOR, mCalculator);
        super.onSaveInstanceState(savedInstanceState);
    }
}