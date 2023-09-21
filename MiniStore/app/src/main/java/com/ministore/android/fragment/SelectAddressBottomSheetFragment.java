package com.ministore.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.AddressAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressBottomSheetFragment extends BottomSheetDialogFragment {

    private View view;
    private RecyclerView rcvAddresses;

    private SelectAddressBottomSheetFragment() {

    }

    public static SelectAddressBottomSheetFragment newInstance() {
        SelectAddressBottomSheetFragment fragment = new SelectAddressBottomSheetFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_select_address, null);
        bottomSheetDialog.setContentView(view);

        setControl();
        loadData();
        setEvent();

        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return bottomSheetDialog;
    }

    private void setEvent() {

    }

    private void loadData() {
        String authorization = MyApplication.getAuthorization();
        int userId = MyApplication.getUserId();
        ApiService.apiService.getAllAddressByUserId(authorization, userId)
                .enqueue(new Callback<List<Address>>() {
                    @Override
                    public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                        List<Address> addressList = response.body();
                        if (addressList == null || addressList.isEmpty()) return;
                        AddressAdapter addressAdapter = new AddressAdapter(addressList);
                        addressAdapter.setOnItemClickListener((AddressAdapter.OnItemClickListener) getActivity());
                        rcvAddresses.setAdapter(addressAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Address>> call, Throwable t) {

                    }
                });
    }

    private void setControl() {
        rcvAddresses = view.findViewById(R.id.rcv_address);
        rcvAddresses.setHasFixedSize(true);
        rcvAddresses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvAddresses.setFocusable(false);
        rcvAddresses.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvAddresses.addItemDecoration(itemDecoration);
    }
}
