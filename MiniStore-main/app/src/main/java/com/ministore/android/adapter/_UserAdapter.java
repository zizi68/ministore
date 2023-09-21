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

import com.ministore.android.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class _UserAdapter extends RecyclerView.Adapter<_UserAdapter.XUserViewHolder> {
    private List<User> usertList;
    private _UserAdapter.OnItemClickListener onItemClickListener;

    public _UserAdapter(List<User> userList) {
        this.usertList = userList;
        notifyDataSetChanged();
    }

    public void setData(List<User> userList) {
        this.usertList = userList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(User user);
    }

    public void setOnItemClickListener(_UserAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public _UserAdapter.XUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._item_user, parent, false);
        return new _UserAdapter.XUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XUserViewHolder holder, int position) {
        User user = usertList.get(position);
        Picasso.get().load(ApiService.USER_IMAGE_URL + user.getImage())
                .placeholder(R.drawable.img_default_avatar)
                .error(R.drawable.img_default_avatar)
                .into(holder.imgImage);
        holder.tvName.setText(user.getFirstName() + " " + user.getLastName());
        holder.tvGmail.setText(user.getEmail());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(user);
            }
        });
    }



    @Override
    public int getItemCount() {
        return usertList.size();
    }

    public class XUserViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imgImage;
        private TextView tvName, tvGmail;

        public XUserViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imgImage = itemView.findViewById(R.id.img_UserItem);
            tvName = itemView.findViewById(R.id.tv_nameUser);
            tvGmail = itemView.findViewById(R.id.tv_Gmail);
        }
    }
}
