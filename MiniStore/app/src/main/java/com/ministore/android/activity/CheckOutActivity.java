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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.AddressAdapter;
import com.ministore.android.adapter.CheckOutDetailAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.SelectAddressBottomSheetFragment;
import com.ministore.android.model.Address;
import com.ministore.android.model.Cart;
import com.ministore.android.model.District;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.Province;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.Ward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity implements AddressAdapter.OnItemClickListener, MyApplication.OnCartActionListener {

    public static String token = "";
    private Toolbar toolbar;
    private View layoutNoAddress;
    private View itemAddress;
    private TextView tvSpecificAddress, tvAddress;
    private SelectAddressBottomSheetFragment selectAddressBottomSheetFragment;
    private RecyclerView rcvOrderDetail;
    private Button btnViewAddressBook;
    private TextView tvTotalPayment;
    private Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setControl();
        setEvent();
        loadUserAddress();
        loadOrderDetail();
    }

    private void loadOrderDetail() {
        CheckOutDetailAdapter checkOutDetailAdapter = new CheckOutDetailAdapter(MyApplication.cartAdapter.getSelectedCartList());
        rcvOrderDetail.setAdapter(checkOutDetailAdapter);
        tvTotalPayment.setText(MyApplication.formatNumber(MyApplication.cartAdapter.getSelectedCartItemTotalPayment()) + " đ");
        btnOrder.setText(String.format("Order (%s)", MyApplication.cartAdapter.getSelectedCartItemCount()));
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
                            loadInfoAddress(null);
                        } else {
                            Address address = addressList.get(0);
                            loadInfoAddress(address);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Address>> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
    }

    private void loadInfoAddress(Address address) {
        if (address != null) {
            layoutNoAddress.setVisibility(View.GONE);
            Ward ward = address.getWard();
            District district = ward.getDistrict();
            Province province = district.getProvince();
            itemAddress.setVisibility(View.VISIBLE);
            itemAddress.setTag(address);
            tvSpecificAddress.setText(address.getSpecificAddress());
            tvAddress.setText(String.format("%s %s - %s %s - %s",
                    ward.getWardPrefix(), ward.getWardName(),
                    district.getDistrictPrefix(), district.getDistrictName(),
                    province.getProvinceName()));
        }
        else {
            layoutNoAddress.setVisibility(View.VISIBLE);
            itemAddress.setVisibility(View.GONE);
            itemAddress.setTag(null);
        }
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        itemAddress.setOnClickListener(view -> {
            selectAddressBottomSheetFragment = SelectAddressBottomSheetFragment.newInstance();
            selectAddressBottomSheetFragment.show(getSupportFragmentManager(), selectAddressBottomSheetFragment.getTag());
        });
        btnViewAddressBook.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddressActivity.class);
            startActivity(intent);
        });
        btnOrder.setOnClickListener(view -> {
            int userId = MyApplication.getUserId();
            String auth = MyApplication.getAuthorization();
            Address address = (Address) itemAddress.getTag();
            if (address == null) {
                showMessage("Please select delivery address!");
                return;
            }
            Order order = new Order();
//            order.setDate(new Date());
            order.setAddress(MyApplication.fromAddressToString(address));
            order.setTotalPrice((int) MyApplication.cartAdapter.getSelectedCartItemTotalPayment());
            ApiService.apiService.insertOrderByUserId(auth, userId, order).enqueue(new Callback<ResponseObject>() {
                @Override
                public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                    ResponseObject responseObject;
                    if (response.code() == 200) {
                        responseObject = response.body();
                        Gson gson = new GsonBuilder().create();
                        Order order1 = gson.fromJson(gson.toJson(responseObject.getData()), Order.class);

                        List<OrderDetail> orderDetailList = new ArrayList<>();
                        double priceAfterDiscount;
                        for (int i = 0; i < MyApplication.cartAdapter.getSelectedCartList().size(); ++i) {
                            Cart cart = MyApplication.cartAdapter.getSelectedCartAt(i);
                            OrderDetail orderDetail = new OrderDetail();
                            priceAfterDiscount = cart.getProduct().getPrice() * (100 - cart.getProduct().getDiscount()) / 100;
                            orderDetail.setPrice((int) priceAfterDiscount);
                            orderDetail.setProduct(cart.getProduct());
                            orderDetail.setQuantity(cart.getQuantity());
                            orderDetailList.add(orderDetail);
                        }
                        ApiService.apiService.insertOrderDetailByOrderId(auth, order1.getOrderId(), orderDetailList)
                                .enqueue(new Callback<ResponseObject>() {
                                    @Override
                                    public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                        ResponseObject responseObject1;
                                        if (response.code() == 200) {
                                            responseObject1 = response.body();
                                            showMessage(responseObject1.getMessage());
                                            for (Cart cart : MyApplication.cartAdapter.getSelectedCartList()) {
                                                MyApplication.editCart(cart.getProduct(), 0, CheckOutActivity.this);
                                            }
//                                            MyApplication.goToMainActivity(CheckOutActivity.this);
//                                            finishAffinity();
                                            Intent intent = new Intent(CheckOutActivity.this, ChoosePayActivity.class);
                                            intent.putExtra("orderId", order1.getOrderId());
                                            intent.putExtra("totalPrice", order1.getTotalPrice());
                                            startActivity(intent);

                                        }
                                        else {
                                            Gson gson = new GsonBuilder().create();
                                            try {
                                                if (response.errorBody() == null) {
                                                    showMessage(response.message());
                                                    return;
                                                }
                                                responseObject1 = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                                                showMessage(responseObject1.getMessage());
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
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        itemAddress = findViewById(R.id.item_address);
        layoutNoAddress = findViewById(R.id.layout_no_address);
        tvSpecificAddress = findViewById(R.id.tv_specific_address);
        tvAddress = findViewById(R.id.tv_address);
        rcvOrderDetail = findViewById(R.id.rcv_order_detail);
        btnViewAddressBook = findViewById(R.id.btn_view_address_book);
        tvTotalPayment = findViewById(R.id.tv_total_payment);
        btnOrder = findViewById(R.id.btn_order);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        rcvOrderDetail.setHasFixedSize(true);
        rcvOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvOrderDetail.setFocusable(false);
        rcvOrderDetail.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvOrderDetail.addItemDecoration(itemDecoration);
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Address address) {
        loadInfoAddress(address);
        selectAddressBottomSheetFragment.dismiss();
    }

    @Override
    public void onLongClick(Address address) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserAddress();
    }

    @Override
    public void onAuthenticationFailed() {
        MyApplication.goToLoginActivity(CheckOutActivity.this);
    }

    @Override
    public void onCartShowMessage(String message) {
        showMessage(message);
    }
}