package com.ministore.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.DetailActivity;
import com.ministore.android.adapter.ProductAdatper;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {

    private View view;
    private RecyclerView rcvProducts;
    private Category category;

    public ProductFragment(Category category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
        setControl();
        setEvent();
        loadProducts();
        return view;
    }

    private void setControl() {
        rcvProducts = view.findViewById(R.id.rcv_products);
        rcvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvProducts.setHasFixedSize(true);
        rcvProducts.setFocusable(false);
        rcvProducts.setNestedScrollingEnabled(false);
    }

    private void setEvent() {

    }

    private void loadProducts() {
        ApiService.apiService.findProducts(1, 10, "id", "ASC", Long.valueOf(category.getCategoryId()))
                .enqueue(new Callback<ProductOutput>() {
                    @Override
                    public void onResponse(Call<ProductOutput> call, Response<ProductOutput> response) {
                        ProductOutput productOutput = response.body();
                        if (productOutput == null) return;
                        List<Product> productList = productOutput.getListResult();
                        if (productList == null || productList.isEmpty()) return;
                        ProductAdatper productAdatper = new ProductAdatper(productList);
                        if (getActivity() instanceof ProductAdatper.OnClickListener) {
                            productAdatper.setOnClickListener((ProductAdatper.OnClickListener) getActivity());
                        }
                        rcvProducts.setAdapter(productAdatper);
                    }

                    @Override
                    public void onFailure(Call<ProductOutput> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        view.requestLayout();
    }
}