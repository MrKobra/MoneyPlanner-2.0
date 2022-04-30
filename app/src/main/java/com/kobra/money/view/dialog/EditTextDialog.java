package com.kobra.money.view.dialog;

import android.content.Context;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.view.form.input.edittext.CustomEditText;

public class EditTextDialog extends FullscreenDialog {
    private CustomEditText editText;
    private Event event;

    public EditTextDialog(Context context, CustomEditText editText) {
        super(context, R.layout.dialog_edit_text);
        setEditText(editText);
        setAcceptOnClickListener();
    }

    public EditTextDialog(Context context) {
        super(context, R.layout.dialog_edit_text);
        setAcceptOnClickListener();
    }

    public void setEditText(CustomEditText editText) {
        this.editText = editText;
        this.editText.setEditText(dialog.findViewById(R.id.dialogEditText));
    }

    public void setHintText(String text) {
        editText.getEditText().setHint(text);
    }

    public void setEditTextType(int inputType) {
        editText.getEditText().setInputType(inputType);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setAcceptOnClickListener() {
        CardView acceptButton = dialog.findViewById(R.id.acceptDialogButton);
        if(acceptButton != null) {
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(event != null) event.onSuccess(editText.getValue());
                    dialog.dismiss();
                }
            });
        }
    }

    public CustomEditText getEditText() {
        return editText;
    }

    @Override
    public void show() {
        super.show();
    }

    public interface Event {
        void onSuccess(String value);
    }
}
