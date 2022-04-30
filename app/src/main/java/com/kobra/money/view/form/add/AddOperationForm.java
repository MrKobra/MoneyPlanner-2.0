package com.kobra.money.view.form.add;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kobra.money.R;
import com.kobra.money.entity.Category;
import com.kobra.money.include.Finder;
import com.kobra.money.include.UserException;
import com.kobra.money.include.Validate;
import com.kobra.money.view.dialog.EditTextDialog;
import com.kobra.money.view.dialog.select.SelectCategoryDialog;
import com.kobra.money.view.dialog.select.SelectDialogInterface;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.input.edittext.AmountEditText;
import com.kobra.money.view.form.input.select.SelectCategoryTable;

import java.util.List;

public class AddOperationForm extends Form {
    private SelectCategoryTable categoryTable;
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
        amountEditDialog.getEditText().setValue("");
        selectCategoryDialog.setSelected("");

        for(FormField field : formFields) {
            field.getFieldView().setValue("");
        }
    }

    @Override
    public void setFormView(@NonNull View formView) {
        super.setFormView(formView);
        categoryTable = new SelectCategoryTable(context, formView.findViewById(R.id.categoryTable));
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
                        selectCategoryDialog.setSelected(categoryTable.getValue());
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
        selectCategoryDialog.setOnAcceptClickListener(new SelectDialogInterface.OnAcceptListener() {
            @Override
            public void onSuccess() {
                categoryTable.setValue(selectCategoryDialog.getSelected());
            }

            @Override
            public void onError() {
                Toast.makeText(context, R.string.category_not_selected, Toast.LENGTH_SHORT).show();
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
