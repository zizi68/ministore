package com.ministore.android.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.ForgotPasswordActivity;
import com.ministore.android.activity.MainActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.LoginRequest;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {

    private View view;
    private TextInputLayout tilUsername, tilPassword;
    private EditText edtUsername, edtPassword;
    private TextView txtForgetPassword;
    private AppCompatButton btnLogin;
    private TextView txtForgotPass;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_login, container, false);
        setControl();
        setEvent();
        setAnimation();
        return view;
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", null);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showMessage("Hãy nhập đầy đủ các thông tin!");
                    return;
                }

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(username);
                loginRequest.setPassword(password);

                ApiService.apiService.authenticateUser(loginRequest)
                        .enqueue(new Callback<JwtResponse>() {
                            @Override
                            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                                if (response.code() == 401) {
                                    showMessage("Tên đăng nhập hoặc mật khẩu của bạn không chính xác!");
                                    return;
                                }
                                if (!response.isSuccessful()) {
                                    showMessage("Đăng nhập thất bại!\n" + response.message());
                                    return;
                                }
                                JwtResponse jwtResponse = response.body();
                                Paper.book().write(MyApplication.KEY_JWT_RESPONSE, jwtResponse);
                                if (MyApplication.checkShipperPermission()) {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finishAffinity();
                                }else showMessage("Bạn không phải shipper!\n" + response.message());

                            }

                            @Override
                            public void onFailure(Call<JwtResponse> call, Throwable t) {
                                showMessage(t.getMessage());
                            }
                        });
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        tilUsername = view.findViewById(R.id.til_username);
        tilPassword = view.findViewById(R.id.til_password);
        edtUsername = view.findViewById(R.id.edt_login_username);
        edtPassword = view.findViewById(R.id.edt_login_password);
        txtForgetPassword = view.findViewById(R.id.txt_login_forget);
        btnLogin = view.findViewById(R.id.btn_login);
        txtForgotPass = view.findViewById(R.id.txt_login_forget);
        edtUsername.setText("shipper");
        edtPassword.setText("123456");
    }

    private void setAnimation() {
        tilUsername.setTranslationX(800);
        tilPassword.setTranslationX(800);
        edtUsername.setTranslationX(800);
        edtPassword.setTranslationX(800);
        txtForgetPassword.setTranslationX(800);
        btnLogin.setTranslationX(800);

        tilUsername.setAlpha(v);
        tilPassword.setAlpha(v);
        edtUsername.setAlpha(v);
        edtPassword.setAlpha(v);
        txtForgetPassword.setAlpha(v);
        btnLogin.setAlpha(v);

        tilUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        tilPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        edtUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edtPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        txtForgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
    }
}
