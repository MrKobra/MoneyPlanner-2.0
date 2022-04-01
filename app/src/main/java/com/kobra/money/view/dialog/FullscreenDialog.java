package com.kobra.money.view.dialog;

import android.app.Dialog;
import android.content.Context;
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
        setBackOnClickListener();
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

    protected void setBackOnClickListener() {
        ImageView backIcon = (ImageView) dialog.findViewById(R.id.arrowBack);
        if(backIcon != null) {
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }
}
