package com.ministore.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.ResponseCastObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity {
    private EditText editTextConfirmOTP;
    private Button buttonConfirm,btnThoat;
    public String OTP="";
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        OTP=getIntent().getStringExtra("OTP");
        username=getIntent().getStringExtra("username");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setControl();
        setEvent();
    }


    private void setEvent() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextConfirmOTP.getText().toString().equals(OTP)){
                    Intent intent = new Intent(OTPActivity.this, NewPasswordActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);

                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        editTextConfirmOTP=findViewById(R.id.editTextConfirmOTP);
        buttonConfirm=findViewById(R.id.buttonConfirm);
        btnThoat = findViewById(R.id.buttonThoatHiem);
    }


}
