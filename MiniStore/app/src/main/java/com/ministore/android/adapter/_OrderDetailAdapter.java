package com.ministore.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity._OrderDetailActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class _OrderDetailAdapter extends ArrayAdapter<OrderDetail> {

    Context context;
    int resource;
    List<OrderDetail> data;


    public _OrderDetailAdapter(@NonNull Context context, int resource, @NonNull List<OrderDetail> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ImageView imgProduct = convertView.findViewById(R.id.img_product_image);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvDiscount = convertView.findViewById(R.id.tv_discount);
        TextView tvPriceBefore = convertView.findViewById(R.id.tv_price_before_discount);
        tvPriceBefore.setPaintFlags(tvPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        TextView tvPriceAfter = convertView.findViewById(R.id.tv_price_after_discount);
        TextView tvTotal = convertView.findViewById(R.id.tv_total);
        TextView tvQuantity = convertView.findViewById(R.id.tv_quantity);

        OrderDetail order = data.get(position);

        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + order.getProduct().getImage()).into(imgProduct);
        tvName.setText(order.getProduct().getName());

        int discount = order.getProduct().getDiscount();
        if (discount > 0) {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText(String.format("-%s%%", order.getProduct().getDiscount()));
            tvPriceBefore.setVisibility(View.VISIBLE);
            tvPriceBefore.setText(String.format("%s đ", MyApplication.formatNumber(order.getProduct().getPrice())));
        } else {
            tvDiscount.setVisibility(View.INVISIBLE);
            tvPriceBefore.setVisibility(View.INVISIBLE);
        }
        double priceAfterDiscount = order.getPrice();
        tvPriceAfter.setText(String.format("%s đ", MyApplication.formatNumber(priceAfterDiscount)));
        double total = priceAfterDiscount * order.getQuantity();
        tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(total)));
        tvQuantity.setText(String.format("x%s" ,order.getQuantity()));

        return convertView;
    }
}
