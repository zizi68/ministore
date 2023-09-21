package com.ministore.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.CheckOutActivity;
import com.ministore.android.activity.LoginActivity;
import com.ministore.android.activity.MainActivity;
import com.ministore.android.adapter.CartAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Product;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.request.CartRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements MyApplication.OnCartActionListener {

    public interface OnDeletedAllCartItemsListener {
        void onDeletedAllCartItems();
    }

    private View view;
    private CheckBox cbAllItems;
    private Button btnDelete;
    private TextView tvTotalPayment;
    private Button btnBuy;
    private RecyclerView rcvCart;

    private OnDeletedAllCartItemsListener onDeletedAllCartItemsListener;

    public void setOnDeletedAllCartItemsListener(OnDeletedAllCartItemsListener onDeletedAllCartItemsListener) {
        this.onDeletedAllCartItemsListener = onDeletedAllCartItemsListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        setControl();
        setEvent();
        return view;
    }

    private void setEvent() {
        cbAllItems.setOnClickListener(view1 -> {
            MyApplication.cartAdapter.setCheckedAllItems(cbAllItems.isChecked());
        });
        btnDelete.setOnClickListener(view1 -> {
            if (MyApplication.cartAdapter.hasSelectedProduct()) {
                showRemoveCartBottomSheet(MyApplication.cartAdapter.getSelectedCartList());
            }
            else {
                showMessage("Please select the product to you want to delete first!");
            }
        });
        btnBuy.setOnClickListener(view1 -> {
            if (MyApplication.cartAdapter.hasSelectedProduct()) {
                Intent intent = new Intent(getContext(), CheckOutActivity.class);
                startActivity(intent);
            }
            else {
                showMessage("Please select items you want to buy first!");
            }
        });
        MyApplication.cartAdapter.setOnItemClickListener(new CartAdapter.OnItemActionListener() {
            @Override
            public void onClick(Cart cart) {

            }

            @Override
            public void onLongClick(Cart cart) {
                showRemoveCartBottomSheet(Arrays.asList(cart));
            }

            @Override
            public void onCheckBoxCheckedChanged(Cart cart) {
                updateInfo();
            }

            @Override
            public void onImageProductClick(Cart cart) {
                MyApplication.viewDetail(getContext(), cart.getProduct());
            }

            @Override
            public void onButtonMinusClick(Cart cart) {
                if (cart.getQuantity() <= 1) {
                    showRemoveCartBottomSheet(Arrays.asList(cart));
                }
                else {
                    MyApplication.addToCart(cart.getProduct(), -1, CartFragment.this);
                }
            }

            @Override
            public void onButtonPlusClick(Cart cart) {
                MyApplication.addToCart(cart.getProduct(), 1, CartFragment.this);
            }

            @Override
            public void onEditTextQuantityLostFocus(Cart cart, EditText editText) {
                // Hide keyboard after edittext lost focus
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                int quantity;
                try {
                    quantity = Integer.parseInt(editText.getText().toString());
                }
                catch (NumberFormatException ex) {
                    quantity = 1;
                }
                if (quantity <= 0) {
                    quantity = 1;
                }
                MyApplication.editCart(cart.getProduct(), quantity, CartFragment.this);
            }

            @Override
            public void onCartListDataChanged() {
                updateInfo();
            }
        });
        rcvCart.setAdapter(MyApplication.cartAdapter);
        updateInfo();
    }

    private void updateInfo() {
        cbAllItems.setChecked(MyApplication.cartAdapter.isAllCartItemSelected());
        cbAllItems.setText(String.format("Select all (%s products, %s selected)",
                MyApplication.cartAdapter.getItemCount(), MyApplication.cartAdapter.getSelectedProductCount()));
        if (MyApplication.cartAdapter.hasSelectedProduct()) {
            btnDelete.setBackgroundTintList(btnBuy.getResources().getColorStateList(R.color.bg_btn_red));
            btnBuy.setBackgroundTintList(btnBuy.getResources().getColorStateList(R.color.bg_btn_red));
        }
        else {
            btnDelete.setBackgroundTintList(btnBuy.getResources().getColorStateList(R.color.bg_btn_gray));
            btnBuy.setBackgroundTintList(btnBuy.getResources().getColorStateList(R.color.bg_btn_gray));
        }

        tvTotalPayment.setText(MyApplication.formatNumber(MyApplication.cartAdapter.getSelectedCartItemTotalPayment()) + " đ");
        btnBuy.setText(String.format("Buy (%s)", MyApplication.cartAdapter.getSelectedCartItemCount()));
    }

    private void showRemoveCartBottomSheet(List<Cart> cartList) {
        RemoveCartBottomSheetFragment fragment = RemoveCartBottomSheetFragment.newInstance(cartList);
        fragment.setOnClickListener(new RemoveCartBottomSheetFragment.onClickListener() {
            @Override
            public void onButtonRemoveClick() {
                for (Cart cart: cartList) {
                    MyApplication.editCart(cart.getProduct(), 0, CartFragment.this);
                }
            }

            @Override
            public void onButtonCancelClick() {

            }
        });
        fragment.show(getChildFragmentManager(), fragment.getTag());
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setControl() {
        cbAllItems = view.findViewById(R.id.cb_all_items);
        btnDelete = view.findViewById(R.id.btn_delete);
        tvTotalPayment = view.findViewById(R.id.tv_total_payment);
        btnBuy = view.findViewById(R.id.btn_buy);

        rcvCart = view.findViewById(R.id.rcv_cart);
        rcvCart.setHasFixedSize(true);
        rcvCart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvCart.setFocusable(false);
        rcvCart.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvCart.addItemDecoration(itemDecoration);
    }

    @Override
    public void onAuthenticationFailed() {
        MyApplication.goToLoginActivity(getActivity());
    }

    @Override
    public void onCartShowMessage(String message) {
        showMessage(message);
        if (MyApplication.cartAdapter.getItemCount() <= 0) {
            if (onDeletedAllCartItemsListener != null) {
                onDeletedAllCartItemsListener.onDeletedAllCartItems();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateInfo();
    }
}