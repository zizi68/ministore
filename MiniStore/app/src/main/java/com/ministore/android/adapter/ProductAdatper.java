package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.activity.DetailActivity;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Feedback;
import com.ministore.android.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdatper extends RecyclerView.Adapter<ProductAdatper.ProductViewHolder>{

    private List<Product> productList;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(Product product);
        void onAddToCartClick(ImageView imgProduct, Product product);
    }

    public ProductAdatper(List<Product> productList) {
        this.productList = productList;
    }

    public void addData(List<Product> productList) {
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) return;
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + product.getImage()).into(holder.imgProduct);
        holder.tvName.setText(product.getName());
        int soldQuantity = product.getSoldQuantity();
        if (soldQuantity > 0) {
            holder.tvSoldQuantity.setVisibility(View.VISIBLE);
            holder.tvSoldQuantity.setText(String.format("%s sold", product.getSoldQuantity()));
        }
        else {
            holder.tvSoldQuantity.setVisibility(View.INVISIBLE);
        }
        int discount = product.getDiscount();
        if (discount > 0) {
            holder.tvDiscount.setVisibility(View.VISIBLE);
            holder.tvDiscount.setText(String.format("-%s%%", product.getDiscount()));
        }
        else {
            holder.tvDiscount.setVisibility(View.INVISIBLE);
        }
        double priceAfterDiscount = product.getPrice() * (100 - discount) / 100;
        NumberFormat numberFormat = new DecimalFormat("###,###");
        holder.tvPrice.setText(String.format("%s đ", numberFormat.format(priceAfterDiscount)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(product);
                }
            }
        });
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onAddToCartClick(holder.imgProduct, product);
                }
            }
        });

        ApiService.apiService.getFeedbacksByProductId(product.getProductId()).enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                List<Feedback> feedbackList = response.body();
                if (feedbackList == null || feedbackList.isEmpty()) {
                    holder.rtbRate.setRating(0);
                    return;
                }
                int sum = 0;
                for(Feedback f : feedbackList){
                    sum += f.getVote();
                }

                float rate = (float)sum/feedbackList.size();
                holder.rtbRate.setRating(rate);
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                Toast.makeText(null, t.getMessage() + "!!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getPosition(int productId) {
        if (productList == null) return -1;
        for (int i = 0; i < productList.size(); ++i) {
            if (productList.get(i).getProductId() == productId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvSoldQuantity, tvDiscount, tvPrice;
        private AppCompatImageButton btnAddToCart;
        private RatingBar rtbRate;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            imgProduct = itemView.findViewById(R.id.img_image);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSoldQuantity = itemView.findViewById(R.id.tv_sold_quantity);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
            rtbRate = itemView.findViewById(R.id.rtb_rate);
        }
    }
}
