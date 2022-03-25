package com.kobra.money.view.form;

import android.content.Context;
import android.view.View;

import com.kobra.money.R;
import com.kobra.money.controller.AuthController;
import com.kobra.money.include.UserException;
import com.kobra.money.include.Validate;

public class LoginForm extends Form {
    private LoginForm(Context context) {
        super(context);
    }

    @Override
    public void setFormView(View formView) {
        super.setFormView(formView);
        initFields();
    }

    @Override
    public void submit() {
        UserException exception = checkFields();
        if(exception.getCode() == 0) {
            if(submitEvent != null) submitEvent.onSuccess();
        } else {
            if(submitEvent != null) submitEvent.onError(exception);
        }
    }

    private void initFields() {
        if(formView != null) {
            formFields.add(new FormField(formView.findViewById(R.id.editUsername), "username",
                    "username"));
            formFields.add(new FormField(formView.findViewById(R.id.editPassword), "password",
                    "password"));
        }
    }

    private UserException checkFields() {
        UserException exception = new UserException();

        for(FormField field : formFields) {
            exception = Validate.validateFormField(field);
            if(exception.getCode() != 0) {
                break;
            }
        }

        return exception;
    }

    public static class Builder extends Form.Builder {
        public Builder(Context context) {
            form = new LoginForm(context);
            this.context = context;
        }
    }
}
