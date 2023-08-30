package com.example.calculatortuts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

// importing the javascript context and scriptable to calculate the result
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result_text, input_text;

    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose, buttonDivide;
    MaterialButton button7, button8, button9, buttonMultiply;
    MaterialButton button4, button5, button6, buttonSubstract;
    MaterialButton button1, button2, button3, buttonAdd;
    MaterialButton buttonAC, button0, buttonDot, buttonEquals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Assigning the IDs
        result_text = findViewById(R.id.result_text);
        input_text = findViewById(R.id.input_text);

//       Assigning the id's resource to buttons
        assignId(buttonC, R.id.button_c);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(button0, R.id.button_0);

        assignId(buttonAC, R.id.button_ac);
        assignId(buttonEquals, R.id.button_equal);
        assignId(buttonDot, R.id.button_dot);

        assignId(buttonBrackOpen, R.id.button_open_bracket);
        assignId(buttonBrackClose, R.id.button_close_bracket);

        assignId(buttonAdd, R.id.button_add);
        assignId(buttonSubstract, R.id.button_substract);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonDivide, R.id.button_divide);

    }

    //    this method will be setting on click listener and setting the id to a button
    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //    OnClick handling function from interface "View.OnClickListener"
    @Override
    public void onClick(View view) {
//        Picking the buttons MaterialButton type from the "view"
        MaterialButton button = (MaterialButton) view;
//        Extracting the button's text
        String buttonText = button.getText().toString();
//        Extracting the already in the input field
        String dataToCalculate = input_text.getText().toString();

//        Making conditionals for "AC", "C", "=" buttons
        if (buttonText.equals("AC")) {
//            making both 0 onClicking "AC"
            input_text.setText("");
            result_text.setText("0");
            return; // this will return without printing the buttonText->value
        }
        if (buttonText.equals("=")) {
//            making the result to be printed in the input field
            input_text.setText(result_text.getText());
            return;
        }
        if (buttonText.equals("C")) {
//            making a substring of the 0th(current one with a space between "327(1) 234(0)<-") index's length-1 -> last printed character and assigning it to dataToCalculate
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            input_text.setText(dataToCalculate);
        } else {
            //        Concatenating the old data available in inputField with new button text which is pressed
            dataToCalculate = dataToCalculate + buttonText;
        }
        //        Setting the dataToCalculate text to the inputField
        input_text.setText(dataToCalculate);

//        Calculating the final result
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
//            setting the result field to final text
            result_text.setText(finalResult);
        }
    }

    //    Using preBuilt mozilla Rhino JavaScript engine to calculate the result
    String getResult(String data) {
        try {
//            creating a new JavaScript execution context using the Rhino JavaScript engine which allows to evaluate JavaScript code within the app
            Context context = Context.enter();
//            disabling optimization
            context.setOptimizationLevel(-1);
//            initialising a "scriptable" object within the javaScript context which provides a basic environment where JavaScript code can be executed which is similar as creating a container to hold variables and functions for script execution
            Scriptable scriptable = context.initStandardObjects();
//            evaluating the input "data" as javaScript code within the context of the scriptable object
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
//            if ends with .0 then do not show that ".0" part
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
//            returning the finalResult
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}