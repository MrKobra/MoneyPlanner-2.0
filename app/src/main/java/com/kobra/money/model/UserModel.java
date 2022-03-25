package com.kobra.money.model;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kobra.money.request.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UserModel {
    private User user;
    private boolean running;

    public UserModel() {
        running = false;
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(HashMap<String, String> args, CustomRequest request, OperationModel.Event event) {
        setRunning(true);
        user = null;

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Response", response);
                try {
                    JSONObject result = new JSONObject(response);
                    if(result.getInt("error") == 0) {
                        user = new User(result.getJSONObject("user"));
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

        request.request(responseListener, errorListener, CustomRequest.Entity.USER,
                "check_auth", args);
    }

    public boolean isRunning() {
        return running;
    }

    private void setRunning(boolean running) {
        this.running = running;
    }

    public interface Event {
        void onSuccess();
        void onError();
    }

    public static class User {
        private long id;
        private String username;
        private String name;
        private String password;
        private String email;

        public User(JSONObject item) throws JSONException {
            id = item.getLong("id");
            username = item.getString("username");
            name = item.getString("name");
            password = item.getString("password");
            email = item.getString("email");
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
