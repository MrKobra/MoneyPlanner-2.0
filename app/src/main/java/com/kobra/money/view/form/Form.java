package com.kobra.money.view.form;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.kobra.money.R;
import com.kobra.money.include.UserException;

import java.util.ArrayList;
import java.util.List;

public abstract class Form {
    protected Context context;
    protected View formView;
    protected View submitButton;

    protected Submit submitListener;
    protected Event submitEvent;

    protected List<FormField> formFields;

    public Form(Context context) {
        this.context = context;
        formFields = new ArrayList<>();
        submitListener = new Submit() {
            @Override
            public void onSubmit() {
                submit();
            }
        };
    }

    public abstract void submit();

    public void setFormView(View formView) {
        this.formView = formView;
        submitButton = formView.findViewById(R.id.submitButton);
        setSubmitOnClickListener();
    }

    public void setSubmitEvent(Event submitEvent) {
        this.submitEvent = submitEvent;
    }

    public void setSubmitListener(Submit submitListener) {
        this.submitListener = submitListener;
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

    protected void setSubmitOnClickListener() {
        if(submitButton != null) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(submitListener != null) {
                        submitListener.onSubmit();
                    }
                }
            });
        }
    }

    public static class FormField {
        private String value;
        private String type;
        private String name;
        private boolean required;

        public FormField(String type, String name) {
            this.type = type;
            this.name = name;
            value = "";
            required = true;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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
        void onSubmit();
    }

    public interface Event {
        void onSuccess();
        void onError(UserException exception);
    }
}
