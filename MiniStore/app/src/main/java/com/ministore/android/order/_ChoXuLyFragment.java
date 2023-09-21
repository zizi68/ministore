package com.ministore.android.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter._OrderAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment._OrderFragment;
import com.ministore.android.model.Order;
import com.ministore.android.model.ResponseObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _ChoXuLyFragment extends Fragment {

    View mView;

    ListView lvChoXuLy;
    List<Order> dataList = new ArrayList<>();
    _OrderAdapter orderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout._fragment_tab1, container, false);

        getControl();
        setEvent();
        return mView;
    }

    private void setEvent() {
        loadList();
    }

    private  void loadList() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(getActivity());
            return;
        }
        ApiService.apiService.getOrderByStatus(authorization, _OrderFragment.CHO_XU_LY).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                dataList = response.body();
                orderAdapter = new _OrderAdapter(getContext(), R.layout._item_order, dataList);
                lvChoXuLy.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }

    private void getControl() {
        lvChoXuLy = mView.findViewById(R.id.lvChoXuLy);
    }
}
