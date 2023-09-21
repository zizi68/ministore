package com.ministore.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.R;
import com.ministore.android.adapter.ResponseTestString;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.ResponseCastObject;
import com.ministore.android.model.User;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseEmailOrPhoneActivity extends AppCompatActivity {
    private TextView txtUsertoRecoverPassword;
    private Button btnTiepTuc,btnThoat;
    private String email ;
    private String phone;
    private RadioButton btnEmail, btnPhone;
    private String username;
    private String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose__email_phone);
        username=getIntent().getStringExtra("username");
        setControl();
        setEvent();
        email = getIntent().getStringExtra("gmail");
        phone = getIntent().getStringExtra("phone");
        btnEmail.setText( "Email: " + email.substring(0,4) + "******" +email.substring(email.length()-4,email.length()));
        btnPhone.setText("Phone: " + phone.substring(0,3) + "******" +phone.substring(phone.length()-3,phone.length()));
        OTP = createCode();
    }

    private void setControl() {
        txtUsertoRecoverPassword = findViewById(R.id.txtUsername);
        btnTiepTuc = findViewById(R.id.btn_tiepTup);
        btnThoat = findViewById(R.id.btn_thoat);
        btnEmail = findViewById(R.id.checkedEmail);
        btnPhone = findViewById(R.id.checkedPhone);
    }

    private void setEvent() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( btnEmail.isChecked()){
                   ApiService.apiService.getOTPbyEmail(email,OTP).enqueue(new Callback<String>() {

                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {

                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {


                       }
                   });

                   Intent intent = new Intent(ChooseEmailOrPhoneActivity.this, OTPActivity.class);

                   intent.putExtra("username",username);
                   intent.putExtra("OTP",OTP);
                   startActivity(intent);
               }else{
                   ApiService.apiService.getOTPbyPhone(phone).enqueue(new Callback<String>() {

                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {
                           Intent intent = new Intent(ChooseEmailOrPhoneActivity.this, OTPActivity.class);
                           OTP = response.body();
                           intent.putExtra("OTP",response.body());
                           intent.putExtra("username",username);
                           startActivity(intent);
                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {

                       }
                   });
               }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseEmailOrPhoneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    String createCode() {
        String code = "";
        Random rand = new Random();

        for(int i =1;i<=6;i++) {
            int tempIntCode = rand.nextInt(10);
            code +=tempIntCode;
        }

        return code;
    }
}
