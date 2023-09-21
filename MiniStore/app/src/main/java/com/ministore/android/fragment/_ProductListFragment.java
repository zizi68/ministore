package com.ministore.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.adapter._ProductAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _ProductListFragment extends Fragment {

    View view;
    RecyclerView rcvProducts;
    _ProductAdapter productAdapter;
    List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout._fragment_product_list, container, false);
        setControl();
        loadProductList();
        return view;
    }

    public void loadProductList() {
        ApiService.apiService.getListProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                if (productList == null || productList.isEmpty()) return;
                productAdapter.setData(productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setControl() {
        rcvProducts = view.findViewById(R.id.rcv_productList);
        rcvProducts.setHasFixedSize(true);
        rcvProducts.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvProducts.setFocusable(false);
        rcvProducts.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvProducts.addItemDecoration(itemDecoration);

        productAdapter = new _ProductAdapter(new ArrayList<>());
        productAdapter.setOnItemClickListener((_ProductAdapter.OnItemClickListener) getParentFragment());
        rcvProducts.setAdapter(productAdapter);
    }

    public List<Product> getProductList() {
        return productList;
    }
}
