package com.ministore.android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ministore.android.R;
import com.ministore.android.adapter.CategoryAdapter;
import com.ministore.android.adapter._CategoryAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _CategoryListFragment extends Fragment {

    private View view;
    private RecyclerView rcvCategories;
    private _CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout._fragment_category_list, container, false);
        setControl();
        loadCategoryList();
        return view;
    }

    private void setControl() {
        rcvCategories = view.findViewById(R.id.rcv_categories);
        rcvCategories.setHasFixedSize(true);
        rcvCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvCategories.setFocusable(false);
        rcvCategories.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvCategories.addItemDecoration(itemDecoration);

        categoryAdapter = new _CategoryAdapter(new ArrayList<>());
        categoryAdapter.setOnItemClickListener((_CategoryAdapter.OnItemClickListener) getParentFragment());
        rcvCategories.setAdapter(categoryAdapter);
    }

    public void loadCategoryList() {
        ApiService.apiService.getListCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                if (categoryList == null || categoryList.isEmpty()) return;
                categoryAdapter.setData(categoryList);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}