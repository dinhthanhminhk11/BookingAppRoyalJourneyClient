package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Paint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.librarycireleimage.CircleImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<FeedBack> feedbackList;

    public FeedbackAdapter(List<FeedBack> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedBack feedBack = feedbackList.get(position);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        String date = format.format(time);
        holder.tvCountFeedback.setVisibility(View.GONE);
        holder.tvName.setText(feedBack.getName());
        Glide.with(holder.itemView.getContext()).load(feedBack.getImgUser()).placeholder(R.drawable.img_contact).into(holder.imgFeedback);
        try {
            Date b = format.parse(date);
            String txtTime =
                    (String) DateUtils.getRelativeTimeSpanString(
                            Long.parseLong(feedBack.getTime()), b.getTime(), 60000,
                            ((int) DateUtils.MINUTE_IN_MILLIS));
            holder.tvDate.setText(txtTime);
            holder.tvContent.setText(feedBack.getTextUser());
            holder.tvCountFeedback.setText("Hiển thị tất cả " + feedbackList.size() + " đánh giá");
            holder.tvCountFeedback.setPaintFlags(holder.tvCountFeedback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (position == feedbackList.size()-1 && position!=0) {
                holder.tvCountFeedback.setVisibility(View.VISIBLE);
                holder.imgFeedback.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.tvDate.setVisibility(View.GONE);
                holder.tvContent.setVisibility(View.GONE);
        }
        if (position == 4) {
            if (feedbackList.size() - 5 == 0) {
                holder.tvCountFeedback.setVisibility(View.GONE);
            } else {

                holder.tvCountFeedback.setVisibility(View.VISIBLE);
                holder.imgFeedback.setVisibility(View.GONE);
                holder.tvName.setVisibility(View.GONE);
                holder.tvDate.setVisibility(View.GONE);
                holder.tvContent.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgFeedback;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvContent;
        private TextView tvCountFeedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFeedback = itemView.findViewById(R.id.imgFeedback);
            tvName = itemView.findViewById(R.id.tvNameFeedback);
            tvDate = itemView.findViewById(R.id.tvDateFeedBack);
            tvContent = itemView.findViewById(R.id.tvContentFeedback);
            tvCountFeedback = itemView.findViewById(R.id.tvCountFeedback);

        }
    }
}
