package com.kobra.money.include;

import android.widget.EditText;

import com.kobra.money.view.form.Form;

public class Validate {
    /* Настройки */
    public static int minUsernameLength = 3;
    public static int minPasswordLength = 3;
    public static int minNameLength = 3;
    public static int maxNameLength = 30;

    public static UserException validateFormField(Form.FormField formField) {
        UserException userException = new UserException();

        switch (formField.getType()) {
            case "username":
                if(formField.getValue().length() < minUsernameLength) {
                    userException.setCode(1);
                }
                break;
            case "password":
                if(formField.getValue().length() < minPasswordLength) {
                    userException.setCode(2);
                }
                break;
            case "category":
                if(formField.getValue().length() == 0 || formField.getValue().equals("0")) {
                    userException.setCode(6);
                }
                break;
            case "amount":
                if(formField.getValue().length() == 0 || formField.getValue().equals("0")) {
                    userException.setCode(5);
                }
                break;
        }

        return userException;
    }
}
