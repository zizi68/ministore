package com.ministore.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnLogout;
    private ImageView imgVietNam, imgAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setControl();
        setEvent();
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        btnLogout.setOnClickListener(view -> {
            MyApplication.signOut(this);
        });
        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ganNgonNgu("en");
            }
        });
        imgVietNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ganNgonNgu("vi");
            }
        });
    }

    public void ganNgonNgu(String language) {
        Locale locale = new Locale(language);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        btnLogout = findViewById(R.id.btn_logout);
        imgAnh = findViewById(R.id.img_anh);
        imgVietNam = findViewById(R.id.img_vn);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setTitle("");
        }

        if (MyApplication.checkUserLogged()) {
            btnLogout.setVisibility(View.VISIBLE);
        }
        else {
            btnLogout.setVisibility(View.GONE);
        }
    }
}