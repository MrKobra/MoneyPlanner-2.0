package com.kobra.money.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class CategoryModel {

    public static class Category {
        private long id;
        private long user_id;
        private long type_id;
        private String title;
        private String slug;
        private String icon;
        private boolean delete;

        public Category(JSONObject item) throws JSONException {
            id = item.getLong("id");
            user_id = item.getLong("user_id");
            type_id = item.getLong("type_id");
            title = item.getString("title");
            slug = item.getString("slug");
            icon = item.getString("icon");
            delete = item.getInt("is_delete") > 0;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public long getType_id() {
            return type_id;
        }

        public void setType_id(long type_id) {
            this.type_id = type_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public boolean isDelete() {
            return delete;
        }

        public void setDelete(boolean delete) {
            this.delete = delete;
        }

        public int getIconID(Context context) {
            return context.getResources().getIdentifier(icon,"drawable", context.getPackageName());
        }
    }
}
