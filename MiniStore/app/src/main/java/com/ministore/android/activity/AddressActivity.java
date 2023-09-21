package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.AddressAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.RemoveAddressBottomSheetFragment;
import com.ministore.android.fragment.RemoveCartBottomSheetFragment;
import com.ministore.android.model.Address;
import com.ministore.android.model.Cart;
import com.ministore.android.model.ResponseObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.OnItemClickListener,
        RemoveAddressBottomSheetFragment.OnClickListener {

    private Toolbar toolbar;
    private View layoutNoAddress;
    private RecyclerView rcvAddress;
    private Button btnAddNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        setControl();
        setEvent();
        loadUserAddress();
    }

    private void loadUserAddress() {
        int userId = MyApplication.getUserId();
        String authorization = MyApplication.getAuthorization();
        ApiService.apiService.getAllAddressByUserId(authorization, userId)
                .enqueue(new Callback<List<Address>>() {
                    @Override
                    public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                        List<Address> addressList = response.body();
                        if (addressList == null || addressList.isEmpty()) {
                            layoutNoAddress.setVisibility(View.VISIBLE);
                        }
                        else {
                            layoutNoAddress.setVisibility(View.GONE);
                        }
                        if (addressList == null) return;
                        AddressAdapter adapter = new AddressAdapter(addressList);
                        adapter.setOnItemClickListener(AddressActivity.this);
                        rcvAddress.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Address>> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        btnAddNewAddress.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditAddressActivity.class);
            startActivity(intent);
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        layoutNoAddress = findViewById(R.id.layout_no_address);
        rcvAddress = findViewById(R.id.rcv_address);
        btnAddNewAddress = findViewById(R.id.btn_add_new_address);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        rcvAddress.setHasFixedSize(true);
        rcvAddress.setLayoutManager(new LinearLayoutManager(this));
        rcvAddress.setFocusable(false);
        rcvAddress.setNestedScrollingEnabled(false);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        rcvAddress.addItemDecoration(itemDecoration);
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserAddress();
    }

    @Override
    public void onClick(Address address) {
        Intent intent = new Intent(this, EditAddressActivity.class);
        intent.putExtra(EditAddressActivity.KEY_ADDRESS, address);
        startActivity(intent);
    }

    @Override
    public void onLongClick(Address address) {
        RemoveAddressBottomSheetFragment fragment = RemoveAddressBottomSheetFragment.newInstance(Arrays.asList(address));
        fragment.setOnClickListener(this);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    @Override
    public void onButtonRemoveClick(List<Address> addressList) {
        for (Address address : addressList) {
            deleteAddress(address.getAddressId());
        }
    }

    private void deleteAddress(int addressId) {
        String authorization = MyApplication.getAuthorization();
        ApiService.apiService.deleteAddressById(authorization, addressId)
                .enqueue(new Callback<ResponseObject>() {
                    @Override
                    public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                        ResponseObject responseObject;
                        if (response.code() == 200) {
                            responseObject = response.body();
                            showMessage(responseObject.getMessage());
                            loadUserAddress();
                        }
                        else {
                            Gson gson = new GsonBuilder().create();
                            try {
                                if (response.errorBody() == null) {
                                    showMessage(response.message());
                                    return;
                                }
                                responseObject = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                                showMessage(responseObject.getMessage());
                            }
                            catch (IOException e) {
                                showMessage(e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObject> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
    }

    @Override
    public void onButtonCancelClick() {

    }
}