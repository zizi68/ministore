package com.ministore.android.fragment;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import com.ministore.android.R;

import com.ministore.android.adapter._UserAdapter;
import com.ministore.android.api.ApiService;

import com.ministore.android.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class _UserListFragment extends Fragment {

    View view;
    RecyclerView rcvUser;
    _UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout._fragment_user_list, container, false);
        setControl();
        loadUserList();
        return view;
    }

    public void loadUserList() {
        ApiService.apiService.getListUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    List<User> user = response.body();
                    Log.d("RRRRR",user.size()+"");
                    if (user == null || user.isEmpty()) return;
                    userAdapter.setData(user);
                }else {
                    Toast.makeText(getContext(),"Fail",Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setControl() {
        rcvUser = view.findViewById(R.id.rcv_user);
        rcvUser.setHasFixedSize(true);
        rcvUser.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcvUser.setFocusable(false);
        rcvUser.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        userAdapter = new _UserAdapter(new ArrayList<>());
        userAdapter.setOnItemClickListener((_UserAdapter.OnItemClickListener) getParentFragment());
        rcvUser.setAdapter(userAdapter);
    }
}
