package com.kobra.money.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.kobra.money.LoginActivity;
import com.kobra.money.MainActivity;
import com.kobra.money.entity.User;
import com.kobra.money.include.AppSettings;
import com.kobra.money.include.UserException;
import com.kobra.money.model.UserModel;
import com.kobra.money.request.CustomRequest;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.LoginForm;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthController extends Controller {
    private LoginForm loginForm;

    public static User authUser;

    public AuthController(Context context) {
        super(context);
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        loginForm.setSubmitEvent(new Form.Submit() {
            @Override
            public void onSuccess(HashMap<String, String> fields) {
                loginForm.showFormLoader();

                auth(fields, new Event() {
                    @Override
                    public void onSuccess() {
                        loginForm.hideFormLoader();
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        context.startActivity(mainIntent);
                    }

                    @Override
                    public void onError() {
                        loginForm.hideFormLoader();
                        Toast.makeText(context, UserException.getErrorMessageByTable(3),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(UserException exception) {
                Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        Intent authIntent = new Intent(context, LoginActivity.class);
                        context.startActivity(authIntent);
                        if (event != null) event.onError();
                    } else {
                        if (event != null) event.onSuccess();
                    }
                }

                @Override
                public void onError() {
                    Intent authIntent = new Intent(context, LoginActivity.class);
                    context.startActivity(authIntent);
                    if (event != null) event.onError();
                }
            });
        } else {
            Intent authIntent = new Intent(context, LoginActivity.class);
            context.startActivity(authIntent);
            if (event != null) event.onError();
        }
    }

    private void checkAuth(HashMap<String, String> args, Event event) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        authUser = new User(result.getJSONObject("user"));
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

    private void auth(HashMap<String, String> args, Event event) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        AppSettings appSettings = new AppSettings(context);
                        authUser = new User(result.getJSONObject("user"));
                        appSettings.addProperty("token", result.getJSONObject("user").getString("app_token"));
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
                "auth", args);
    }

    public interface Event {
        void onSuccess();
        void onError();
    }
}
