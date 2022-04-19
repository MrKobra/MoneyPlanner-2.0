package com.kobra.money.view.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.entity.Category;
import com.kobra.money.model.CategoryModel;
import com.kobra.money.view.FormFieldView;
import com.kobra.money.view.form.Form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CategoryTable implements FormFieldView {
    private Context context;
    private CustomTable table;
    private List<Category> categories;
    private List<CategoryTableItem> categoryItems;

    private List<String> selected;

    public CategoryTable(Context context, TableLayout table) {
        this.context = context;
        this.table = new CustomTable(context, table);
        categoryItems = new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        generateCategoryTableItems();
    }

    public List<CategoryTableItem> getCategoryItems() {
        return categoryItems;
    }

    public void setColumn(int column) {
        table.setColumn(column);
    }

    public void print(int rowPadding) {
        table.print(getCategoryViews(), rowPadding);
    }

    private void generateCategoryTableItems() {
        categoryItems.clear();

        if(categories != null && categories.size() > 0) {
            for(Category category : categories) {
                categoryItems.add(new CategoryTableItem(context, category));
            }
        }
    }

    private List<View> getCategoryViews() {
        List<View> views = new ArrayList<>();

        if(categoryItems != null && categoryItems.size() > 0) {
            for(CategoryTableItem categoryTableItem : categoryItems) {
                views.add(categoryTableItem.getView());
            }
        }

        return views;
    }

    @Override
    public String getValue() {
        return String.join(",", selected);
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public void setError(boolean error) {
        if(error) {
            for (CategoryTableItem tableItem : categoryItems) {
                CardView categoryIcon = tableItem.getView().findViewById(R.id.categoryIcon);
                categoryIcon.setCardBackgroundColor(context.getColor(R.color.error));
            }
        } else {
            for (CategoryTableItem tableItem : categoryItems) {
                CardView categoryIcon = tableItem.getView().findViewById(R.id.categoryIcon);
                categoryIcon.setCardBackgroundColor(context.getColor(R.color.normal));
            }
        }
    }

    public static class CategoryTableItem {
        private Context context;
        private View view;
        private Category category;

        public CategoryTableItem(@NonNull Context context, @NonNull Category category) {
            this.context = context;
            this.category = category;
            generateView();
        }

        public View getView() {
            return view;
        }

        public Category getCategory() {
            return category;
        }

        public void setSelectedView(boolean select) {
            CardView categoryIcon = (CardView) view.findViewById(R.id.categoryIcon);
            if(categoryIcon != null) {
                if(select) {
                    categoryIcon.setCardBackgroundColor(context.getColor(R.color.active));
                } else {
                    categoryIcon.setCardBackgroundColor(context.getColor(R.color.normal));
                }
            }
        }

        private void generateView() {
            LayoutInflater inflater = LayoutInflater.from(context);
            LinearLayout categoryCard = (LinearLayout) inflater.inflate(R.layout.item_category_circle,
                    null);

            TextView categoryTitle = categoryCard.findViewById(R.id.categoryTitle);
            if(categoryTitle != null) categoryTitle.setText(category.getTitle());

            ImageView categoryImage = categoryCard.findViewById(R.id.categoryImg);
            if(categoryImage != null) categoryImage.setImageResource(category.getIconID(context));

            view = categoryCard;
        }
    }
}
