package com.ministore.android.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckOutDetailAdapter extends RecyclerView.Adapter<CheckOutDetailAdapter.CheckOutDetailViewHolder> {

    private List<Cart> selectedCardList;
//    private OnItemActionListener onItemActionListener;
//
//    public interface OnItemActionListener {
//        void onClick(Cart cart);
//    }
//
//    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
//        this.onItemActionListener = onItemActionListener;
//    }

    public CheckOutDetailAdapter(List<Cart> selectedCartList) {
        this.selectedCardList = selectedCartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CheckOutDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_cart, parent, false);
        return new CheckOutDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckOutDetailViewHolder holder, int position) {
        Cart cart = selectedCardList.get(position);
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + cart.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(cart.getProduct().getName());

        int discount = cart.getProduct().getDiscount();
        if (discount > 0) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText(String.format("-%s%%", cart.getProduct().getDiscount()));
            holder.tvPriceBefore.setVisibility(View.VISIBLE);
            holder.tvPriceBefore.setText(String.format("%s đ", MyApplication.formatNumber(cart.getProduct().getPrice())));
        } else {
            holder.tvDiscount.setVisibility(View.INVISIBLE);
            holder.tvPriceBefore.setVisibility(View.INVISIBLE);
        }
        double priceAfterDiscount = cart.getProduct().getPrice() * (100 - discount) / 100;
        holder.tvPriceAfter.setText(String.format("%s đ", MyApplication.formatNumber(priceAfterDiscount)));
        double total = priceAfterDiscount * cart.getQuantity();
        holder.tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(total)));
        holder.tvQuantity.setText(String.format("x%s" ,cart.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (selectedCardList != null) {
            return selectedCardList.size();
        }
        return 0;
    }

    public class CheckOutDetailViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvDiscount, tvPriceBefore, tvPriceAfter, tvQuantity, tvTotal;

        public CheckOutDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPriceBefore = itemView.findViewById(R.id.tv_price_before_discount);
            tvPriceBefore.setPaintFlags(tvPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvPriceAfter = itemView.findViewById(R.id.tv_price_after_discount);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }
}
