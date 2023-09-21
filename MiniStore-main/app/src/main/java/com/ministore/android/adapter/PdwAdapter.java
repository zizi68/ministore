package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.model.District;
import com.ministore.android.model.Province;
import com.ministore.android.model.Ward;

import java.util.List;

public class PdwAdapter extends RecyclerView.Adapter<PdwAdapter.PdwViewHolder> {

    private List<Object> objectList;
    private OnItemClickListener onItemClickListener;

    public PdwAdapter(List<Object> objectList) {
        this.objectList = objectList;
        notifyDataSetChanged();
    }

    public void setData(List<Object> categoryList) {
        this.objectList = categoryList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(Object object);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PdwViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdw, parent, false);
        return new PdwViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdwViewHolder holder, int position) {
        Object object = objectList.get(position);
        String name = "";
        if (object instanceof Province) {
            Province province = (Province) object;
            name = String.format("%s", province.getProvinceName());
        }
        else if (object instanceof District) {
            District district = (District) object;
            name = String.format("%s %s", district.getDistrictPrefix(), district.getDistrictName());
        }
        else if (object instanceof Ward) {
            Ward ward = (Ward) object;
            name = String.format("%s %s", ward.getWardPrefix(), ward.getWardName());
        }
        holder.tvName.setText(name);
        holder.view.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(object);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (objectList != null) {
            return objectList.size();
        }
        return 0;
    }

    public class PdwViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvName;

        public PdwViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
