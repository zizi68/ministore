package com.ministore.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.OrderDetailAdapter;
import com.ministore.android.adapter._OrderDetailAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment._OrderFragment;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.ResponseObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _OrderDetailActivity extends AppCompatActivity {
    int status = 0;
    int accept = 0;
    int deny;

    TextView tvUsername, tvAddress, tvDate, tvTotalPrice;
    Button btnAccept, btnDeny;

    ListView rcvOrderDetail;
    List<OrderDetail> list;
    _OrderDetailAdapter adapter;

    Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_order_detail);

        setControl();
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");

        setEvent();
    }

    private void setEvent() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(this);
            return;
        }
        status = order.getStatusId().getStatusId();
        tvUsername.setText(order.getUser().getUsername());
        tvAddress.setText(order.getAddress());

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String day = formatter.format(order.getDate());
        tvDate.setText(day);


        tvTotalPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(order.getTotalPrice()) + "đ");

        if(status != _OrderFragment.DANG_GIAO ) {
            btnAccept.setVisibility(View.GONE);
            btnDeny.setVisibility(View.GONE);
        }

        ApiService.apiService.getOrderDetailByOrderId(authorization, order.getOrderId()).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                list = response.body();
                adapter = new _OrderDetailAdapter(getApplication(), R.layout.item_selected_cart, list);
                rcvOrderDetail.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });

        if(status == _OrderFragment.DANG_GIAO ) {
            if(status == _OrderFragment.DANG_GIAO) {
                accept = _OrderFragment.DA_GIAO;
                deny = _OrderFragment.DA_HUY;
            }
//            else {
//                accept = _OrderFragment.DA_HUY;
//                deny = _OrderFragment.DANG_GIAO;
//            }
            btnDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDenyDialog();
                }
            });
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openAcceptDialog();
                }
            });
        }

    }

    private void openAcceptDialog() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(this);
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure accept this request?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.changeOrderStatus(authorization, order.getOrderId(), accept).enqueue(new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                if(response.code() == 200) {
                                    Toast.makeText(_OrderDetailActivity.this, "Order update successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                    Toast.makeText(_OrderDetailActivity.this, "Order update failed! Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseObject> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openDenyDialog() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(this);
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure deny this request?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.changeOrderStatus(authorization, order.getOrderId(), deny).enqueue(new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                if(response.code() == 200) {
                                    Toast.makeText(_OrderDetailActivity.this, "Order update successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                    Toast.makeText(_OrderDetailActivity.this, "Order update failed Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseObject> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setControl() {
        tvUsername = findViewById(R.id.tvUsernameO);
        tvAddress = findViewById(R.id.tvAddressO);
        tvDate = findViewById(R.id.tvDateO);
        tvTotalPrice = findViewById(R.id.tvTotalPriceO);
        btnAccept = findViewById(R.id.btnAccept);
        btnDeny = findViewById(R.id.btnDeny);
        rcvOrderDetail = findViewById(R.id.rcvOrderDetail);
    }
}
