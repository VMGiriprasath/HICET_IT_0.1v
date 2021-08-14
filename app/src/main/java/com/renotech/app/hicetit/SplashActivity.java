package com.renotech.app.hicetit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Splash screen Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login_act_int=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login_act_int);
                SplashActivity.this.finish();
            }
        },2000);
    }
}