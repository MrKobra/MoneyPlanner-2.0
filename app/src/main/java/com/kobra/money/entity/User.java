package com.kobra.money.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
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
