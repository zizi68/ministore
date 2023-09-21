package com.ministore.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter._ImportAdapter;
import com.ministore.android.adapter._OrderAdapter;
import com.ministore.android.adapter._ProductAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Import;
import com.ministore.android.model.Order;
import com.ministore.android.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _ImportListFragment extends Fragment {

    View mView;

    ListView lvImport;
    List<Import> dataList = new ArrayList<>();
    _ImportAdapter importAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout._fragment_import_list, container, false);

        getControl();
        setEvent();
        return mView;
    }

    private void setEvent() {
        loadList();
    }

    public  void loadList() {
        String authorization = MyApplication.getAuthorization();
        if (authorization == null) {
            MyApplication.goToLoginActivity(getActivity());
            return;
        }
        ApiService.apiService.getAllImports(authorization).enqueue(new Callback<List<Import>>() {
            @Override
            public void onResponse(Call<List<Import>> call, Response<List<Import>> response) {
                dataList = response.body();
                importAdapter = new _ImportAdapter(getContext(), R.layout._item_order, dataList);
                lvImport.setAdapter(importAdapter);
            }

            @Override
            public void onFailure(Call<List<Import>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }

    private void getControl() {
        lvImport = mView.findViewById(R.id.lvImport);
    }
}
