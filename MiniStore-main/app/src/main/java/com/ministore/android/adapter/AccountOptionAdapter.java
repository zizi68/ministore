package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.model.AccountOption;
import com.ministore.android.model.OrderStatus;

import java.util.List;

public class AccountOptionAdapter extends RecyclerView.Adapter<AccountOptionAdapter.AccountOptionViewHolder> {

    private List<AccountOption> accountOptionList;
    private OnItemClickListener onItemClickListener;

    public AccountOptionAdapter(List<AccountOption> accountOptionList) {
        this.accountOptionList = accountOptionList;
    }

    public interface OnItemClickListener {
        void onClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AccountOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_option, parent, false);
        return new AccountOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountOptionViewHolder holder, int position) {
        AccountOption accountOption = accountOptionList.get(position);
        holder.imgImage.setImageResource(accountOption.getImage());
        holder.txtName.setText(accountOption.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountOptionList.get(holder.getAdapterPosition()).getOnOptionListener().onClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (accountOptionList != null) {
            return accountOptionList.size();
        }
        return 0;
    }

    public class AccountOptionViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imgImage;
        private TextView txtName;

        public AccountOptionViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgImage = itemView.findViewById(R.id.img_image);
            txtName = itemView.findViewById(R.id.tv_name);
        }
    }

}
