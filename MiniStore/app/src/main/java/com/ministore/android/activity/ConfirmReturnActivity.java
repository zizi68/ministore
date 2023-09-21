package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.OrderDetailAdapter;
import com.ministore.android.adapter.ReturnAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.Return;
import com.ministore.android.model.ReturnDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmReturnActivity extends AppCompatActivity {

    public static final String OFF_PAYMENT = "off";

    private Toolbar toolbar;
    private RecyclerView rcvReturnDetail;
    private TextView tvRefundType, tvTotalRefund;
    private EditText edtReason;
    private Button btnConfirm;
    private OrderDetailAdapter orderDetailAdapter;
    private Order order;
    private List<OrderDetail> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_return);

        order = (Order) getIntent().getSerializableExtra("order");
        setControl();
        setEvent();
    }

    public double countTotalRefund(List<OrderDetail> list){
        double sum = 0;
        for(OrderDetail o : list){
            sum += o.getQuantity() * o.getPrice();
        }
        return sum;
    }

    private void setEvent() {
        if(order.getPaymentType().equals(OFF_PAYMENT))
            tvRefundType.setText("Cash");
        else
            tvRefundType.setText("MoMo");

//        Toast.makeText(this, ReturnAdapter.selectedOderDetails.get(0).getId() + "", Toast.LENGTH_SHORT).show();
        list = ReturnAdapter.selectedOderDetails;
        orderDetailAdapter = new OrderDetailAdapter(list);
        rcvReturnDetail.setAdapter(orderDetailAdapter);
        rcvReturnDetail.setLayoutManager(new LinearLayoutManager(this));

        tvTotalRefund.setText(String.format("%s đ", MyApplication.formatNumber(countTotalRefund(list))));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = edtReason.getText().toString();
                if(reason.equals("")){
                    Toast.makeText(ConfirmReturnActivity.this, "Reason should not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Return aReturn = new Return();
                aReturn.setReason(reason);
                aReturn.setRefundType(order.getPaymentType());
                aReturn.setTotalPrice(countTotalRefund(list));
                ApiService.apiService.insertReturnByOrderId(MyApplication.getAuthorization(), order.getOrderId(), aReturn).enqueue(new Callback<ResponseObject>() {
                    @Override
                    public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                        ResponseObject responseObject = response.body();
                        Gson gson = new GsonBuilder().create();
                        Return return0 = gson.fromJson(gson.toJson(responseObject.getData()), Return.class);
                        List<ReturnDetail> returnDetails = new ArrayList<>();
                        for(OrderDetail o : list){
                            ReturnDetail r = new ReturnDetail();
                            r.setProduct(o.getProduct());
                            r.setQuantity(o.getQuantity());
                            returnDetails.add(r);
                        }
                        ApiService.apiService.insertReturnDetailByReturnId(MyApplication.getAuthorization(), return0.getId(), returnDetails).enqueue(new Callback<ResponseObject>() {
                            @Override
                            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                                ResponseObject object = response.body();
                                Toast.makeText(ConfirmReturnActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseObject> call, Throwable t) {
                                Toast.makeText(ConfirmReturnActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseObject> call, Throwable t) {
                        Toast.makeText(ConfirmReturnActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        tvRefundType = findViewById(R.id.tvRefundType);
        tvTotalRefund = findViewById(R.id.tv_total_refund);
        edtReason = findViewById(R.id.edt_reason);
        btnConfirm = findViewById(R.id.btn_confirm);
        rcvReturnDetail = findViewById(R.id.rcv_return_detail);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }
}