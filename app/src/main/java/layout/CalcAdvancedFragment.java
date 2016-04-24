package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quisy.calculator.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcAdvancedFragment extends Fragment implements View.OnClickListener {


    private static double result = 0;
    private static double op1 = 0, op2 = 0;
    private static boolean isCleared = true;
    private static String nextOperation = "";
    public static final String ADD_OPERATION = "add";
    public static final String SUB_OPERATION = "sub";
    public static final String MUL_OPERATION = "mul";
    public static final String DIV_OPERATION = "div";
    public static final String EQUAL_OPERATION = "equal";
    public static final String SIN_OPERATION = "sin";
    public static final String COS_OPERATION = "cos";
    public static final String TAN_OPERATION = "tan";
    public static final String LN_OPERATION = "ln";
    Button zero, one, two, three, four, five, six, seven, eight, nine, add, sub, mul, div, equal, cancel, delete, dot, sign, sin, cos, tan, ln;
    EditText display;


    public CalcAdvancedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calc_advanced, container, false);

        initButtons(view);
        display = (EditText) view.findViewById(R.id.display);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_dot:
                Button btn = (Button) v.findViewById(v.getId());
                appendChar(btn);
                break;

            case R.id.btn_delete:
                deleteChar();
                break;

            case R.id.btn_cancel:
                clearDisplay();
                break;

            case R.id.btn_add:
                doOperation(ADD_OPERATION);
                break;

            case R.id.btn_sub:
                doOperation(SUB_OPERATION);
                break;

            case R.id.btn_mul:
                doOperation(MUL_OPERATION);
                break;

            case R.id.btn_div:
                doOperation(DIV_OPERATION);
                break;

            case R.id.btn_equal:
                doOperation(EQUAL_OPERATION);
                break;

            case R.id.btn_sign:
                changeSign();
                break;

            case R.id.btn_sin:
                doOperation2(SIN_OPERATION);
                break;

            case R.id.btn_cos:
                doOperation2(COS_OPERATION);
                break;

            case R.id.btn_tan:
                doOperation2(TAN_OPERATION);
                break;

            case R.id.btn_ln:
                doOperation2(LN_OPERATION);
                break;

        }
    }


    private void changeSign() {
        if (op1 == 0) {
            updateResult(Double.parseDouble(display.getText().toString()) * -1);
            display.setText(Double.toString(result));
        } else
            display.setText(Double.toString(Double.parseDouble(display.getText().toString()) * -1));
    }

    private void appendChar(Button btn) {
        if (nextOperation != "" && !isCleared) {
            isCleared = true;
            display.setText("");
        }

        Editable text = display.getText();
        if((!text.toString().contains(".") && btn == dot) || btn != dot)
        {
            display.setText(text.append(btn.getText()));
        }
    }

    private void deleteChar() {
        Editable text = display.getText();
        int length = text.length();
        if (length == 1)
            display.setText("0");
        else if (text.toString().substring(length - 2, length - 1).equals("."))
            display.setText(text.delete(length - 2, length));
        else
            display.setText(text.delete(length - 1, length));

        if (op1 == 0)
            updateResult(Double.parseDouble(display.getText().toString()));
    }

    private void clearDisplay() {
        display.setText("");
        updateResult(Double.parseDouble("0"));
        nextOperation = "";

    }

    private void doOperation(String operationType) {
        if (op1 != 0) {
            op2 = Double.parseDouble(display.getText().toString());
            switch (nextOperation.toLowerCase()) {
                case ADD_OPERATION:
                    result += op2;
                    break;
                case SUB_OPERATION:
                    result -= op2;
                    break;
                case MUL_OPERATION:
                    result *= op2;
                    break;
                case DIV_OPERATION:
                    if (op2 != 0)
                        result /= op2;
                    else
                        sendToastMessage("Division by zero! Nothing done.");
                    break;
            }

            display.setText(Double.toString(result));
            if (operationType != EQUAL_OPERATION) {
                nextOperation = operationType;
                op1 = Double.parseDouble(display.getText().toString());
            } else {
                nextOperation = "";
                op1 = 0;
            }


            isCleared = false;
        } else {
            updateResult(Double.parseDouble(display.getText().toString()));
            op1 = Double.parseDouble(display.getText().toString());
            nextOperation = operationType;
            display.setText("");
        }

    }

    private void doOperation2(String operationType) {

        Double temp = Double.parseDouble(display.getText().toString());
        double radians;
        switch (operationType.toLowerCase()) {
            case SIN_OPERATION:
                radians = Math.toRadians(temp);
                temp = Math.sin(radians);
                break;
            case COS_OPERATION:
                radians = Math.toRadians(temp);
                temp = Math.cos(radians);
                break;
            case TAN_OPERATION:
                radians = Math.toRadians(temp);
                temp = Math.tan(radians);
                break;
            case LN_OPERATION:
                temp = Math.log(temp);
                break;
        }

        if (op1 == 0) {
            updateResult(temp);
            display.setText(Double.toString(result));
        } else
            display.setText(Double.toString(temp));
    }


    private void updateResult(Double value) {
        result = value;
    }


    private void sendToastMessage(String message)
    {
        Context context = getActivity().getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void initButtons(View v) {
        zero = (Button) v.findViewById(R.id.btn_0);
        zero.setOnClickListener(this);
        one = (Button) v.findViewById(R.id.btn_1);
        one.setOnClickListener(this);
        two = (Button) v.findViewById(R.id.btn_2);
        two.setOnClickListener(this);
        three = (Button) v.findViewById(R.id.btn_3);
        three.setOnClickListener(this);
        four = (Button) v.findViewById(R.id.btn_4);
        four.setOnClickListener(this);
        five = (Button) v.findViewById(R.id.btn_5);
        five.setOnClickListener(this);
        six = (Button) v.findViewById(R.id.btn_6);
        six.setOnClickListener(this);
        seven = (Button) v.findViewById(R.id.btn_7);
        seven.setOnClickListener(this);
        eight = (Button) v.findViewById(R.id.btn_8);
        eight.setOnClickListener(this);
        nine = (Button) v.findViewById(R.id.btn_9);
        nine.setOnClickListener(this);
        dot = (Button) v.findViewById(R.id.btn_dot);
        dot.setOnClickListener(this);

        add = (Button) v.findViewById(R.id.btn_add);
        add.setOnClickListener(this);
        sub = (Button) v.findViewById(R.id.btn_sub);
        sub.setOnClickListener(this);
        div = (Button) v.findViewById(R.id.btn_div);
        div.setOnClickListener(this);
        mul = (Button) v.findViewById(R.id.btn_mul);
        mul.setOnClickListener(this);

        cancel = (Button) v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(this);
        delete = (Button) v.findViewById(R.id.btn_delete);
        delete.setOnClickListener(this);
        equal = (Button) v.findViewById(R.id.btn_equal);
        equal.setOnClickListener(this);

        sign = (Button) v.findViewById(R.id.btn_sign);
        sign.setOnClickListener(this);

        sin = (Button) v.findViewById(R.id.btn_sin);
        sin.setOnClickListener(this);
        cos = (Button) v.findViewById(R.id.btn_cos);
        cos.setOnClickListener(this);
        tan = (Button) v.findViewById(R.id.btn_tan);
        tan.setOnClickListener(this);
        ln = (Button) v.findViewById(R.id.btn_ln);
        ln.setOnClickListener(this);

    }

}
