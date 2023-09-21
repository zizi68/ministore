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
import com.squareup.picasso.Picasso;

import java.util.List;

public class _CategoryAdapter extends RecyclerView.Adapter<_CategoryAdapter.XCategoryViewHolder> {

    private List<Category> categoryList;
    private OnItemClickListener onItemClickListener;

    public _CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public void setData(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(Category category);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public XCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._item_category, parent, false);
        return new XCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XCategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        Picasso.get().load(ApiService.CATEGORY_IMAGE_URL + category.getImage())
                .placeholder(R.drawable.ic_add_photo_alternate)
                .error(R.drawable.ic_add_photo_alternate)
                .into(holder.imgImage);
        holder.tvName.setText(category.getName());
        holder.tvNote.setText(category.getNote());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public class XCategoryViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imgImage;
        private TextView tvName, tvNote;

        public XCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgImage = itemView.findViewById(R.id.img_category);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNote = itemView.findViewById(R.id.tv_note);
        }
    }

}
