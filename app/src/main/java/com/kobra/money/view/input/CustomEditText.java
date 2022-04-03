package com.kobra.money.view.input;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public abstract class CustomEditText {
    protected EditText editText;

    public CustomEditText(EditText editText) {
        this.editText = editText;
    }

    public CustomEditText() {
        editText = null;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }

    public abstract String getValue();

    public abstract void setValue(String value);
}