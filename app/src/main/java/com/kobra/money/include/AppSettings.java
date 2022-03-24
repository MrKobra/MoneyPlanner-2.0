package com.kobra.money.include;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSettings {
    private final SharedPreferences app_settings;
    private final SharedPreferences.Editor editor;

    public AppSettings(Context context) {
        app_settings = context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        editor = app_settings.edit();
    }

    public void addProperty(String name, String value) {
        editor.putString(name, value);
        editor.apply();
    }

    public String getProperty(String name) {
        return app_settings.getString(name, null);
    }
}
