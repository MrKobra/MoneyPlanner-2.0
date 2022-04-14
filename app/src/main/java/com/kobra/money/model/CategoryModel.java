package com.kobra.money.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.R;
import com.kobra.money.entity.Category;
import com.kobra.money.request.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryModel extends Model {
    private HashMap<String, String> args;

    public CategoryModel(Context context) {
        super(context);
    }

    public void getItemsFromHTTP(HashMap<String, String> args, CustomRequest request,
                                 GetEvent<Category> event) {
        setRun(true);
        this.args = args;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        JSONArray items = result.getJSONArray("items");
                        if(event != null) event.onSuccess(getCategoryList(items));
                    } else {
                        if(event != null) event.onError(context.getString(R.string.get_error));
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    if(event != null) event.onError(context.getString(R.string.get_error));
                }

                setRun(false);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(event != null) event.onError(context.getString(R.string.network_error));
                setRun(false);
            }
        };

        request.request(responseListener, errorListener, CustomRequest.Entity.CATEGORY,
                "get", args);
    }

    private List<Category> getCategoryList(JSONArray items) {
        List<Category> categories = new ArrayList<Category>();
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                Category category = new Category(item);
                categories.add(category);
            }
            catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
        return categories;
    }

    /*
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

    public void update(CustomRequest request, Event event) {
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
    } */
}
