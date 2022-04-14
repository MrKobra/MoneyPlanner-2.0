package com.kobra.money.view.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

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
        getFormFieldsValue();
        UserException exception = checkFields();
        if(exception.getCode() == 0) {
            if(submitEvent != null) submitEvent.onSuccess(getFormValues());
        } else {
            if(submitEvent != null) submitEvent.onError(exception);
        }
    }

    @Override
    public void resetForm() {

    }

    private void initFields() {
        if(formView != null) {
            formFields.add(new FormField("username","username"));
            formFields.add(new FormField("password","password"));
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

    private void getFormFieldsValue() {
        for(FormField field : formFields) {
            switch (field.getName()) {
                case "username":
                    EditText username = formView.findViewById(R.id.editUsername);
                    if(username != null) field.setValue(username.getText().toString());
                    break;
                case "password":
                    EditText password = formView.findViewById(R.id.editPassword);
                    if(password != null) field.setValue(password.getText().toString());
                    break;
            }
        }
    }

    public static class Builder extends Form.Builder {
        public Builder(Context context) {
            form = new LoginForm(context);
            this.context = context;
        }
    }
}
