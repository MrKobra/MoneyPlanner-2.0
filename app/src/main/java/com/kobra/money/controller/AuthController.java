package com.kobra.money.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.LoginActivity;
import com.kobra.money.MainActivity;
import com.kobra.money.R;
import com.kobra.money.entity.User;
import com.kobra.money.include.AppSettings;
import com.kobra.money.include.UserException;
import com.kobra.money.model.AuthModel;
import com.kobra.money.model.Model;
import com.kobra.money.request.HttpRequest;
import com.kobra.money.view.form.Form;
import com.kobra.money.view.form.LoginForm;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthController extends Controller {
    private LoginForm loginForm;
    /* ModelRequest queue */
    private ModelRequestQueue authRequestQueue;
    /* Model */
    private AuthModel authModel;

    /* Authed user */
    public static User authUser;

    private AuthController(Context context) {
        super(context);
    }


    public void isLogin(String token, Event event) {
        if(token != null) {
            HashMap<String, String> args = new HashMap<String, String>() {{
                put("app_token", token);
            }};

            authRequestQueue.add(new ModelRequest() {
                @Override
                public void request() {
                    authModel.checkAuth(args, new AuthModel.AuthEvent() {
                        @Override
                        public void onSuccess(JSONObject user) {
                            try {
                                authUser = new User(user);
                                if (event != null) event.onSuccess();
                            } catch (JSONException exception) {
                                exception.printStackTrace();
                                Intent authIntent = new Intent(context, LoginActivity.class);
                                context.startActivity(authIntent);
                            }
                        }

                        @Override
                        public void onError(String error) {
                            Intent authIntent = new Intent(context, LoginActivity.class);
                            context.startActivity(authIntent);
                            if (event != null) event.onError(error);
                        }
                    });
                }
            });
        } else {
            Intent authIntent = new Intent(context, LoginActivity.class);
            context.startActivity(authIntent);
            if (event != null) event.onError(null);
        }
    }

    private void auth(HashMap<String, String> args, Event event) {
        authRequestQueue.add(new ModelRequest() {
            @Override
            public void request() {
                authModel.auth(args, new AuthModel.AuthEvent() {
                    @Override
                    public void onSuccess(JSONObject user) {
                        try {
                            authUser = new User(user);
                            AppSettings appSettings = new AppSettings(context);
                            appSettings.addProperty("token", user.getString("app_token"));
                            if (event != null) event.onSuccess();
                        }
                        catch (JSONException exception) {
                            exception.printStackTrace();
                            if (event != null) event.onError(context.getString(R.string.get_error));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        if(event != null) event.onError(error);
                    }
                });
            }
        });
    }

    private void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
        loginForm.setSubmitEvent(new Form.Submit() {
            @Override
            public void onSuccess() {
                loginForm.showFormLoader();

                auth(loginForm.getFormValues(), new Event() {
                    @Override
                    public void onSuccess() {
                        loginForm.hideFormLoader();
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        context.startActivity(mainIntent);
                    }

                    @Override
                    public void onError(String error) {
                        loginForm.hideFormLoader();
                        Toast.makeText(context, UserException.getErrorMessageByTable(3),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(UserException exception, Form.FormField formField) {
                formField.getFieldView().setError(true);
            }
        });
    }

    private void createAuthModel() {
        if (authRequestQueue == null) {
            authModel = new AuthModel(context);
            authRequestQueue = new ModelRequestQueue(authModel);
            authModel.setReady(new Model.ReadyEvent() {
                @Override
                public void onReady() {
                    while(!authModel.isRun() && !authRequestQueue.isEmpty()) {
                        ModelRequest modelRequest = authRequestQueue.poll();
                        if(modelRequest != null) modelRequest.request();
                    }
                }
            });
        }
    }

    public static class Builder {
        private AuthController controller;

        public Builder(Context context) {
            controller = new AuthController(context);
            controller.createAuthModel();
        }

        public Builder setLoginOption(LoginForm loginForm) {
            controller.setLoginForm(loginForm);
            return this;
        }

        public AuthController getController() {
            return controller;
        }
    }
}
