package com.ministore.android.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.OrderAdapter;
import com.ministore.android.adapter.OrderDetailAdapter;
import com.ministore.android.adapter.OrderStatusViewPagerAdapter;
import com.ministore.android.adapter.ReturnDetailAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.OrderFragment;
import com.ministore.android.model.Address;
import com.ministore.android.model.District;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.OrderStatus;
import com.ministore.android.model.Province;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.Return;
import com.ministore.android.model.ReturnDetail;
import com.ministore.android.model.Ward;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String KEY_ORDER = "order";

    private Toolbar toolbar;
    private TextView tvSpecificAddress;
    private RecyclerView rcvOrderDetail, rcvReturnDetail;
    private TextView tvTotal, tvOrderId, tvDate, tvTotalRefund, tvReturnDate, tvReason, tvPaymentType;
    private Button btnAction, btnReturn;
    private LinearLayout returnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setControl();
        setEvent();
        loadOrder();
        loadOrderDetails();
    }

    private void loadOrder() {
        Order order = (Order) getIntent().getExtras().get(KEY_ORDER);
        tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(order.getTotalPrice())));
        tvOrderId.setText(String.valueOf(order.getOrderId()));
        tvDate.setText(String.format(MyApplication.formatDate(order.getDate())));
        tvPaymentType.setText(order.getPaymentType().equals("off") ? "Thanh toán khi nhận hàng" : "MoMo");
        tvSpecificAddress.setText(order.getAddress());

        int statusId = order.getStatus().getId();
        if(OrderFragment.STATUS_ID == 9){
            returnLayout.setVisibility(View.VISIBLE);
            ApiService.apiService.getReturnByOrderId(MyApplication.getAuthorization(), order.getOrderId()).enqueue(new Callback<Return>() {
                @Override
                public void onResponse(Call<Return> call, Response<Return> response) {
                    Return aReturn = response.body();
                    tvReturnDate.setText(String.format(MyApplication.formatDate(aReturn.getDate())));
                    tvTotalRefund.setText(String.format("%s đ", MyApplication.formatNumber(aReturn.getTotalPrice())));
                    tvReason.setText(aReturn.getReason());

                    ApiService.apiService.getReturnDetailByReturnId(MyApplication.getAuthorization(), aReturn.getId()).enqueue(new Callback<List<ReturnDetail>>() {
                        @Override
                        public void onResponse(Call<List<ReturnDetail>> call, Response<List<ReturnDetail>> response) {
                            List<ReturnDetail> returnDetails = response.body();
                            if (returnDetails == null || returnDetails.isEmpty()) {
                                return;
                            }
                            ReturnDetailAdapter returnDetailAdapter = new ReturnDetailAdapter(returnDetails);
                            rcvReturnDetail.setAdapter(returnDetailAdapter);
                            rcvReturnDetail.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
                        }

                        @Override
                        public void onFailure(Call<List<ReturnDetail>> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<Return> call, Throwable t) {

                }
            });

        }
    }


    private void loadOrderDetails() {
        Order order = (Order) getIntent().getExtras().get(KEY_ORDER);
        String auth = MyApplication.getAuthorization();
        ApiService.apiService.getOrderDetailByOrderId(auth, order.getOrderId())
                .enqueue(new Callback<List<OrderDetail>>() {
                    @Override
                    public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                        List<OrderDetail> orderDetailList = response.body();
                        if (orderDetailList == null || orderDetailList.isEmpty()) {
                            return;
                        }
                        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderDetailList);
                        orderDetailAdapter.setOnItemClickListener(new OrderDetailAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(OrderDetail orderDetail) {
                                MyApplication.viewDetail(getApplicationContext(), orderDetail.getProduct());
                            }

                            @Override
                            public void onRateButtonClick(OrderDetail orderDetail) {
                                Intent intent = new Intent(OrderDetailActivity.this, RateActivity.class);
                                intent.putExtra(RateActivity.KEY_PRODUCT, orderDetail.getProduct());
                                intent.putExtra(RateActivity.KEY_DETAIL, orderDetail);
                                startActivity(intent);
                            }
                        });
                        rcvOrderDetail.setAdapter(orderDetailAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<OrderDetail>> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
        int check = order.getStatus().getId();
        if (OrderFragment.STATUS_ID == 9 || check == 3) {
            btnReturn.setVisibility(View.GONE);
            btnAction.setVisibility(View.GONE);
            return;
        }
        MyApplication.setActionButton(btnAction, check);
        if(check != 4)
            btnReturn.setVisibility(View.GONE);
        else
            btnReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, ReturnActivity.class);
                    intent.putExtra("order", order);
//                    Toast.makeText(OrderDetailActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            });

        btnAction.setOnClickListener(view -> {
            MyApplication.actionOrder(this, order, new MyApplication.OnOrderActionListener() {
                @Override
                public void onAuthenticationFailed() {
                    MyApplication.goToLoginActivity(OrderDetailActivity.this);
                }

                @Override
                public void onActionCompleted(String message) {
                    showMessage(message);
                    finish();
//                    if(check == 4 || check == 5) {
//                        Intent intent = new Intent(OrderDetailActivity.this, CartActivity.class);
//                        startActivity(intent);
//                    }

                }
            });
        });
    }

    public void showMessage(String s) {
        Toast.makeText(OrderDetailActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        returnLayout = findViewById(R.id.layout_return_info);
        tvSpecificAddress = findViewById(R.id.tv_specific_address);
        rcvOrderDetail = findViewById(R.id.rcv_order_detail);
        rcvReturnDetail = findViewById(R.id.rcv_returnDetail);
        tvTotal = findViewById(R.id.tv_total);
        tvPaymentType = findViewById(R.id.tv_payment_type);
        tvTotalRefund = findViewById(R.id.tv_totalRefund);
        tvReason = findViewById(R.id.tv_reason);
        tvReturnDate = findViewById(R.id.tv_returnDate);
        tvOrderId = findViewById(R.id.tv_order_id);
        tvDate = findViewById(R.id.tv_date);
        btnAction = findViewById(R.id.btn_action);
        btnReturn = findViewById(R.id.btn_return);

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
}