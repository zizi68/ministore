package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.UpdatePasswordRequest;
import com.ministore.android.util.Constants;

import java.io.IOException;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private View rootView;
    private ImageView imgUserAvatar;
    private EditText edtOldPass, edtNewPass, edtConfirmPass;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setControl();
        setEvent();
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", null);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = edtOldPass.getText().toString().trim();
                String newPass = edtNewPass.getText().toString().trim();
                String confirmPass = edtConfirmPass.getText().toString().trim();

                if (oldPass.isEmpty()) {
                    showMessage(Constants.OLD_PASSWORD_BLANK.getMessage());
                    return;
                }
                if (newPass.isEmpty()) {
                    showMessage(Constants.NEW_PASSWORD_BLANK.getMessage());
                    return;
                }
                if (!confirmPass.equals(newPass)) {
                    showMessage(Constants.CONFIRM_PASSWORD_FAIL.getMessage());
                    return;
                }

                JwtResponse jwtResponse = Paper.book().read(MyApplication.KEY_JWT_RESPONSE);
                if (jwtResponse == null) {
                    return;
                }

                int id = jwtResponse.getId();
                UpdatePasswordRequest request = new UpdatePasswordRequest();
                request.setId(id);
                request.setOldPassword(oldPass);
                request.setNewPassword(newPass);

                ApiService.apiService.updatePassword(request)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.code() == 400) {
                                    try {
                                        showMessage(response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                                if (!response.isSuccessful()) {
                                    showMessage(Constants.VALIDATION_FAIL.getMessage() +"\n" + response.message());
                                    return;
                                }
                                Toast.makeText(ChangePasswordActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                showMessage(t.getMessage());
                            }
                        });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setControl() {
        rootView = findViewById(R.id.root_view);
        imgUserAvatar = findViewById(R.id.img_user_avatar);
        edtOldPass = findViewById(R.id.edt_old_password);
        edtNewPass = findViewById(R.id.edt_new_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
    }
}