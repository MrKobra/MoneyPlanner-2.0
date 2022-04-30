package com.kobra.money.view.form.input.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.kobra.money.R;

import java.text.NumberFormat;
import java.util.Locale;

public class AmountEditText extends CustomEditText implements TextWatcher {
    private final String currency = "₽";
    private final String separator = " ";
    private boolean format;
    private int separatorCount;

    public AmountEditText(Context context, EditText editText) {
        super(context, editText);
        separatorCount = 0;
    }

    public AmountEditText(Context context) {
        super(context);
        separatorCount = 0;
    }

    @Override
    public void setEditText(EditText editText) {
        super.setEditText(editText);
        editText.addTextChangedListener(this);
    }

    @Override
    public String getValue() {
        return getValue(editText.getText());
    }

    @Override
    public void setValue(String value) {
        String amountStr = value.replace(separator, "").replace(currency, "");
        if(amountStr.length() > 0) {
            amountStr = NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amountStr)).replace(",", separator) + " " + currency;
            editText.setText(amountStr);
        } else {
            editText.setText("");
        }
    }

    @Override
    public void setError(boolean error) {
        if(error) {
            editText.setBackgroundTintList(context.getColorStateList(R.color.error));
        } else {
            editText.setBackgroundTintList(context.getColorStateList(R.color.normal));
        }
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
