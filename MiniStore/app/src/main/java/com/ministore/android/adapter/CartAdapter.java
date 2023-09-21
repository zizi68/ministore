package com.ministore.android.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartList;
    private final List<Cart> selectedCartList;
    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(Cart cart);

        void onLongClick(Cart cart);

        void onCheckBoxCheckedChanged(Cart cart);

        void onImageProductClick(Cart cart);

        void onButtonMinusClick(Cart cart);

        void onButtonPlusClick(Cart cart);

        void onEditTextQuantityLostFocus(Cart cart, EditText editText);

        void onCartListDataChanged();
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public List<Cart> getSelectedCartList() {
        return selectedCartList;
    }

    public Cart getSelectedCartAt(int position) {
        return selectedCartList.get(position);
    }

    public int getSelectedProductCount() {
        return selectedCartList.size();
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
        this.selectedCartList = new ArrayList<>();
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void setData(List<Cart> cartList) {
        this.cartList.clear();
        this.cartList.addAll(cartList);
        List<Cart> list = new ArrayList<>();
        for (int i = 0; i < cartList.size(); ++i) {
            for (int j = 0; j < selectedCartList.size(); ++j) {
                if (cartList.get(i).getCartId() == selectedCartList.get(j).getCartId()) {
                    list.add(cartList.get(i));
                    break;
                }
            }
        }
        selectedCartList.clear();
        selectedCartList.addAll(list);
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void clearData() {
        this.cartList.clear();
        this.selectedCartList.clear();
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public Cart getCartByProductId(int productId) {
        for (int i = 0; i < cartList.size(); ++i) {
            if (cartList.get(i).getProduct().getProductId() == productId) {
                return cartList.get(i);
            }
        }
        return null;
    }

    public int getPosition(int productId) {
        for (int i = 0; i < cartList.size(); ++i) {
            if (cartList.get(i).getProduct().getProductId() == productId) {
                return i;
            }
        }
        return -1;
    }

    public int getCartItemCount() {
        int count = 0;
        for (Cart cart : cartList) {
            count += cart.getQuantity();
        }
        return count;
    }

    public int getSelectedCartItemCount() {
        int count = 0;
        for (Cart cart : selectedCartList) {
            count += cart.getQuantity();
        }
        return count;
    }

    private double getCartItemPayment(Cart cart) {
//        if (!cartList.contains(cart)) {
//            throw new RuntimeException("This cart parameter not belong to this adapter!");
//        }
        int discount = cart.getProduct().getDiscount();
        double priceAfterDiscount = cart.getProduct().getPrice() * (100 - discount) / 100;
        return priceAfterDiscount * cart.getQuantity();
    }

    public double getSelectedCartItemTotalPayment() {
        double total = 0;
        for (Cart cart : selectedCartList) {
            total += getCartItemPayment(cart);
        }
        return total;
    }

    public void addItems(List<Cart> cartList) {
        for (Cart cart : cartList) {
            updateItem(cart);
        }
    }

    public void addItem(Cart cart) {
        cartList.add(cart);
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void updateItem(Cart cart) {
        int position = getPosition(cart.getProduct().getProductId());
        if (position == -1) {
            addItem(cart);
        } else {
            Cart oldCart = cartList.get(position);
            cartList.set(position, cart);
            if (selectedCartList.contains(oldCart)) {
                selectedCartList.remove(oldCart);
                selectedCartList.add(cart);
            }
            notifyDataSetChanged();
            if (onItemActionListener != null) {
                onItemActionListener.onCartListDataChanged();
            }
        }
    }

    public void removeItem(Cart cart) {
        int position = getPosition(cart.getProduct().getProductId());
        if (position == -1) return;
        Cart oldCart = cartList.get(position);
        cartList.remove(position);
        if (selectedCartList.contains(oldCart)) {
            selectedCartList.remove(oldCart);
        }
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onCartListDataChanged();
        }
    }

    public void setCheckedAllItems(boolean isChecked) {
        selectedCartList.clear();
        if (isChecked) {
            selectedCartList.addAll(cartList);
        }
        notifyDataSetChanged();
    }

    public boolean isAllCartItemSelected() {
//        Log.d("xxx", selectedCartList.size() + " - " + cartList.size());
        return selectedCartList.containsAll(cartList);
    }

    public boolean hasSelectedProduct() {
        return selectedCartList.size() > 0;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        if (cart == null) return;
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + cart.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct().getName());

        NumberFormat numberFormat = new DecimalFormat("###,###");
        int discount = cart.getProduct().getDiscount();
        if (discount > 0) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText(String.format("-%s%%", cart.getProduct().getDiscount()));
            holder.tvPriceBefore.setVisibility(View.VISIBLE);
            holder.tvPriceBefore.setText(String.format("%s đ", numberFormat.format(cart.getProduct().getPrice())));
        } else {
            holder.tvDiscount.setVisibility(View.INVISIBLE);
            holder.tvPriceBefore.setVisibility(View.INVISIBLE);
        }
        double priceAfterDiscount = cart.getProduct().getPrice() * (100 - discount) / 100;
        holder.tvPriceAfter.setText(String.format("%s đ", numberFormat.format(priceAfterDiscount)));
        double total = priceAfterDiscount * cart.getQuantity();
        holder.tvTotal.setText(String.format("%s đ", numberFormat.format(total)));
        holder.edtQuantity.setText(String.valueOf(cart.getQuantity()));

        if (cart.getQuantity() < cart.getProduct().getQuantity()) {
            holder.btnPlus.setEnabled(true);
            holder.btnPlus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_green));
        } else {
            holder.btnPlus.setEnabled(false);
            holder.btnPlus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_gray));
        }

        holder.cbItem.setOnCheckedChangeListener((compoundButton, b) -> {
            selectedCartList.remove(cart);
            if (b) {
                selectedCartList.add(cart);
            }
            if (onItemActionListener != null) {
                onItemActionListener.onCheckBoxCheckedChanged(cart);
            }
        });
        holder.cbItem.setChecked(selectedCartList.contains(cart));

        holder.itemView.setOnClickListener(view -> {
            view.requestFocus();
            if (onItemActionListener != null) {
                onItemActionListener.onClick(cart);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            view.requestFocus();
            if (onItemActionListener != null) {
                onItemActionListener.onLongClick(cart);
            }
            return false;
        });
        holder.imgProduct.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onImageProductClick(cart);
            }
        });
        holder.btnMinus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonMinusClick(cart);
            }
        });
        holder.btnPlus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonPlusClick(cart);
            }
        });
        holder.edtQuantity.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (onItemActionListener != null) {
                    onItemActionListener.onEditTextQuantityLostFocus(cart, holder.edtQuantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private CheckBox cbItem;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvDiscount, tvPriceBefore, tvPriceAfter, tvTotal;
        private EditText edtQuantity;
        private ImageButton btnMinus, btnPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            cbItem = itemView.findViewById(R.id.cb_item);
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPriceBefore = itemView.findViewById(R.id.tv_price_before_discount);
            tvPriceAfter = itemView.findViewById(R.id.tv_price_after_discount);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            tvTotal = itemView.findViewById(R.id.tv_total);
            edtQuantity = itemView.findViewById(R.id.edt_quantity);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);

            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
            tvPriceBefore.setPaintFlags(tvPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
