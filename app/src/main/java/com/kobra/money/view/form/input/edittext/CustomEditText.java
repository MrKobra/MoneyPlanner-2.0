package com.kobra.money.view.form.input.edittext;

import android.content.Context;
import android.widget.EditText;

import com.kobra.money.view.form.FormFieldView;

public abstract class CustomEditText implements FormFieldView {
    protected EditText editText;
    protected Context context;

    public CustomEditText(Context context, EditText editText) {
        this.editText = editText;
        this.context = context;
    }

    public CustomEditText(Context context) {
        this.context = context;
        editText = null;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }
}
