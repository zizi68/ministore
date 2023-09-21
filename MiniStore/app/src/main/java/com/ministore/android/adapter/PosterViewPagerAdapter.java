package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Poster;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterViewPagerAdapter extends RecyclerView.Adapter<PosterViewPagerAdapter.PosterViewHolder> {

    private List<Poster> posterList;

    public PosterViewPagerAdapter(List<Poster> posterList) {
        this.posterList = posterList;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        Poster poster = posterList.get(position);
        Picasso.get().load(ApiService.POSTER_IMAGE_URL + poster.getName()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        if (posterList != null) {
            return posterList.size();
        }
        return 0;
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
        }
    }
}
