package com.ministore.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ministore.android.R;
import com.ministore.android.adapter.AddressAdapter;
import com.ministore.android.model.Address;

import java.io.Serializable;
import java.util.List;

public class RemoveAddressBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String KEY_LIST_ADDRESS = "list_address";
    private List<Address> addressList;

    private View view;
    private RecyclerView rcvSelectedAddresses;
    private AppCompatButton btnRemove, btnCancel;

    public interface OnClickListener {
        void onButtonRemoveClick(List<Address> addressList);
        void onButtonCancelClick();
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private RemoveAddressBottomSheetFragment() {

    }

    public static RemoveAddressBottomSheetFragment newInstance(List<Address> addressList) {
        RemoveAddressBottomSheetFragment fragment = new RemoveAddressBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST_ADDRESS, (Serializable) addressList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            addressList = (List<Address>) bundle.get(KEY_LIST_ADDRESS);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_remove_address, null);
        bottomSheetDialog.setContentView(view);

        setControl();
        setData();
        setEvent();

        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return bottomSheetDialog;
    }

    private void setEvent() {
        btnRemove.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onButtonRemoveClick(addressList);
            }
            this.dismiss();
        });
        btnCancel.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onButtonCancelClick();
            }
            this.dismiss();
        });
    }

    private void setData() {
        if (addressList == null) return;
        AddressAdapter adapter = new AddressAdapter(addressList);
        rcvSelectedAddresses.setAdapter(adapter);
    }

    private void setControl() {
        rcvSelectedAddresses = view.findViewById(R.id.rcv_selected_addresses);
        btnRemove = view.findViewById(R.id.btn_remove);
        btnCancel = view.findViewById(R.id.btn_cancel);

        rcvSelectedAddresses.setHasFixedSize(true);
        rcvSelectedAddresses.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvSelectedAddresses.setFocusable(false);
        rcvSelectedAddresses.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvSelectedAddresses.addItemDecoration(itemDecoration);
    }
}
