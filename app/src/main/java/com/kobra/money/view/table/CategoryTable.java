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
import com.kobra.money.include.ArrayListListener;
import com.kobra.money.view.form.FormFieldView;

import java.util.ArrayList;
import java.util.List;

public class CategoryTable extends CustomTable {
    protected List<Category> categories;
    protected ArrayListListener<CategoryTableItem> categoryItems;

    public CategoryTable(Context context, TableLayout table) {
        super(context, table);
        categoryItems = new ArrayListListener<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        generateCategoryTableItems();
    }

    public ArrayListListener<CategoryTableItem> getCategoryItems() {
        return categoryItems;
    }

    public void print(int rowPadding) {
        super.print(getCategoryViews(), rowPadding);
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
