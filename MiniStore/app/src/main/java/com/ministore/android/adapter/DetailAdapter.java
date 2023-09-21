package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.model.Detail;
import com.ministore.android.model.OrderStatus;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private List<Detail> detailList;

    public DetailAdapter(List<Detail> detailList) {
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Detail detail = detailList.get(position);
        holder.tvHeader.setText(detail.getHeader());
        holder.tvContent.setText(detail.getContent());
    }

    @Override
    public int getItemCount() {
        if (detailList != null) {
            return detailList.size();
        }
        return 0;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHeader, tvContent;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_header);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
