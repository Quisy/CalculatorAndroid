package layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quisy.calculator.CalcFunctions;
import com.example.quisy.calculator.Operations;
import com.example.quisy.calculator.R;

import java.io.Console;

/**
 * Created by Mariusz on 2016-04-10.
 */
public class CalcSimpleFragment extends Fragment implements View.OnClickListener {

    Button zero, one, two, three, four, five, six, seven, eight, nine, add, sub, mul, div, equal, cancel, delete, dot, sign;
    EditText display;

    private CalcFunctions _calc;

    public CalcSimpleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calc_simple, container, false);

        initButtons(view);
        display = (EditText) view.findViewById(R.id.display);

        display.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        display.setText("0");

        _calc = new CalcFunctions(view,display,getActivity());

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
                _calc.appendChar(btn);
                break;

            case R.id.btn_delete:
                _calc.deleteChar();
                break;

            case R.id.btn_cancel:
                _calc.clearDisplay();
                break;

            case R.id.btn_add:
                _calc.doOperation(Operations.ADD);
                break;

            case R.id.btn_sub:
                _calc.doOperation(Operations.SUBSTRACT);
                break;

            case R.id.btn_mul:
                _calc.doOperation(Operations.MULTIPLY);
                break;

            case R.id.btn_div:
                _calc.doOperation(Operations.DIVIDE);
                break;

            case R.id.btn_equal:
                _calc.doOperation(Operations.EQUAL);
                break;

            case R.id.btn_sign:
                _calc.changeSign();
                break;

        }
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

    }

}