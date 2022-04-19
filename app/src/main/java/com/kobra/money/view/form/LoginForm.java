package com.kobra.money.view.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.kobra.money.R;
import com.kobra.money.controller.AuthController;
import com.kobra.money.entity.User;
import com.kobra.money.include.UserException;
import com.kobra.money.include.Validate;
import com.kobra.money.view.input.SimpleEditText;

public class LoginForm extends Form {
    private SimpleEditText usernameEdit;
    private SimpleEditText passwordEdit;

    private LoginForm(Context context) {
        super(context);
    }

    @Override
    public void setFormView(View formView) {
        super.setFormView(formView);
        usernameEdit = new SimpleEditText(context, formView.findViewById(R.id.editUsername));
        passwordEdit = new SimpleEditText(context, formView.findViewById(R.id.editPassword));
        initFields();
    }

    @Override
    public void submit() {
        UserException exception = new UserException();
        boolean success = true;
        for (FormField field : formFields) {
            if(field.isRequired()) {
                exception = Validate.validateFormField(field);
                if(exception.getCode() != 0) {
                    submitEvent.onError(exception, field);
                    success = false;
                }
            }
        }

        if(success) {
            submitEvent.onSuccess();
        }
    }

    @Override
    public void resetForm() {

    }

    private void initFields() {
        if(formView != null) {
            formFields.add(new FormField("username","username", usernameEdit));
            formFields.add(new FormField("password","password", passwordEdit));
        }
    }

    public static class Builder extends Form.Builder {
        public Builder(Context context) {
            form = new LoginForm(context);
            this.context = context;
        }
    }
}
