package com.kobra.money.request;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kobra.money.include.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static HttpRequest httpRequest;
    private RequestQueue requestQueue;

    private HttpRequest(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public static HttpRequest getInstance(Context context) {
        if(httpRequest == null) {
            httpRequest = new HttpRequest(context);
        }

        return httpRequest;
    }

    public void sendRequest(HashMap<String, String> args, Entity entity, Action action,
                            Event event) {

        addServiceArgs(args, entity, action);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (event != null) event.onSuccess(jsonResponse);
                }
                catch (JSONException exception) {
                    if (event != null) event.onError(exception);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(event != null) event.onError(error);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Config.httpRequestHost, responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return args;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static void addServiceArgs(@NonNull HashMap<String, String> args, Entity entity,
                                       Action action) {
        if(action != null) {
            args.put("action", action.getActionString());
        }

        if(entity != null) {
            args.put("entity", entity.getEntityString());
        }
    }

    public enum Entity {
        OPERATION("operation"),
        USER("user"),
        CATEGORY("category"),
        TYPE("type");

        private final String entity;

        Entity(String entity) {
            this.entity = entity;
        }

        public String getEntityString() {
            return entity;
        }
    }

    public enum Action {
        ADD("create"),
        DELETE("delete"),
        UPDATE("update"),
        GET("get"),
        AUTH("auth"),
        CHECK_AUTH("check_auth");

        private final String action;

        Action(String action) {
            this.action = action;
        }

        public String getActionString() {
            return action;
        }
    }

    public interface Event {
        void onSuccess(JSONObject response);
        void onError(Exception exception);
    }
}
