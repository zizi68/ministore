package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.ministore.android.MyApplication;
import com.ministore.android.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgBackground, imgLogo;
    private TextView tvAppName, tvWelcome;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setControl();
        setEvent();
    }

    private void setControl() {
        imgBackground = findViewById(R.id.img_background);
        imgLogo = findViewById(R.id.img_logo);
        tvAppName = findViewById(R.id.tv_app_name);
        tvWelcome = findViewById(R.id.tv_welcome);
        lottieAnimationView = findViewById(R.id.lottie_animation_view);
    }

    private void setEvent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        }, 2000);
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }
}