package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.ReturnDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReturnDetailAdapter extends RecyclerView.Adapter<ReturnDetailAdapter.OrderDetailViewHolder> {

    public interface OnItemClickListener {
        void onClick(OrderDetail orderDetail);
        void onRateButtonClick(OrderDetail orderDetail);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<ReturnDetail> orderDetailList;

    public ReturnDetailAdapter(List<ReturnDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        ReturnDetail orderDetail = orderDetailList.get(position);
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + orderDetail.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(orderDetail.getProduct().getName());
        holder.tvPrice.setVisibility(View.GONE);
        holder.tvTotal.setVisibility(View.GONE);
//        holder.tvPrice.setText(String.format("%s đ", MyApplication.formatNumber(orderDetail.getPrice())));
        holder.tvQuantity.setText(String.format("x%s", orderDetail.getQuantity()));
//        holder.tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(orderDetail.getPrice() * orderDetail.getQuantity())));
    }

    @Override
    public int getItemCount() {
        if (orderDetailList != null) {
            return orderDetailList.size();
        }
        return 0;
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice, tvQuantity, tvTotal;
        private Button btnRate;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvTotal = itemView.findViewById(R.id.tv_total);
            btnRate = itemView.findViewById(R.id.btn_rate);
        }
    }
}
