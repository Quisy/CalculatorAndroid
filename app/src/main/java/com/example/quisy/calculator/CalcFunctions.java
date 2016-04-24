package com.example.quisy.calculator;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Mariusz on 2016-04-24.
 */
public class CalcFunctions {

    private View _view;
    private EditText _display;
    private Activity _activity;
    private Double _result = 0.0, _operand1 = Double.NaN, _operand2 = Double.NaN;
    private boolean needDisplayClear = false;


    public static Operations NextOperation = null;

    public CalcFunctions(View view, EditText display, Activity activity) {
        this._view = view;
        this._display = display;
        this._activity = activity;
    }

    public void changeSign() {
        Double newVal = Double.parseDouble(getDisplayText().toString()) * -1;
        setDisplayText(Double.toString(newVal));
        if (needDisplayClear) {
            _operand1 = newVal;
            _operand2 = Double.NaN;
            NextOperation = null;
        }

        updateOperands(newVal);
    }

    public void appendChar(Button btn) {

        if (needDisplayClear) {
            setDisplayText("0");
            needDisplayClear = false;
        }

        Editable text = getDisplayText();

        if (isNaN(Double.parseDouble(text.toString())) || text.toString().toLowerCase().contains("infinity"))
        {
            text.clear();
            text.append("0");
        }

        if ((text.length() == 1 && text.toString().equals("0") && !btn.getText().equals(".")))
            text.clear();


        if (!((text.toString().contains(".") || text.length() == 0) && btn.getText().equals("."))) {
            setDisplayText(text.append(btn.getText()).toString());
        }

        text = getDisplayText();

        updateOperands(Double.parseDouble(text.toString()));
    }

    public void deleteChar() {
        Editable text = getDisplayText();
        int length = text.length();
        if (length > 0) {
            if (length == 1 || (length == 2 && text.toString().contains("-")) || isNaN(Double.parseDouble(text.toString())))
                setDisplayText("0");
            else if (text.toString().substring(length - 2, length - 1).equals(".") || text.toString().substring(length - 2, length - 1).equals("E"))
                setDisplayText((text.delete(length - 2, length)).toString());
            else
                setDisplayText((text.delete(length - 1, length).toString()));

            updateOperands(Double.parseDouble(getDisplayText().toString()));
        }
    }

    public void clearDisplay() {
        setDisplayText("0");
        setResult(0.0);
        NextOperation = null;
        _operand1 = Double.NaN;
        _operand2 = Double.NaN;
    }

    public void doOperation(Operations operation) {
        if (NextOperation != null && !needDisplayClear) {
            switch (NextOperation) {
                case ADD:
                    add();
                    break;
                case SUBSTRACT:
                    substract();
                    break;
                case MULTIPLY:
                    multiply();
                    break;
                case DIVIDE:
                    divide();
                    break;
                case POWER:
                    power();
                    break;
            }
            if (operation.equals(Operations.EQUAL)) {
                setDisplayText(Double.toString(getResult()));
                NextOperation = null;
                needDisplayClear = true;
                _operand1 = getResult();
                _operand2 = Double.NaN;
            } else {
                setDisplayText(Double.toString(getResult()));
                NextOperation = operation;
                _operand1 = getResult();
                _operand2 = 0.0;
                needDisplayClear = true;
            }
        } else if (needDisplayClear) {
            if (!operation.equals(Operations.EQUAL)) {
                NextOperation = operation;
                _operand2 = 0.0;
            }

        } else {
            _operand1 = Double.parseDouble(getDisplayText().toString());
            if (!operation.equals(Operations.EQUAL)) {
                needDisplayClear = true;
                NextOperation = operation;
                _operand2 = 0.0;
            }
        }
    }


    public void doOperation2(Operations operation) {
        Double temp = Double.parseDouble(getDisplayText().toString());
        double radians;
        switch (operation) {
            case SIN:
                radians = Math.toRadians(temp);
                temp = Math.sin(radians);
                break;
            case COS:
                radians = Math.toRadians(temp);
                temp = Math.cos(radians);
                break;
            case TAN:
                radians = Math.toRadians(temp);
                temp = Math.tan(radians);
                break;
            case LN:
                temp = Math.log(temp);
                break;
            case SQRT:
                temp = Math.sqrt(temp);
                break;
            case SQUARE:
                temp = Math.pow(temp,2);
                break;
            case LOG:
                temp = Math.log10(temp);
                break;
        }
        if (needDisplayClear) {
            _operand1 = temp;
            _operand2 = Double.NaN;
            NextOperation = null;
            setDisplayText(temp.toString());
        }
        else
        {
            setDisplayText(temp.toString());
        }

        updateOperands(temp);

    }


    private void add() {
        setResult(_operand1 + _operand2);
    }

    private void substract() {
        setResult(_operand1 - _operand2);
    }

    private void multiply() {
        setResult(_operand1 * _operand2);
    }

    private void divide() {
        if (_operand2 != 0)
            setResult(_operand1 / _operand2);
        else
            sendToastMessage("Division by zero! Nothing done.");
    }

    private void power() {
        setResult(Math.pow(_operand1,_operand2));
    }



    private void updateOperands(Double value) {
        if (Double.isNaN(_operand1) || Double.isNaN(_operand2))
            _operand1 = value;
        else
            _operand2 = value;

    }

    private void setResult(Double value) {
        _result = value;
    }

    private Double getResult() {
        return _result;
    }

    public Editable getDisplayText() {
        return _display.getText();
    }

    public void setDisplayText(String text) {
        _display.setText(text);
    }

    private void sendToastMessage(String message) {
        Context context = _activity.getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private boolean isNaN(Double value)
    {
        return Double.isNaN(value);
    }
}
