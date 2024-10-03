package com.example.bottomnav;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class CalculatorFragment extends Fragment {

    private final String SAVED_OPERATION = "pendingOp";
    private final String SAVED_OPERAND = "op1";

    private EditText newNumber;
    private EditText result;
    private TextView displayOperation;

    private Double op1 = null;
    private String pendingOp = "=";

    @SuppressLint({"SetTextI18n", "ResourceType", "InflateParams"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        newNumber = view.findViewById(R.id.newNumber);
        result = view.findViewById(R.id.result);
        displayOperation = view.findViewById(R.id.operation);

        Button button0 = view.findViewById(R.id.button0);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);
        Button button3 = view.findViewById(R.id.button3);
        Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);
        Button button6 = view.findViewById(R.id.button6);
        Button button7 = view.findViewById(R.id.button7);
        Button button8 = view.findViewById(R.id.button8);
        Button button9 = view.findViewById(R.id.button9);
        Button buttonDot = view.findViewById(R.id.buttonDot);

        Button buttonEquals = view.findViewById(R.id.buttonEquals);
        Button buttonDevide = view.findViewById(R.id.buttonDevide);
        Button buttonMultiply = view.findViewById(R.id.buttonMultiply);
        Button buttonMinus = view.findViewById(R.id.buttonMinus);
        Button buttonPlus = view.findViewById(R.id.buttonAdd);
        Button percentageButton = view.findViewById(R.id.percentage);
        Button brackets = view.findViewById(R.id.buttonBracket);

        // OnClick Actions for the Number Buttons
        View.OnClickListener onClickNumber = v -> {
            Button b = (Button) v;
            result.append(b.getText().toString());
        };

        button0.setOnClickListener(onClickNumber);
        button1.setOnClickListener(onClickNumber);
        button2.setOnClickListener(onClickNumber);
        button3.setOnClickListener(onClickNumber);
        button4.setOnClickListener(onClickNumber);
        button5.setOnClickListener(onClickNumber);
        button6.setOnClickListener(onClickNumber);
        button7.setOnClickListener(onClickNumber);
        button8.setOnClickListener(onClickNumber);
        button9.setOnClickListener(onClickNumber);
        buttonDot.setOnClickListener(onClickNumber);

        // OnClick Actions for Operation Buttons
        View.OnClickListener onClickOperation = v -> {
            Button b = (Button) v;
            String operation = b.getText().toString();
            String value = result.getText().toString();
            try {
                Double doubleValue = Double.valueOf(value);
                performOperation(doubleValue, operation);
            } catch (NumberFormatException e) {
                result.setText("");
            }
            pendingOp = operation;
            displayOperation.setText(pendingOp);
        };

        buttonEquals.setOnClickListener(onClickOperation);
        buttonDevide.setOnClickListener(onClickOperation);
        buttonMinus.setOnClickListener(onClickOperation);
        buttonPlus.setOnClickListener(onClickOperation);
        buttonMultiply.setOnClickListener(onClickOperation);

        Button clearText = view.findViewById(R.id.clearText);
        clearText.setOnClickListener(v -> {
            newNumber.setText("");
            result.setText("");
            displayOperation.setText("");
            op1 = null;
        });

        percentageButton.setOnClickListener(v -> {
            String s = newNumber.getText().toString();
            String m = result.getText().toString();
            if ((s.length() == 0) && (m.length() == 0)) {
                newNumber.setText("0.0");
            } else {
                try {
                    Double value = Double.valueOf(s);
                    Double num = Double.valueOf(m);
                    double cal = (value * num) / 100.0;
                    this.newNumber.setText(Double.toString(cal));
                    result.setText(this.newNumber.getText());
                    displayOperation.setText("");
                    op1 = null;
                } catch (Exception e) {
                    newNumber.setText("");
                    result.setText("");
                    displayOperation.setText("");
                    op1 = null;
                }
            }
        });

        Button buttonNeg = view.findViewById(R.id.negSymobol);
        buttonNeg.setOnClickListener(v -> {
            String s = result.getText().toString();
            if (s.length() == 0) {
                result.setText("-");
            } else {
                try {
                    double doublevalue = Double.parseDouble(s);
                    doublevalue *= -1;
                    op1 = doublevalue;
                    result.setText(Double.toString(doublevalue));
                } catch (NumberFormatException e) {
                    result.setText("");
                }
            }
        });

        brackets.setOnClickListener(v -> {
            try {
                String str = result.getText().toString();
                str = str.substring(0, str.length() - 1);
                result.setText(str);
            } catch (Exception ignored) {
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean isFirstRun = sharedPreferences.getBoolean("IS_FIRST_RUN", true);

        if (isFirstRun) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setMessage("→ New Material UI Introduced. " +
                    "\n→ Minor Bugs Fixed " +
                    "\n→ Added New Functions " +
                    "\n→ With New Refreshing Look " +
                    "\n→ Dev CraazY");

            builder.setTitle("Update Info!");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            editor.putBoolean("IS_FIRST_RUN", false);
            editor.apply();
        }

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double value, String operation) {
        if (null == op1) {
            op1 = value;
        } else {
            if (pendingOp.equals("=")) {
                pendingOp = operation;
            }
            switch (pendingOp) {
                case "=":
                    op1 = value;
                    break;
                case "÷":
                    if (value == 0) {
                        op1 = 0.0;
                    } else {
                        op1 /= value;
                    }
                    break;
                case "×":
                    op1 *= value;
                    break;
                case "-":
                    op1 -= value;
                    break;
                case "+":
                    op1 += value;
                    break;
            }
        }
        newNumber.setText(String.format("%.2f", op1));
        result.setText("");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SAVED_OPERATION, pendingOp);
        if (op1 != null) {
            outState.putDouble(SAVED_OPERAND, op1);
        }
        super.onSaveInstanceState(outState);
    }
}
