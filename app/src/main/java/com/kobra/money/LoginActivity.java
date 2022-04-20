package com.kobra.money;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.kobra.money.controller.AuthController;
import com.kobra.money.view.form.LoginForm;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private AuthController authController;

    private LoginForm loginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        loginForm = (LoginForm) new LoginForm.Builder(context)
                .setFormView(findViewById(R.id.loginForm))
                .getForm();
        authController = new AuthController.Builder(context)
                .setLoginOption(loginForm)
                .getController();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}