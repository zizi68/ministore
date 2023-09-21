package com.ministore.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Feedback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{

    private List<Feedback> feedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public void setData(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        if (feedback == null) return;
        Picasso.get().load(ApiService.USER_IMAGE_URL + feedback.getUser().getImage()).into(holder.imgUserAvatar);
        holder.tvUser.setText(feedback.getUser().getLastName() + " " + feedback.getUser().getFirstName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvDate.setText(simpleDateFormat.format(feedback.getDate()));
        holder.tvComment.setText(feedback.getComment());
        holder.rtbVote.setRating(feedback.getVote());
    }

    @Override
    public int getItemCount() {
        if (feedbackList != null) {
            return feedbackList.size();
        }
        return 0;
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUserAvatar;
        private TextView tvUser, tvDate, tvComment;
        private RatingBar rtbVote;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUserAvatar = itemView.findViewById(R.id.img_user_avatar);
            tvUser = itemView.findViewById(R.id.tv_user);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvComment = itemView.findViewById(R.id.tv_comment);
            rtbVote = itemView.findViewById(R.id.rtb_vote);
        }
    }
}
