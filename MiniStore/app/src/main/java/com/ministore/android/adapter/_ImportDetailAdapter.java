package com.ministore.android.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity._NewImportActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.ImportDetail;
import com.ministore.android.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class _ImportDetailAdapter extends ArrayAdapter<ImportDetail> {

    Context context;
    int resource;
    List<ImportDetail> data;


    public _ImportDetailAdapter(@NonNull Context context, int resource, @NonNull List<ImportDetail> data) {
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
        TextView tvPrice = convertView.findViewById(R.id.tv_price);
        TextView tvTotal = convertView.findViewById(R.id.tv_total);
        TextView tvQuantity = convertView.findViewById(R.id.tv_quantity);

        ImageView btnDelete = convertView.findViewById(R.id.btnDelete);

        ImportDetail importDetail = data.get(position);

        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + importDetail.getProduct().getImage()).into(imgProduct);
        tvName.setText(importDetail.getProduct().getName());

        double price = importDetail.getPrice();
        double total = price * importDetail.getQuantity();
        tvPrice.setText(String.format("%s đ", MyApplication.formatNumber(price)));
        tvTotal.setText(String.format("%s đ", MyApplication.formatNumber(total)));
        tvQuantity.setText(String.format("x%s" ,importDetail.getQuantity()));

        if(btnDelete != null)
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    notifyDataSetChanged();
                    _NewImportActivity.totalPrice -= total;
                    _NewImportActivity.hienThi();
                }
            });

        return convertView;
    }
}
