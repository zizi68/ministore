package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.model.Address;
import com.ministore.android.model.District;
import com.ministore.android.model.Province;
import com.ministore.android.model.Ward;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList;
    private OnItemClickListener onItemClickListener;

    public AddressAdapter(List<Address> addressList) {
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    public void setData(List<Address> categoryList) {
        this.addressList = categoryList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(Address address);
        void onLongClick(Address address);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        Ward ward = address.getWard();
        District district = ward.getDistrict();
        Province province = district.getProvince();
        holder.tvSpecificAddress.setText(address.getSpecificAddress());
        holder.tvAddress.setText(String.format("%s %s - %s %s - %s",
                ward.getWardPrefix(), ward.getWardName(),
                district.getDistrictPrefix(), district.getDistrictName(),
                province.getProvinceName()));
        holder.view.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(address);
            }
        });
        holder.view.setOnLongClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onLongClick(address);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (addressList != null) {
            return addressList.size();
        }
        return 0;
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvSpecificAddress, tvAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvSpecificAddress = itemView.findViewById(R.id.tv_specific_address);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }

}
