package com.kobra.money.view.table;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

public class CustomTable {
    protected Context context;
    protected TableLayout table;
    protected int column;

    public CustomTable(Context context, TableLayout table) {
        this.context = context;
        this.table = table;
        column = 4;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void print(List<View> views, int rowPadding) {
        int cur = 0;
        table.setStretchAllColumns(true);
        table.removeAllViews();
        while(cur < views.size()) {
            float dp = context.getResources().getDisplayMetrics().density;

            /* Создание строки */
            TableRow row = new TableRow(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(layoutParams);
            row.setPadding(0, (int) (rowPadding * dp), 0, (int) (rowPadding * dp));

            for(int i = 0; i < column && cur < views.size(); i++) {
                row.addView(views.get(cur++));
            }

            table.addView(row);
        }
    }
}
