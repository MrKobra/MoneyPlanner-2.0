package com.kobra.money.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.controller.AddOperationController;
import com.kobra.money.controller.OperationController;
import com.kobra.money.request.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryModel {
    private List<Category> list;
    private boolean running;
    private AddOperationController.Event event;
    private HashMap<String, String> queryArgs;

    public CategoryModel(AddOperationController.Event event) {
        this.event = event;
        list = new ArrayList<>();
        running = false;
    }

    public void setList(JSONArray items) {
        list.clear();
        add(items);
    }

    public void setList(HashMap<String, String> args, CustomRequest request, Event event) {
        setRunning(true);

        queryArgs = args;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        JSONArray items = result.getJSONArray("items");
                        setList(items);
                        if(event != null) event.onSuccess();
                    } else {
                        if(event != null) event.onError();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    if(event != null) event.onError();
                }

                setRunning(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(event != null) event.onError();
                setRunning(false);
            }
        };

        request.request(responseListener, errorListener, CustomRequest.Entity.CATEGORY,
                "get", args);
    }

    public void update(CustomRequest request, OperationModel.Event event) {
        setRunning(true);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        JSONArray items = result.getJSONArray("items");
                        setList(items);
                        if(event != null) event.onSuccess();
                    } else {
                        if(event != null) event.onError();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    if(event != null) event.onError();
                }

                setRunning(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(event != null) event.onError();
                setRunning(false);
            }
        };

        request.request(responseListener, errorListener, CustomRequest.Entity.CATEGORY,
                "get", queryArgs);
    }

    public boolean isRunning() {
        return running;
    }

    public List<Category> getList() {
        return list;
    }

    private void add(JSONArray items) {
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                Category category = new Category(item);
                list.add(category);
            }
            catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void setRunning(boolean flag) {
        running = flag;
        if(!isRunning()) {
            event.onReady();
        }
    }

    public interface Event {
        void onSuccess();
        void onError();
    }

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
            user_id = (item.getString("user_id").equals("null")) ? 0 : item.getLong("user_id");
            type_id = item.getLong("type_id");
            title = item.getString("title");
            slug = item.getString("slug");
            icon = item.getString("icon");
            delete = !item.getString("is_delete").equals("null");
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
