package com.kobra.money.view.dialog.select;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.view.dialog.FullscreenDialog;

public abstract class SelectDialog extends FullscreenDialog {
    public SelectDialog(Context context, int dialogViewId) {
        super(context, dialogViewId);
    }

    public abstract String getSelected();

    public abstract void setSelected(String selected);

    public abstract boolean checkSelected();

    public void setOnAcceptClickListener(SelectDialogInterface.OnAcceptListener acceptListener) {
        CardView acceptButton = dialog.findViewById(R.id.acceptDialogButton);
        if(acceptButton != null) {
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkSelected()) {
                        if(acceptListener != null) acceptListener.onSuccess();
                        dialog.dismiss();
                    } else {
                        if(acceptListener != null) acceptListener.onError();
                    }
                }
            });
        }
    }
}
