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
        EditText editText = formField.getEdit();

        switch (formField.getType()) {
            case "username":
                if(formField.getEdit().length() < minUsernameLength) {
                    userException.setCode(1);
                }
                break;
            case "password":
                if(formField.getEdit().length() < minPasswordLength) {
                    userException.setCode(2);
                }
                break;
        }

        return userException;
    }
}
