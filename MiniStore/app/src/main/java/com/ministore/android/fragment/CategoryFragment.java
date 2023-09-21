package com.ministore.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.ProductActivity;
import com.ministore.android.adapter.CategoryAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private View view;
    private RecyclerView rcvCategories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        setControl();
        setEvent();
        return view;
    }

    private void setControl() {
        rcvCategories = view.findViewById(R.id.rcv_categories);
        rcvCategories.setHasFixedSize(true);
        rcvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rcvCategories.setFocusable(false);
        rcvCategories.setNestedScrollingEnabled(false);
    }

    private void setEvent() {
        ApiService.apiService.getListCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                if (categoryList == null || categoryList.isEmpty()) return;
                CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList);
                categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Category category) {
                        Intent intent = new Intent(getContext(), ProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(MyApplication.KEY_CATEGORY, category);
                        intent.putExtras(bundle);
                        getContext().startActivity(intent);
                    }
                });
                rcvCategories.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}