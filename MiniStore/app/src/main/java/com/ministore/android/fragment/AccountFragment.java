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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.AddressActivity;
import com.ministore.android.activity.ChangePasswordActivity;
import com.ministore.android.activity.OrderActivity;
import com.ministore.android.adapter.AccountOptionAdapter;
import com.ministore.android.adapter.OrderStatusAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.AccountOption;
import com.ministore.android.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private View view;
    private ImageView imgUser;
    private TextView txtName, txtEmail;
    private Button btnEditProfile, btnChangePassword;

    private RecyclerView rcvOrderStatus, rcvAccountOptions;

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
        rcvOrderStatus = view.findViewById(R.id.rcv_order_status);
        rcvAccountOptions = view.findViewById(R.id.rcv_account_options);

        rcvOrderStatus.setHasFixedSize(true);
        rcvOrderStatus.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvOrderStatus.setFocusable(false);
        rcvOrderStatus.setNestedScrollingEnabled(false);
        OrderStatusAdapter orderStatusAdapter = new OrderStatusAdapter();
        orderStatusAdapter.setOnItemClickListener(orderStatus -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            intent.putExtra(OrderActivity.KEY_ORDER_STATUS, orderStatus);
            getContext().startActivity(intent);
        });
        rcvOrderStatus.setAdapter(orderStatusAdapter);

        rcvAccountOptions.setHasFixedSize(true);
        rcvAccountOptions.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rcvAccountOptions.setFocusable(false);
        rcvAccountOptions.setNestedScrollingEnabled(false);
        AccountOptionAdapter accountOptionAdapter = new AccountOptionAdapter(getListAccountOption());
        rcvAccountOptions.setAdapter(accountOptionAdapter);
    }

//    private List<OrderStatus> getListOrderStatus() {
//        List<OrderStatus> orderStatusList = new ArrayList<>();
//        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_add, new Status(1, "Processing")));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_remove, "Requesting cancellation"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_delivery_dining, "Delivering"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_check_box, "Delivered"));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_cancel, "Cancelled"));
//        return orderStatusList;
//    }

    private List<AccountOption> getListAccountOption() {
        List<AccountOption> accountOptions = new ArrayList<>();
        accountOptions.add(new AccountOption(R.drawable.img_cute_book_pencil_cartoon, "Address book", () -> {
            Intent intent = new Intent(getContext(), AddressActivity.class);
            startActivity(intent);
        }));
        return accountOptions;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserInfo();
    }
}