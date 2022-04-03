package com.kobra.money.view.input;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kobra.money.R;

import java.text.NumberFormat;
import java.util.Locale;

public class AmountEditText extends CustomEditText implements TextWatcher {
    private final String currency = "₽";
    private final String separator = " ";
    private boolean format;
    private int separatorCount;

    public AmountEditText(EditText editText) {
        super(editText);
        separatorCount = 0;
        editText.addTextChangedListener(this);
    }

    @Override
    public String getValue() {
        return getValue(editText.getText());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!format) {
            format = true;
            String amountStr = getValue(editable);
            if(amountStr.length() > 0) {
                int cursorPosition = editText.getSelectionStart();
                int currentSeparatorCount = (amountStr.length() - 1) / 3;
                amountStr = NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amountStr)).replace(",", separator) + " " + currency;

                if(separatorCount > currentSeparatorCount) {
                    cursorPosition--;
                } else if(separatorCount < currentSeparatorCount) {
                    cursorPosition++;
                }

                separatorCount = currentSeparatorCount;

                if(cursorPosition > amountStr.length() - 2) {
                    cursorPosition = amountStr.length() - 2;
                }

                editText.setText(amountStr);
                editText.setSelection(cursorPosition);
            } else {
                editText.setText("");
            }
            format = false;
        }
    }

    private String getValue(Editable editable) {
        return editable.toString().replace(separator, "").replace(currency, "");
    }
}
