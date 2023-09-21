package com.ministore.android.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

public class SelectedCartAdapter extends RecyclerView.Adapter<SelectedCartAdapter.SelectedItemsViewHolder> {

    private List<Cart> cartList;
    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(Cart cart);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public SelectedCartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectedItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_simplify, parent, false);
        return new SelectedItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemsViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onClick(cart);
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

    public class SelectedItemsViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvDiscount, tvPriceBefore, tvPriceAfter;

        public SelectedItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPriceBefore = itemView.findViewById(R.id.tv_price_before_discount);
            tvPriceAfter = itemView.findViewById(R.id.tv_price_after_discount);
            tvDiscount = itemView.findViewById(R.id.tv_discount);

            tvPriceBefore.setPaintFlags(tvPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
