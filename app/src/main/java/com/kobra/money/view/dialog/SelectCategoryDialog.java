package com.kobra.money.view.dialog;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.include.Finder;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.table.CategoryTable;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryDialog extends FullscreenDialog {
    private CategoryTable categoryTable;
    private boolean multiple;
    private List<Long> selected;
    private Event event;

    public SelectCategoryDialog(Context context) {
        super(context, R.layout.dialog_select_category);
        setDialogTitle(context.getString(R.string.select_category_heading));
        categoryTable = new CategoryTable(context, dialog.findViewById(R.id.categoryTable));
        multiple = false;
        selected = new ArrayList<>();
        setAcceptOnClickListener();
    }

    public void setCategories(List<CategoryModel.Category> categories) {
        categoryTable.setCategories(categories);
        categoryTable.print(10);
        selectCategory();
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private void selectCategory() {
        List<CategoryTable.CategoryTableItem> categoryTableItems = categoryTable.getCategoryItems();
        if(categoryTableItems != null && categoryTableItems.size() > 0) {
            for(CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                categoryTableItem.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CategoryModel.Category clickCategory = categoryTableItem.getCategory();

                        if(multiple) {

                        } else {
                            long selectId = (selected.size() > 0) ? selected.get(0) : 0;
                            for (CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                                categoryTableItem.setSelectedView(false);
                            }

                            if (clickCategory.getId() == selectId) {
                                selectId = 0L;
                            } else {
                                selectId = clickCategory.getId();
                                categoryTableItem.setSelectedView(true);
                            }

                            if(selected.size() == 0) {
                                selected.add(selectId);
                            } else {
                                selected.set(0, selectId);
                            }
                        }
                    }
                });
            }
        }
    }

    private void setAcceptOnClickListener() {
        CardView acceptButton = dialog.findViewById(R.id.acceptDialogButton);
        if(acceptButton != null) {
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selected.size() == 0 || selected.get(0) == 0) {
                        Toast.makeText(context, R.string.category_not_selected, Toast.LENGTH_SHORT).show();
                    } else {
                        if(event != null) event.onAccept();
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public interface Event {
        void onAccept();
    }
}
