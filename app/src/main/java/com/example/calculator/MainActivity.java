package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import androidx.fragment.app.FragmentActivity;


public class MainActivity extends android.app.Activity {

    Calculator calci = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
        {
            TextView display = (TextView)findViewById(R.id.calc_display);
            display.setText(savedInstanceState.getString("expression"));
        }

        //Setting the digit button click events
        setUpButtons();

    }

    private void setUpButtons()
    {
        OnClickListener digitButtonsClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView display = ((TextView)findViewById(R.id.calc_display));
                String existingText = display.getText().toString();
                switch(v.getId())
                {
                    case R.id.digit_0 : display.setText(existingText + "0"); break;
                    case R.id.digit_1 : display.setText(existingText + "1"); break;
                    case R.id.digit_2 : display.setText(existingText + "2"); break;
                    case R.id.digit_3 : display.setText(existingText + "3"); break;
                    case R.id.digit_4 : display.setText(existingText + "4"); break;
                    case R.id.digit_5 : display.setText(existingText + "5"); break;
                    case R.id.digit_6 : display.setText(existingText + "6"); break;
                    case R.id.digit_7 : display.setText(existingText + "7"); break;
                    case R.id.digit_8 : display.setText(existingText + "8"); break;
                    case R.id.digit_9 : display.setText(existingText + "9"); break;
                    case R.id.extra_closebrack : display.setText(existingText + ")"); break;
                    case R.id.operator_pow : display.setText(existingText+"^");break;
                    case R.id.operator_div : display.setText(existingText+"/");break;
                    case R.id.operator_mul : display.setText(existingText+"*");break;
                    case R.id.operator_mod : display.setText(existingText+"%");break;
                    case R.id.operator_plus : display.setText(existingText+"+");break;
                    case R.id.operator_minus : display.setText(existingText+"-");break;
                    case R.id.extra_openbrack : display.setText(existingText+"(");break;
                    case R.id.extra_dot : display.setText(existingText+".");break;
                }
            }
        };
        ((Button)findViewById(R.id.digit_0)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_1)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_2)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_3)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_4)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_5)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_6)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_7)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_8)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.digit_9)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.extra_closebrack)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_pow)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_div)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_mul)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_mod)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_plus)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.operator_minus)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.extra_openbrack)).setOnClickListener(digitButtonsClickListener);
        ((Button)findViewById(R.id.extra_dot)).setOnClickListener(digitButtonsClickListener);

    }

    private void showMessageForInvalidExpression()
    {
        Toast msg = Toast.makeText(getApplicationContext(), "Invalid Expression", Toast.LENGTH_SHORT);
        msg.show();
    }

    public void calculate(View v)
    {
        TextView display = findViewById(R.id.calc_display);
        String expression = display.getText().toString();

        if(!calci.checkExpression(expression)) {
            showMessageForInvalidExpression();
            return;
        }

        double res = 0.0;
        try {
            res = calci.evaluate(expression);
        }
        catch(InvalidExpressionException e)
        {
            showMessageForInvalidExpression();
            return;
        }
        display.setText(new Double(res).toString());

    }

    public void clearDisplay(View v)
    {
        ((TextView)findViewById(R.id.calc_display)).setText("");
    }

    public void backspace(View v)
    {
        TextView display = (TextView)findViewById(R.id.calc_display);
        String expression = display.getText().toString();

        //Checking if the expression is blank
        if(expression.length() != 0)
            display.setText(expression.substring(0, expression.length() - 1));
    }

    @Override
    protected void onSaveInstanceState(Bundle stateBundle)
    {
        super.onSaveInstanceState(stateBundle);
        stateBundle.putString("expression", ((TextView)findViewById(R.id.calc_display)).getText().toString());
    }
}

class InvalidExpressionException extends Throwable
{
    @Override
    public String toString()
    {
        return "Invalid Input Expression";
    }
}
