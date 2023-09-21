package com.ministore.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.User;
import com.ministore.android.response.ResponseBody;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView txtUsertoRecoverPassword;
    private Button btnTiepTuc,btnThoat;
    private String email ;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_username);

        setControl();
        setEvent();
    }

    private void setControl() {
        txtUsertoRecoverPassword = findViewById(R.id.txtUsername);
        btnTiepTuc = findViewById(R.id.btnNext);
        btnThoat = findViewById(R.id.buttonThoat);
    }

    private void setEvent() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtUsertoRecoverPassword.getText().toString().isEmpty()){
                    showMessage("Enter username");
                    //Toast.makeText(ForgotPasswordActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                String username = txtUsertoRecoverPassword.getText().toString();
                ApiService.apiService.getGmailByUsername(username).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {



                        if (response.code() == 200) {
                            Gson gson = new GsonBuilder().create();
                            try {

                                User user  = response.body();
                                email = user.getEmail();
                                phone = user.getPhone();

                                Intent intent = new Intent(ForgotPasswordActivity.this, ChooseEmailOrPhoneActivity.class);
                                intent.putExtra("gmail", email);
                                intent.putExtra("phone", phone);
                                intent.putExtra("username",username);
                                startActivity(intent);
                            }
                            catch (Exception e) {
                                // handle failure to read error
                                showMessage(e.getMessage());
                            }
                            return;
                        }


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });



            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
