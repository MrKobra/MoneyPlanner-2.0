package com.kobra.money;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import com.kobra.money.controller.AuthController;
import com.kobra.money.include.AppSettings;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private AuthController authController;
    private AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        authController = new AuthController(context);
        appSettings = new AppSettings(context);

        String token = appSettings.getProperty("token");
        authController.isLogin(token, new AuthController.Event() {
            @Override
            public void onSuccess() {
                loadFragment(HomeFragment.newInstance());
            }

            @Override
            public void onError() {
                //finish();
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }
}