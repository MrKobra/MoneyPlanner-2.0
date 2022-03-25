package com.kobra.money.view.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kobra.money.R;
import com.kobra.money.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryTable {
    private Context context;
    private CustomTable table;
    private List<CategoryModel.Category> categories;
    private List<CategoryTableItem> categoryItems;

    public CategoryTable(Context context, TableLayout table) {
        this.context = context;
        this.table = new CustomTable(context, table);
        categoryItems = new ArrayList<>();
    }

    public void setCategories(List<CategoryModel.Category> categories) {
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
            for(CategoryModel.Category category : categories) {
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

    public static class CategoryTableItem {
        private Context context;
        private View view;
        private CategoryModel.Category category;

        public CategoryTableItem(@NonNull Context context, @NonNull CategoryModel.Category category) {
            this.context = context;
            this.category = category;
            generateView();
        }

        public View getView() {
            return view;
        }

        public CategoryModel.Category getCategory() {
            return category;
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
