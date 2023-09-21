package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;
import com.ministore.android.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class _ProductAdapter extends RecyclerView.Adapter<_ProductAdapter.XProductViewHolder> {

    private List<Product> productList;
    private OnItemClickListener onItemClickListener;

    public _ProductAdapter(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public void setData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public XProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._item_product, parent, false);
        return new _ProductAdapter.XProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + product.getImage())
                .placeholder(R.drawable.ic_add_photo_alternate)
                .error(R.drawable.ic_add_photo_alternate)
                .into(holder.imgImage);
        holder.tvName.setText(product.getName());
        holder.tvRemainQuantity.setText(String.valueOf(product.getQuantity() - product.getSoldQuantity()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class XProductViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imgImage;
        private TextView tvName, tvRemainQuantity;

        public XProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgImage = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_nameP);
            tvRemainQuantity = itemView.findViewById(R.id.tv_remain_quantityP);
        }
    }
}
