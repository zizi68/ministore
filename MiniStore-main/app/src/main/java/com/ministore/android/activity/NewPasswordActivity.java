package com.ministore.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewPasswordActivity extends Activity {
    private EditText editTextNewPassword;
    private Button buttonConfirmNewPassword, btnThoat;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        username=getIntent().getStringExtra("username");

        setControl();
        setEvent();
    }

    private void setEvent() {
        buttonConfirmNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService.apiService.updatePasswordForgot( editTextNewPassword.getText().toString(),username).enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        editTextNewPassword=findViewById(R.id.editTextNewPassword);
        buttonConfirmNewPassword=findViewById(R.id.buttonConfirmNewPassword);
        btnThoat = findViewById(R.id.buttonThoatOTP);
    }
}
