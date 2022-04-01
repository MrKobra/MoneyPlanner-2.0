package com.kobra.money.view.form.add;

import android.content.Context;
import android.view.View;

import com.kobra.money.R;
import com.kobra.money.include.Finder;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.LoginForm;
import com.kobra.money.view.table.CategoryTable;

import java.util.List;

public class AddOperationForm extends Form {
    private CategoryTable categoryTable;

    private AddOperationForm(Context context) {
        super(context);
    }

    @Override
    public void submit() {

    }

    @Override
    public void setFormView(View formView) {
        super.setFormView(formView);
        categoryTable = new CategoryTable(context, formView.findViewById(R.id.categoryTable));
        initFields();
    }

    public void setCategories(List<CategoryModel.Category> categories) {
        if(categoryTable != null) {
            categoryTable.setCategories(categories);
            categoryTable.print(10);
            selectCategory();
        }
    }

    private void selectCategory() {
        List<CategoryTable.CategoryTableItem> categoryTableItems = categoryTable.getCategoryItems();
        if(categoryTableItems != null && categoryTableItems.size() > 0) {
            FormField field = formFields.get(Finder.searchByFieldName("category_id", formFields));
            for(CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                categoryTableItem.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long categoryId = Long.parseLong(field.getValue());
                        CategoryModel.Category clickCategory = categoryTableItem.getCategory();

                        for(CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                            categoryTableItem.setSelectedView(false);
                        }

                        if(clickCategory.getId() == categoryId) {
                            field.setValue("");
                        } else {
                            field.setValue(Long.toString(clickCategory.getId()));
                            categoryTableItem.setSelectedView(true);
                        }
                    }
                });
            }
        }
    }

    private void initFields() {
        if(formView != null) {
            formFields.add(new FormField("category", "category_id"));
            formFields.add(new FormField("amount", "amount"));
        }
    }

    public static class Builder extends Form.Builder {
        public Builder(Context context) {
            form = new AddOperationForm(context);
            this.context = context;
        }
    }
}
