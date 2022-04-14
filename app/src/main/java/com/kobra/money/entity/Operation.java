package com.kobra.money.entity;

import com.kobra.money.model.CategoryModel;
import com.kobra.money.model.TypeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class Operation {
    private long id;
    private int amount;
    private long typeId;
    private long categoryId;
    private long bankCardId;
    private LocalDate date;
    private Category category;
    private Type type;

    public Operation(JSONObject item) throws JSONException {
        id = item.getLong("id");
        amount = item.getInt("amount");
        typeId = item.getLong("type_id");
        categoryId = item.getLong("category_id");
        bankCardId = item.getLong("bank_card_id");
        date = LocalDate.parse(item.getString("date"));
        try {
            JSONObject categoryJSON = item.getJSONObject("category");
            category = new Category(categoryJSON);
        }
        catch (JSONException exception) {
            category = null;
        }
        try {
            JSONObject typeJSON = item.getJSONObject("type");
            type = new Type(typeJSON);
        }
        catch (JSONException exception) {
            type = null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
