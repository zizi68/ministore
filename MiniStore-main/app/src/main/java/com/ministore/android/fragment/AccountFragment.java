package com.ministore.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.ChangePasswordActivity;
import com.ministore.android.adapter.OrderStatusAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private View view;
    private ImageView imgUser;
    private TextView txtName, txtEmail;
    private Button btnEditProfile, btnChangePassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        setControl();
        setEvent();
        loadUserInfo();
        return view;
    }

    private void loadUserInfo() {
        if (MyApplication.checkUserLogged()) {
            int id = MyApplication.getUserId();
            ApiService.apiService.getUserById(id)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            Picasso.get().load(ApiService.USER_IMAGE_URL + user.getImage())
                                    .error(R.drawable.img_default_avatar)
                                    .placeholder(R.drawable.img_default_avatar)
                                    .into(imgUser);
                            txtName.setText(String.format("%s %s", user.getLastName(), user.getFirstName()));
                            txtEmail.setText(user.getEmail());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
            imgUser.setOnClickListener(view -> {
                MyApplication.viewProfile(getContext());
            });
        }
    }

    private void setEvent() {
        btnEditProfile.setOnClickListener(view -> {
            MyApplication.viewProfile(getContext());
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        imgUser = view.findViewById(R.id.img_user_avatar);
        txtName = view.findViewById(R.id.tv_name);
        txtEmail = view.findViewById(R.id.tv_email);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnChangePassword = view.findViewById(R.id.btn_change_password);


    }

//    private List<OrderStatus> getListOrderStatus() {
//        List<OrderStatus> orderStatusList = new ArrayList<>();
//        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_add, new StatusId(1, "Processing")));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_remove, "Requesting cancellation"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_delivery_dining, "Delivering"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_check_box, "Delivered"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_cancel, "Cancelled"));
//        return orderStatusList;
//    }

      @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }
}