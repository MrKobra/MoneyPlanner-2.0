package com.kobra.money.request;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.kobra.money.include.Config;

import java.util.HashMap;
import java.util.Map;

public class CustomRequest {
    private RequestQueue requestQueue;

    public CustomRequest(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public void request(Response.Listener<String> responseListener,
                        Response.ErrorListener errorListener, Entity entity, String action,
                        HashMap<String, String> args) {
        addServiceArgs(args, action, entity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.httpRequestHost,
                responseListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                return args;
            }
        };
        requestQueue.add(stringRequest);
    }

    private static void addServiceArgs(@NonNull HashMap<String, String> args, String action,
                                       Entity entity) {
        if(action.length() > 0) {
            args.put("action", action);
        }
        if(entity != null) {
            switch (entity) {
                case OPERATION:
                    args.put("entity", "operation");
                    break;
                case USER:
                    args.put("entity", "user");
                    break;
                case CATEGORY:
                    args.put("entity", "category");
                    break;
                case TYPE:
                    args.put("entity", "type");
                    break;
            }
        }
    }

    public enum Entity {
        OPERATION,
        USER,
        CATEGORY,
        TYPE
    }
}
