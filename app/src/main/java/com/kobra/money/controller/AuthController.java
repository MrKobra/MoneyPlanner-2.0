package com.kobra.money.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.kobra.money.Login;
import com.kobra.money.model.OperationModel;
import com.kobra.money.model.UserModel;
import com.kobra.money.request.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.HashMap;

public class AuthController {
    private Context context;
    private CustomRequest request;

    public static UserModel.User authUser;

    public AuthController(Context context) {
        this.context = context;
        request = new CustomRequest(Volley.newRequestQueue(context));
    }


    public void isLogin(String token, Event event) {
        if(token != null && token.length() > 0) {
            HashMap<String, String> args = new HashMap<String, String>() {{
                put("app_token", token);
            }};
            checkAuth(args, new Event() {
                @Override
                public void onSuccess() {
                    if (authUser == null) {
                        Intent authIntent = new Intent(context, Login.class);
                        context.startActivity(authIntent);
                        if (event != null) event.onError();
                    } else {
                        if (event != null) event.onSuccess();
                    }
                }

                @Override
                public void onError() {
                    Intent authIntent = new Intent(context, Login.class);
                    context.startActivity(authIntent);
                    if (event != null) event.onError();
                }
            });
        } else {
            Intent authIntent = new Intent(context, Login.class);
            context.startActivity(authIntent);
            if (event != null) event.onError();
        }
    }

    private void checkAuth(HashMap<String, String> args, Event event) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response", response);
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        authUser = new UserModel.User(result.getJSONObject("user"));
                        if(event != null) event.onSuccess();
                    } else {
                        if(event != null) event.onError();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    if(event != null) event.onError();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(event != null) event.onError();
            }
        };

        request.request(responseListener, errorListener, CustomRequest.Entity.USER,
                "check_auth", args);
    }

    public interface Event {
        void onSuccess();
        void onError();
    }
}
