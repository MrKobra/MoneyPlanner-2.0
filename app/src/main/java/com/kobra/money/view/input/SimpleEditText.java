package com.kobra.money.view.input;

import android.content.Context;
import android.widget.EditText;

import com.kobra.money.R;

public class SimpleEditText extends CustomEditText {
    public SimpleEditText(Context context, EditText editText) {
        super(context, editText);
    }

    @Override
    public String getValue() {
        return editText.getText().toString();
    }

    @Override
    public void setValue(String value) {
        editText.setText(value);
    }

    @Override
    public void setError(boolean error) {
        if(error) {
            editText.setBackgroundTintList(context.getColorStateList(R.color.error));
        } else {
            editText.setBackgroundTintList(context.getColorStateList(R.color.normal));
        }
    }
}
