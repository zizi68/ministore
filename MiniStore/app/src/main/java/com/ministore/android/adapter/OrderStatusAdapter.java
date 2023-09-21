package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.model.OrderStatus;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.OrderStatusViewHolder> {

    private OnItemClickListener onItemClickListener;

    public OrderStatusAdapter() {

    }

    public interface OnItemClickListener {
        void onClick(OrderStatus orderStatus);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_status, parent, false);
        return new OrderStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        OrderStatus orderStatus = OrderStatus.getOrderStatusList().get(position);
        holder.imgImage.setImageResource(orderStatus.getImage());
        holder.txtName.setText(orderStatus.getStatus().getDescription());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(orderStatus);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (OrderStatus.getOrderStatusList() != null) {
            return OrderStatus.getOrderStatusList().size();
        }
        return 0;
    }

    public class OrderStatusViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imgImage;
        private TextView txtName;

        public OrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgImage = itemView.findViewById(R.id.img_image);
            txtName = itemView.findViewById(R.id.tv_name);
        }
    }

}
