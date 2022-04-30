package com.kobra.money.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kobra.money.R;

public class FullscreenDialog {
    protected Context context;
    protected Dialog dialog;

    public FullscreenDialog(Context context, int dialogViewId) {
        this.context = context;
        dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog.setContentView(dialogViewId);
        setOnBackClickListener(null);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialogTitle(String title) {
        TextView textTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        if(textTitle != null) {
            textTitle.setText(title);
        }
    }

    public void show() {
        dialog.show();
    }

    public void setOnBackClickListener(View.OnClickListener backListener) {
        ImageView backIcon = (ImageView) dialog.findViewById(R.id.arrowBack);
        if(backIcon != null) {
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(backListener != null) {
                        backListener.onClick(view);
                    } else {
                        dialog.cancel();
                    }
                }
            });
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        if(cancelListener != null) {
            dialog.setOnCancelListener(cancelListener);
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        if(dismissListener != null) {
            dialog.setOnDismissListener(dismissListener);
        }
    }
}
