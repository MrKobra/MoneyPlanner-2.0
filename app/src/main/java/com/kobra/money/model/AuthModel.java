package com.kobra.money.model;

import android.content.Context;

import com.kobra.money.R;
import com.kobra.money.entity.User;
import com.kobra.money.request.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AuthModel extends Model {
    public AuthModel(Context context) {
        super(context);
    }

    public void checkAuth(HashMap<String, String> args, AuthEvent event) {
        setRun(true);

        HttpRequest.getInstance(context).sendRequest(args, HttpRequest.Entity.USER,
                HttpRequest.Action.CHECK_AUTH, new HttpRequest.Event() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if(response.getInt("error") == 0) {
                                if(event != null) event.onSuccess(response.getJSONObject("user"));
                            } else {
                                if(event != null) event.onError(context.getString(R.string.auth_error));
                            }
                        } catch (JSONException exception) {
                            if(event != null) event.onError(context.getString(R.string.get_error));
                        }

                        setRun(false);
                    }

                    @Override
                    public void onError(Exception exception) {
                        if(event != null) event.onError(context.getString(R.string.network_error));
                        setRun(false);
                    }
                });
    }

    public void auth(HashMap<String, String> args, AuthEvent event) {
        setRun(false);

        HttpRequest.getInstance(context).sendRequest(args, HttpRequest.Entity.USER,
                HttpRequest.Action.AUTH, new HttpRequest.Event() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if(response.getInt("error") == 0) {
                                if(event != null) event.onSuccess(response.getJSONObject("user"));
                            } else {
                                if(event != null) event.onError(context.getString(R.string.auth_error));
                            }
                        } catch (JSONException exception) {
                            if(event != null) event.onError(context.getString(R.string.get_error));
                        }

                        setRun(false);
                    }

                    @Override
                    public void onError(Exception exception) {
                        if(event != null) event.onError(context.getString(R.string.network_error));
                        setRun(false);
                    }
                });
    }

    public interface AuthEvent {
        void onSuccess(JSONObject user);
        void onError(String error);
    }
}
