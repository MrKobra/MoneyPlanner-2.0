package com.kobra.money.view.form;

public interface FormFieldView {
    String getValue();
    void setValue(String value);
    void setError(boolean error);
}
