package com.ministore.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.CartActivity;
import com.ministore.android.activity.OrderDetailActivity;
import com.ministore.android.adapter.OrderAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderStatus;
import com.ministore.android.model.ResponseObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment implements OrderAdapter.OnItemActionListener {

    private static final String KEY_ORDER_STATUS = "order_status";
    public static int STATUS_ID;

    private View view;
    private RecyclerView rcvOrders;
    private OrderStatus orderStatus;

    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(OrderStatus orderStatus) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ORDER_STATUS, orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderStatus = (OrderStatus) getArguments().getSerializable(KEY_ORDER_STATUS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);
        setControl();
        setEvent();
        loadOrders();
        return view;
    }

    private void setControl() {
        rcvOrders = view.findViewById(R.id.rcv_orders);
        rcvOrders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvOrders.setHasFixedSize(true);
        rcvOrders.setFocusable(false);
        rcvOrders.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvOrders.addItemDecoration(itemDecoration);
    }

    private void setEvent() {

    }

    private void loadOrders() {
        if (!MyApplication.checkUserLogged()) {
            MyApplication.goToLoginActivity(getActivity());
            return;
        }
        int userId = MyApplication.getUserId();
        String auth = MyApplication.getAuthorization();
        int statusId = orderStatus.getStatus().getId();
        STATUS_ID = orderStatus.getStatus().getId();
//        Toast.makeText(getContext(), STATUS_ID + "", Toast.LENGTH_SHORT).show();
        ApiService.apiService.getOrdersByUserIdAndStatusIdOrderByDateDesc(auth, userId, statusId)
                .enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        List<Order> orderList = response.body();
//                        if (orderList == null || orderList.isEmpty()) return;
                        OrderAdapter orderAdapter = new OrderAdapter(orderList);
                        orderAdapter.setOnItemClickListener(OrderFragment.this);
                        rcvOrders.setAdapter(orderAdapter);
                        rcvOrders.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });

    }

    @Override
    public void onClick(Order order) {
        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
        intent.putExtra(OrderDetailActivity.KEY_ORDER, order);
        startActivity(intent);
    }

    @Override
    public void onActionButtonClick(Order order) {
        MyApplication.actionOrder(getContext(), order, new MyApplication.OnOrderActionListener() {
            @Override
            public void onAuthenticationFailed() {
                MyApplication.goToLoginActivity(getActivity());
            }

            @Override
            public void onActionCompleted(String message) {
//                int check = order.getStatus().getId();
//                if(check == 4 || check == 5) {
//                    Intent intent = new Intent(getContext(), CartActivity.class);
//                    startActivity(intent);
//                }
                loadOrders();
                showMessage(message);
            }
        });
    }

    public void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        rcvOrders.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        view.requestLayout();
        loadOrders();
    }
}