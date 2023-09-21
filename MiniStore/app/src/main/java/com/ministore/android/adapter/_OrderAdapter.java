package com.ministore.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ministore.android.R;
import com.ministore.android.activity._OrderDetailActivity;
import com.ministore.android.activity._ProductInfoActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Order;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class _OrderAdapter extends ArrayAdapter<Order>{
    Context context;
    int resource;
    List<Order> data;


    public _OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> data) {
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
        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        TextView tvTotalPrice = convertView.findViewById(R.id.tvTotalPrice);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        ImageView ivUser = convertView.findViewById(R.id.ivIcon);

        Order order = data.get(position);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String day = formatter.format(order.getDate());

        tvUsername.setText(order.getUser().getUsername());
        tvTotalPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(order.getTotalPrice()) + "đ");
        tvDate.setText(day);

        Picasso.get().load(ApiService.USER_IMAGE_URL + order.getUser().getImage())
                .placeholder(R.drawable.ic_add_photo_alternate)
                .error(R.drawable.ic_add_photo_alternate)
                .into(ivUser);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, String.valueOf(order.getOrderId()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), _OrderDetailActivity.class);
                intent.putExtra("order", order);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
