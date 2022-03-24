package com.kobra.money.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.kobra.money.Login;
import com.kobra.money.model.OperationModel;
import com.kobra.money.model.UserModel;
import com.kobra.money.request.CustomRequest;

import java.util.ArrayDeque;
import java.util.HashMap;

public class AuthController {
    private Context context;
    private CustomRequest request;

    private UserModel model;

    public AuthController(Context context) {
        this.context = context;
        request = new CustomRequest(Volley.newRequestQueue(context));
        createModel();
    }

    public UserModel.User getUser() {
        return model.getUser();
    }

    public void isLogin(String token, Event event) {
        if(token != null && token.length() > 0) {
            HashMap<String, String> args = new HashMap<String, String>() {{
                put("app_token", token);
            }};
            model.setUser(args, request, new OperationModel.Event() {
                @Override
                public void onSuccess() {
                    if (model.getUser() == null) {
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

    private void createModel() {
        model = new UserModel();
    }

    public interface Event {
        void onSuccess();
        void onError();
    }
}
