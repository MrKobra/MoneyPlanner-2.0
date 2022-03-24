package com.kobra.money.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TypeModel {

    public static class Type {
        private long id;
        private String title;
        private String slug;
        private String icon;

        public Type(JSONObject item) throws JSONException {
            id = item.getLong("id");
            title = item.getString("title");
            slug = item.getString("slug");
            icon = item.getString("icon");
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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
    }
}
