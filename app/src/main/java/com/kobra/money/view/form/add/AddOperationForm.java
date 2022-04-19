package com.kobra.money.view.form.add;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kobra.money.R;
import com.kobra.money.entity.Category;
import com.kobra.money.entity.User;
import com.kobra.money.include.Finder;
import com.kobra.money.include.UserException;
import com.kobra.money.include.Validate;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.view.FormFieldView;
import com.kobra.money.view.dialog.EditTextDialog;
import com.kobra.money.view.dialog.SelectCategoryDialog;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.LoginForm;
import com.kobra.money.view.input.AmountEditText;
import com.kobra.money.view.table.CategoryTable;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AddOperationForm extends Form {
    private CategoryTable categoryTable;
    private LinearLayout selectCategoryButton;
    private SelectCategoryDialog selectCategoryDialog;
    private AmountEditText amountEdit;

    private EditTextDialog amountEditDialog;

    private AddOperationForm(Context context) {
        super(context);
        initSelectCategoryDialog();
        initAmountEditDialog();
    }

    @Override
    public void submit() {
        UserException exception = new UserException();
        boolean success = true;
        for (FormField formField : formFields) {
            if(formField.isRequired()) {
                exception = Validate.validateFormField(formField);
                if(exception.getCode() != 0) {
                    submitEvent.onError(exception, formField);
                    success = false;
                }
            }
        }

        if(success) {
            submitEvent.onSuccess();
        }
    }

    @Override
    public void resetForm() {
        amountEdit.setValue("");
        amountEditDialog.getEditText().setValue("");

        for(CategoryTable.CategoryTableItem categoryTableItem : categoryTable.getCategoryItems()) {
            categoryTableItem.setSelectedView(false);
        }

        for(FormField field : formFields) {
            field.getFieldView().setValue("");
        }
    }

    @Override
    public void setFormView(@NonNull View formView) {
        super.setFormView(formView);
        categoryTable = new CategoryTable(context, formView.findViewById(R.id.categoryTable));
        amountEdit = new AmountEditText(context, formView.findViewById(R.id.editAmount));
        amountEdit.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountEditDialog.show();
            }
        });
        initFields();
        initSelectCategoryButton();
    }

    public void setCategories(List<Category> categories) {
        if(categoryTable != null) {
            categoryTable.setCategories(categories.subList(0, 3));
            categoryTable.print(0);
            selectCategoryDialog.setCategories(categories);
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
                        long categoryId = (field.getFieldView().getValue().isEmpty()) ? 0 : Long.parseLong(field.getFieldView().getValue());
                        Category clickCategory = categoryTableItem.getCategory();

                        for(CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                            categoryTableItem.setSelectedView(false);
                        }

                        if(clickCategory.getId() == categoryId) {
                            field.getFieldView().setValue("");
                        } else {
                            field.getFieldView().setValue(Long.toString(clickCategory.getId()));
                            categoryTableItem.setSelectedView(true);
                        }

                        CardView categoryIcon = (CardView) selectCategoryButton.findViewById(R.id.categoryIcon);
                        categoryIcon.setCardBackgroundColor(context.getColor(R.color.normal));
                    }
                });
            }
        }
    }

    private void initSelectCategoryButton() {
        if(formView != null) {
            selectCategoryButton = (LinearLayout) formView.findViewById(R.id.selectCategoryButton);
            if(selectCategoryButton != null) {
                ImageView buttonIcon = selectCategoryButton.findViewById(R.id.categoryImg);
                buttonIcon.setImageResource(R.drawable.ic_other);
                buttonIcon.setColorFilter(context.getColor(R.color.minor));

                TextView buttonText = selectCategoryButton.findViewById(R.id.categoryTitle);
                buttonText.setText(R.string.select_category_button);

                selectCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectCategoryDialog.show();
                    }
                });
            }
        }
    }

    private void initFields() {
        if(formView != null) {
            formFields.add(new FormField("category", "category_id", amountEdit));
            formFields.add(new FormField("amount", "amount", categoryTable));
        }
    }

    private void initSelectCategoryDialog() {
        selectCategoryDialog = new SelectCategoryDialog(context);
        selectCategoryDialog.setEvent(new SelectCategoryDialog.Event() {
            @Override
            public void onAccept() {
                Long selected = selectCategoryDialog.getSelected().get(0);
                FormField field = formFields.get(Finder.searchByFieldName("category_id", formFields));
                field.getFieldView().setValue(Long.toString(selected));

                List<CategoryTable.CategoryTableItem> categoryTableItems = categoryTable.getCategoryItems();
                boolean find = false;
                if(categoryTableItems != null && categoryTableItems.size() > 0) {
                    for (CategoryTable.CategoryTableItem categoryTableItem : categoryTableItems) {
                        if(categoryTableItem.getCategory().getId() == selected) {
                            categoryTableItem.setSelectedView(true);
                            find = true;
                        } else {
                            categoryTableItem.setSelectedView(false);
                        }
                    }
                }

                CardView categoryIcon = (CardView) selectCategoryButton.findViewById(R.id.categoryIcon);
                if(!find) {
                    categoryIcon.setCardBackgroundColor(context.getColor(R.color.active));
                } else {
                    categoryIcon.setCardBackgroundColor(context.getColor(R.color.normal));
                }
            }
        });
    }

    private void initAmountEditDialog() {
        amountEditDialog = new EditTextDialog(context);
        amountEditDialog.setDialogTitle(context.getString(R.string.amount_field));
        amountEditDialog.setEditText(new AmountEditText(context));
        amountEditDialog.setHintText(context.getString(R.string.amount_field));
        amountEditDialog.setEditTextType(InputType.TYPE_CLASS_NUMBER);
        amountEditDialog.setEvent(new EditTextDialog.Event() {
            @Override
            public void onSuccess(String value) {
                amountEdit.setValue(value);

                FormField amountField = formFields.get(Finder.searchByFieldName("amount", formFields));
                amountField.getFieldView().setValue(value);
            }
        });
    }

    public static class Builder extends Form.Builder {
        public Builder(Context context) {
            form = new AddOperationForm(context);
            this.context = context;
        }
    }
}
