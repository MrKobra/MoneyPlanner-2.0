package com.kobra.money.view.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.kobra.money.R;
import com.kobra.money.include.UserException;
import com.kobra.money.view.FormFieldView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Form {
    protected Context context;
    protected View formView;
    protected View submitButton;

    protected Submit submitEvent;

    protected List<FormField> formFields;

    public Form(Context context) {
        this.context = context;
        formFields = new ArrayList<>();
    }

    public abstract void submit();

    public abstract void resetForm();

    public void setFormView(View formView) {
        this.formView = formView;
        submitButton = formView.findViewById(R.id.submitButton);
        setSubmitOnClickListener();
    }

    public void setSubmitEvent(Submit submitEvent) {
        this.submitEvent = submitEvent;
    }

    public void setSubmitButton(View submitButton) {
        this.submitButton = submitButton;
        setSubmitOnClickListener();
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void showFormLoader() {
        if(formView != null) {
            View formLoader = formView.findViewById(R.id.formLoader);
            if(formLoader != null) {
                formLoader.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideFormLoader() {
        if(formView != null) {
            View formLoader = formView.findViewById(R.id.formLoader);
            if(formLoader != null) {
                formLoader.setVisibility(View.GONE);
            }
        }
    }

    public HashMap<String, String> getFormValues() {
        HashMap<String, String> formValues = new HashMap<>();
        if(formFields != null && formFields.size() > 0) {
            for (FormField field : formFields) {
                formValues.put(field.getName(), field.getFieldView().getValue());
            }
        }
        return formValues;
    }

    protected void setSubmitOnClickListener() {
        if(submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submit();
                }
            });
        }
    }

    public static class FormField {
        private String type;
        private String name;
        private boolean required;
        private FormFieldView fieldView;

        public FormField(String type, String name, FormFieldView fieldView) {
            this.type = type;
            this.name = name;
            this.fieldView = fieldView;
            required = true;
        }

        public FormFieldView getFieldView() {
            return fieldView;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public abstract static class Builder {
        protected Form form;
        protected Context context;

        public Builder setFormView(View formView) {
            form.setFormView(formView);
            return this;
        }

        public Builder setSubmitButton(View submitButton) {
            form.setSubmitButton(submitButton);
            return this;
        }

        public Form getForm() {
            return form;
        }
    }

    public interface Submit {
        void onSuccess();
        void onError(UserException exception, FormField fieldView);
    }
}
