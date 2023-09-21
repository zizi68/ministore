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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.activity.ReturnActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.ministore.android.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.ReturnViewHolder>{

    private List<OrderDetail> orderDetailList;
    public static List<OrderDetail> selectedOderDetails = new ArrayList<>();
    private OnItemActionListener onItemActionListener;

    public interface OnItemActionListener {

        void onButtonMinusClick(OrderDetail orderDetail);

        void onButtonPlusClick(OrderDetail orderDetail);

        void onEditTextQuantityLostFocus(OrderDetail orderDetail, EditText editText);

        void onOrderDetailListDataChanged();
    }

    public void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public ReturnAdapter(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
        selectedOderDetails.clear();
        notifyDataSetChanged();
        if (onItemActionListener != null) {
            onItemActionListener.onOrderDetailListDataChanged();
        }
    }

    @NonNull
    @Override
    public ReturnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_return, parent, false);
        return new ReturnViewHolder(view);
    }

    public boolean checkQuantity(OrderDetail orderDetail){
        for(OrderDetail o : ReturnActivity.originalOrderDetailList)
            if(Integer.compare(o.getId(),orderDetail.getId()) == 0) {
                if (o.getQuantity() > orderDetail.getQuantity())
                    return true;
                else
                    return false;
            }

        return false;
    }

    public void setCheckedAllItems(boolean isChecked) {
        selectedOderDetails.clear();
        if (isChecked) {
            selectedOderDetails.addAll(orderDetailList);
        }
        notifyDataSetChanged();
    }

    public int getSelectedProductCount() {
        return selectedOderDetails.size();
    }

    public boolean isAllOrderItemSelected() {
//        Log.d("xxx", selectedCartList.size() + " - " + cartList.size());
        return selectedOderDetails.containsAll(orderDetailList);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetailList.get(position);
        if (orderDetail == null) return;
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + orderDetail.getProduct().getImage()).into(holder.imgProduct);
        holder.tvName.setText(orderDetail.getProduct().getName());

        NumberFormat numberFormat = new DecimalFormat("###,###");

        double priceAfterDiscount = orderDetail.getPrice();
        holder.tvPriceAfter.setText(String.format("%s đ", numberFormat.format(priceAfterDiscount)));
        double total = priceAfterDiscount * orderDetail.getQuantity();
        holder.tvTotal.setText(String.format("%s đ", numberFormat.format(total)));
        holder.edtQuantity.setText(String.valueOf(orderDetail.getQuantity()));

        if (checkQuantity(orderDetail)) {
            holder.btnPlus.setEnabled(true);
            holder.btnPlus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_green));
        } else {
            holder.btnPlus.setEnabled(false);
            holder.btnPlus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_gray));
        }

        if (orderDetail.getQuantity() > 1) {
            holder.btnMinus.setEnabled(true);
            holder.btnMinus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_green));
        } else {
            holder.btnMinus.setEnabled(false);
            holder.btnMinus.setBackgroundTintList(holder.btnPlus.getResources().getColorStateList(R.color.bg_btn_gray));
        }

        holder.cbItem.setOnCheckedChangeListener((compoundButton, b) -> {
            if(selectedOderDetails.contains(orderDetail)) {
                selectedOderDetails.remove(orderDetail);
                Toast.makeText(compoundButton.getContext(), "Remove order detail", Toast.LENGTH_SHORT).show();
            }
            if (b) {
                selectedOderDetails.add(orderDetail);
                Toast.makeText(compoundButton.getContext(), "Add order detail", Toast.LENGTH_SHORT).show();
            }
        });
        holder.cbItem.setChecked(selectedOderDetails.contains(orderDetail));

        holder.btnMinus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonMinusClick(orderDetail);
            }
        });
        holder.btnPlus.setOnClickListener(view -> {
            if (onItemActionListener != null) {
                onItemActionListener.onButtonPlusClick(orderDetail);
            }
        });
        holder.edtQuantity.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (onItemActionListener != null) {
                    onItemActionListener.onEditTextQuantityLostFocus(orderDetail, holder.edtQuantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    class ReturnViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private CheckBox cbItem;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPriceBefore, tvPriceAfter, tvTotal;
        private EditText edtQuantity;
        private ImageButton btnMinus, btnPlus;

        public ReturnViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            cbItem = itemView.findViewById(R.id.cb_item);
            imgProduct = itemView.findViewById(R.id.img_product_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPriceAfter = itemView.findViewById(R.id.tv_price_after_discount);
            tvTotal = itemView.findViewById(R.id.tv_total);
            edtQuantity = itemView.findViewById(R.id.edt_quantity);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);

            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
        }
    }
}
