package com.ministore.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.OrderFragment;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.OrderSummaryDTO;
import com.ministore.android.model.Return;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {
        void onClick(Order order);

        void onActionButtonClick(Order order);
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.itemView.setVisibility(View.INVISIBLE);
        Order order = orderList.get(position);
        holder.tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(order.getTotalPrice())));
        holder.tvDate.setText(String.format(MyApplication.formatDate(order.getDate())));
        String auth = MyApplication.getAuthorization();
        if(OrderFragment.STATUS_ID == 9) {
            holder.tvStatus.setVisibility(View.VISIBLE);

            if (order.getStatus().getId() == 8)
                holder.tvStatus.setText("Approved");

            if (order.getStatus().getId() == 7)
                holder.tvStatus.setText("Unapproved");
        }
        ApiService.apiService.getOrderSummaryByOrderId(auth, order.getOrderId())
                .enqueue(new Callback<OrderSummaryDTO>() {
                    @Override
                    public void onResponse(Call<OrderSummaryDTO> call, Response<OrderSummaryDTO> response) {
                        OrderSummaryDTO dto = response.body();
                        if (dto == null) {
                            holder.layoutFirstDetail.setVisibility(View.GONE);
                            holder.tvDetailQuantity.setVisibility(View.GONE);
                            return;
                        }
                        holder.layoutFirstDetail.setVisibility(View.VISIBLE);
                        holder.tvDetailQuantity.setVisibility(View.VISIBLE);
                        OrderDetail firstDetail = dto.getOrderDetail();
                        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + firstDetail.getProduct().getImage()).into(holder.imgProduct);
                        holder.tvName.setText(firstDetail.getProduct().getName());
                        holder.tvPrice.setText(String.format("%s đ", MyApplication.formatNumber(firstDetail.getPrice())));
                        holder.tvQuantity.setText(String.format("x%s", firstDetail.getQuantity()));
                        holder.tvDetailQuantity.setText(String.format("Total %s details", dto.getSize()));

                        holder.itemView.setOnClickListener(view -> {
                            if (onItemActionListener != null) {
                                onItemActionListener.onClick(order);
                            }
                        });

                        holder.btnAction.setOnClickListener(view -> {
                            if (onItemActionListener != null) {
                                onItemActionListener.onActionButtonClick(order);
                            }
                        });
                        if(OrderFragment.STATUS_ID != 9 && order.getStatus().getId() != 3) {
                            MyApplication.setActionButton(holder.btnAction, order.getStatus().getId());
                            holder.btnAction.setOnClickListener(view -> {
                                if (onItemActionListener != null) {
                                    onItemActionListener.onActionButtonClick(order);
                                }
                            });
                        }
                        else
                            holder.btnAction.setVisibility(View.GONE);
                        holder.itemView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<OrderSummaryDTO> call, Throwable t) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (orderList != null) {
            return orderList.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private View itemView, layoutFirstDetail;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice, tvQuantity, tvDetailQuantity, tvTotal, tvDate, tvStatus;
        private Button btnAction;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            layoutFirstDetail = itemView.findViewById(R.id.layout_first_detail);
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvDetailQuantity = itemView.findViewById(R.id.tv_detail_quantity);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvDate = itemView.findViewById(R.id.tv_date);
            btnAction = itemView.findViewById(R.id.btn_action);
        }
    }
}
