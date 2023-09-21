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

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.SelectedCartAdapter;
import com.ministore.android.model.Cart;

import java.io.Serializable;
import java.util.List;

public class RemoveCartBottomSheetFragment extends BottomSheetDialogFragment {

    private static final String KEY_LIST_CART_OBJECT = "list_cart_object";
    private List<Cart> cartList;

    private View view;
    private RecyclerView rcvSelectedItems;
    private AppCompatButton btnRemove, btnCancel;

    public interface onClickListener {
        void onButtonRemoveClick();
        void onButtonCancelClick();
    }

    private onClickListener onClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private RemoveCartBottomSheetFragment() {

    }

    public static RemoveCartBottomSheetFragment newInstance(List<Cart> cartList) {
        RemoveCartBottomSheetFragment fragment = new RemoveCartBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST_CART_OBJECT, (Serializable) cartList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cartList = (List<Cart>) bundle.get(KEY_LIST_CART_OBJECT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_remove_cart, null);
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
                onClickListener.onButtonRemoveClick();
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
        if (cartList == null) return;
        SelectedCartAdapter adapter = new SelectedCartAdapter(cartList);
        adapter.setOnItemClickListener(cart -> {
            MyApplication.viewDetail(getContext(), cart.getProduct());
        });
        rcvSelectedItems.setAdapter(adapter);
    }

    private void setControl() {
        rcvSelectedItems = view.findViewById(R.id.rcv_selected_items);
        btnRemove = view.findViewById(R.id.btn_remove);
        btnCancel = view.findViewById(R.id.btn_cancel);

        rcvSelectedItems.setHasFixedSize(true);
        rcvSelectedItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        rcvSelectedItems.setFocusable(false);
//        rcvSelectedItems.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvSelectedItems.addItemDecoration(itemDecoration);
    }
}
