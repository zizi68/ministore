package com.ministore.android.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ministore.android.R;

import com.ministore.android.fragment._UserInfoFragment;

import com.ministore.android.model.User;

public class _UserInfoActivity extends AppCompatActivity {
    public static final String KEY_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_user_info);
        Intent intent = getIntent();
        if (intent == null) return;
        User user = (User) intent.getSerializableExtra(KEY_USER);
        _UserInfoFragment fragment = (_UserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_user_info);
        if (fragment != null) {
            fragment.setInfo(user);
        }
    }


}
