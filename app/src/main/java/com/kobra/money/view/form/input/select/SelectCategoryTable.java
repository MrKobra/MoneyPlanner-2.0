package com.kobra.money.view.form.input.select;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import androidx.cardview.widget.CardView;

import com.kobra.money.R;
import com.kobra.money.include.ArrayListListener;
import com.kobra.money.view.form.FormFieldView;
import com.kobra.money.view.table.CategoryTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class SelectCategoryTable extends CategoryTable implements FormFieldView {
    private boolean multiple;
    private List<Long> selected;

    public SelectCategoryTable(Context context, TableLayout table) {
        super(context, table);
        multiple = false;
        selected = new ArrayList<>();
        setSelectCategoryListener();
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

    @Override
    public String getValue() {
        StringJoiner value = new StringJoiner(",");
        if(selected.size() > 0) {
            for (Long select : selected) {
                value.add(Long.toString(select));
            }
        }
        return value.toString();
    }

    @Override
    public void setValue(String value) {
        List<String> values = Arrays.asList(value.split(","));
        if(values.size() > 0) {
            /* For future */
            if (multiple) {

            } else {
                try {
                    long id = Long.parseLong(values.get(0));
                    if (selected.size() > 0) {
                        selected.set(0, id);
                    } else {
                        selected.add(0, id);
                    }

                    for (CategoryTableItem categoryItem : categoryItems) {
                        if (id == categoryItem.getCategory().getId()) {
                            setSelectedView(categoryItem, true);
                        } else {
                            setSelectedView(categoryItem, false);
                        }
                    }
                } catch(NumberFormatException exception) {
                    for (CategoryTableItem categoryItem : categoryItems) {
                        setSelectedView(categoryItem, false);
                    }
                }
            }
        }
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    private void setSelectedView(CategoryTableItem item, boolean select) {
        CardView categoryIcon = (CardView) item.getView().findViewById(R.id.categoryIcon);
        if(categoryIcon != null) {
            if(select) {
                categoryIcon.setCardBackgroundColor(context.getColor(R.color.active));
            } else {
                categoryIcon.setCardBackgroundColor(context.getColor(R.color.normal));
            }
        }
    }

    private void setSelectCategoryListener() {
        categoryItems.setAddListener(new ArrayListListener.AddListener<CategoryTableItem>() {
            @Override
            public void onAdd(CategoryTableItem item) {
                item.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long categoryId = item.getCategory().getId();
                        boolean isFind = false;
                        if(selected.size() > 0) {
                            for (long id : selected) {
                                if (id == categoryId) {
                                    isFind = true;
                                    break;
                                }
                            }
                        }

                        if(isFind) {
                            selected.remove(categoryId);
                            setSelectedView(item, false);
                        } else {
                            /* For future */
                            if (multiple) {

                            } else {
                                if(selected.size() > 0) {
                                    selected.set(0, categoryId);
                                } else {
                                    selected.add(categoryId);
                                }

                                for (CategoryTableItem categoryItem : categoryItems) {
                                    setSelectedView(categoryItem, false);
                                }

                                setSelectedView(item, true);
                            }
                        }
                    }
                });
            }
        });
    }
}
