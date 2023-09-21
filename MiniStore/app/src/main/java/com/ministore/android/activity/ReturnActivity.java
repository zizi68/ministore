package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.CartAdapter;
import com.ministore.android.adapter.ReturnAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.CartFragment;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CheckBox cbAllItems;
    private Button btnNext;
    private RecyclerView rcvOrderDetail;
    private List<OrderDetail> list;
    public static List<OrderDetail> originalOrderDetailList = new ArrayList<>();
    private ReturnAdapter returnAdapter;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        order = (Order) getIntent().getSerializableExtra("order");
        originalOrderDetailList.clear();

        setControl();
        loadOrderDetail();
        setEvent();
//        updateInfo();
    }

    private void loadOrderDetail() {
        String auth = MyApplication.getAuthorization();
        ApiService.apiService.getOrderDetailByOrderId(auth, order.getOrderId()).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                originalOrderDetailList.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
        ApiService.apiService.getOrderDetailByOrderId(auth, order.getOrderId()).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {

                list = response.body();
                returnAdapter = new ReturnAdapter(list);
                returnAdapter.setOnItemClickListener(new ReturnAdapter.OnItemActionListener() {
                    @Override
                    public void onButtonMinusClick(OrderDetail orderDetail) {
                        for(int i = 0; i < list.size(); i++){
                            OrderDetail old = list.get(i);
                            if(old.getId() == orderDetail.getId()) {
                                OrderDetail newO = old;
                                newO.setQuantity(old.getQuantity() - 1);
                                list.set(i, newO);
                                returnAdapter.notifyDataSetChanged();
                                break;
                            }
                        }

                    }

                    @Override
                    public void onButtonPlusClick(OrderDetail orderDetail) {
                        for(int i = 0; i < list.size(); i++){
                            OrderDetail o = list.get(i);
                            if(o.getId() == orderDetail.getId()) {
                                o.setQuantity(o.getQuantity() + 1);
                                list.set(i, o);
                                returnAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onEditTextQuantityLostFocus(OrderDetail orderDetail, EditText editText) {
                        // Hide keyboard after edittext lost focus
                        InputMethodManager imm = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        int quantity;
                        try {
                            quantity = Integer.parseInt(editText.getText().toString());
                        }
                        catch (NumberFormatException ex) {
                            quantity = 1;
                        }
                        if (quantity <= 0) {
                            quantity = 1;
                        }
                        for(int i = 0; i < list.size(); i++){
                            OrderDetail o = list.get(i);
                            if(o.getId() == orderDetail.getId()) {
                                o.setQuantity(quantity);
                                list.set(i, o);
                                returnAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onOrderDetailListDataChanged() {
                        updateInfo();
                    }
                });
                rcvOrderDetail.setAdapter(returnAdapter);
                updateInfo();
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable t) {

            }
        });
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setEvent() {
        cbAllItems.setOnClickListener(view1 -> {
            returnAdapter.setCheckedAllItems(cbAllItems.isChecked());
        });

        btnNext.setOnClickListener(view1 -> {
            if (!ReturnAdapter.selectedOderDetails.isEmpty()) {
                Intent intent = new Intent(this, ConfirmReturnActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
                finish();
            }
            else {
                showMessage("Please select items you want to return!");
            }
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    public void updateInfo() {
        cbAllItems.setChecked(returnAdapter.isAllOrderItemSelected());
        cbAllItems.setText(String.format("Select all (%s products)",
                returnAdapter.getItemCount()));
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        cbAllItems = findViewById(R.id.cb_all_items);
        btnNext = findViewById(R.id.btn_next1);

        rcvOrderDetail = findViewById(R.id.rcv_orderDetail);
        rcvOrderDetail.setHasFixedSize(true);
        rcvOrderDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvOrderDetail.setFocusable(false);
        rcvOrderDetail.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvOrderDetail.addItemDecoration(itemDecoration);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

    }
}