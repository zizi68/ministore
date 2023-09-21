package com.ministore.android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.SignupRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment extends Fragment {

    private View view;
    private EditText edtUsername, edtPassword, edtFirstName, edtLastName, edtEmail, edtPhone;
    private Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_signup, container, false);
        setControl();
        setEvent();
        return view;
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", null);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void setControl() {
        edtUsername = view.findViewById(R.id.edt_signup_username);
        edtPassword = view.findViewById(R.id.edt_signup_password);
        edtFirstName = view.findViewById(R.id.edt_signup_firstname);
        edtLastName = view.findViewById(R.id.edt_signup_lastname);
        edtEmail = view.findViewById(R.id.edt_signup_email);
        edtPhone = view.findViewById(R.id.edt_signup_phone);
        btnSignUp = view.findViewById(R.id.btn_signup);
    }

    private void setEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    showMessage("Hãy điền đầy đủ các thông tin!");
                    return;
                }

                SignupRequest user = new SignupRequest();
                user.setUsername(username);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);

                ApiService.apiService.registerUser(user)
                        .enqueue(new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                ResponseObject responseObject;
                                if (response.code() == 400) {
                                    Gson gson = new GsonBuilder().create();
                                    try {
                                        responseObject = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                                        showMessage(responseObject.getMessage());
                                    }
                                    catch (IOException e) {
                                        // handle failure to read error
                                        showMessage(e.getMessage());
                                    }
                                    return;
                                }
                                if (!response.isSuccessful()) {
                                    showMessage("Đăng ký thất bại!\n" + response.message());
                                    return;
                                }
                                responseObject = response.body();
                                showMessage(responseObject.getMessage());

                                TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
                                tabLayout.getTabAt(0).select();
                                // Điền sẵn thông tin...

                                edtUsername.setText("");
                                edtPassword.setText("");
                                edtFirstName.setText("");
                                edtLastName.setText("");
                                edtEmail.setText("");
                                edtPhone.setText("");
                            }

                            @Override
                            public void onFailure(Call<ResponseObject> call, Throwable t) {
                                showMessage(t.getMessage());
                            }
                        });
            }
        });
    }
}