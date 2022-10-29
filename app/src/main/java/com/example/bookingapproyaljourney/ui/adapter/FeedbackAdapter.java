package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Feedback;
import com.example.librarycireleimage.CircleImageView;


import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<Feedback> feedbackList;
    private Context context;

    public FeedbackAdapter(Context context, List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgFeedback.setImageResource(feedbackList.get(position).getImageUser());
        holder.tvName.setText(feedbackList.get(position).getNameUser());
        holder.tvDate.setText(feedbackList.get(position).getDate());
        holder.tvNews.setText(feedbackList.get(position).getContent());
        if (position == 3) {
            if (feedbackList.size() - 4 == 0) {
                holder.btnAmount.setVisibility(View.GONE);
            } else {

                holder.btnAmount.setVisibility(View.VISIBLE);
                holder.imgFeedback.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.tvDate.setVisibility(View.GONE);
                holder.tvNews.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size() < 4 ? feedbackList.size() : 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgFeedback;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvNews;
        private TextView btnAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFeedback = (CircleImageView) itemView.findViewById(R.id.imgFeedback);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvNews = (TextView) itemView.findViewById(R.id.tvNews);
            btnAmount = (TextView) itemView.findViewById(R.id.btnAmount);

        }
    }
}
