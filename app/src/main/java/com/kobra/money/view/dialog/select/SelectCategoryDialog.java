package com.kobra.money.view.dialog.select;


import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.entity.Category;
import com.kobra.money.include.Finder;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.view.dialog.FullscreenDialog;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.input.select.SelectCategoryTable;
import com.kobra.money.view.table.CategoryTable;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryDialog extends SelectDialog {
    private SelectCategoryTable categoryTable;

    public SelectCategoryDialog(Context context) {
        super(context, R.layout.dialog_select_category);
        setDialogTitle(context.getString(R.string.select_category_heading));
        categoryTable = new SelectCategoryTable(context, dialog.findViewById(R.id.categoryTable));
    }

    public void setMultiple(boolean multiple) {
        categoryTable.setMultiple(multiple);
    }

    public void setCategories(List<Category> categories) {
        categoryTable.setCategories(categories);
        categoryTable.print(10);
    }

    @Override
    public String getSelected() {
        return categoryTable.getValue();
    }

    @Override
    public void setSelected(String selected) {
        categoryTable.setValue(selected);
    }

    @Override
    public boolean checkSelected() {
        return !getSelected().equals("");
    }
}
