package com.example.bookingapproyaljourney.ui.adapter;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.librarycireleimage.CircleImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {
    private List<FeedBack> feedbackList;


    public FeedbackListAdapter(List<FeedBack> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(feedbackList.size()==0){
            return;
        }
        FeedBack feedBack = feedbackList.get(position);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        String date = format.format(time);
        holder.tvName.setText(feedBack.getName());
        Glide.with(holder.itemView.getContext()).load(feedBack.getImgUser()).placeholder(R.drawable.img_contact).into(holder.imgUserFeedback);
        if(feedBack.getSao()==1) holder.imgStar1.setImageResource(R.drawable.ic_star_click);
        if(feedBack.getSao()==2) {
            holder.imgStar1.setImageResource(R.drawable.ic_star_click);
            holder.imgStar2.setImageResource(R.drawable.ic_star_click);
        }
        if(feedBack.getSao()==3){
            holder.imgStar1.setImageResource(R.drawable.ic_star_click);
            holder.imgStar2.setImageResource(R.drawable.ic_star_click);
            holder.imgStar3.setImageResource(R.drawable.ic_star_click);
        }
        if(feedBack.getSao()==4){
            holder.imgStar1.setImageResource(R.drawable.ic_star_click);
            holder.imgStar2.setImageResource(R.drawable.ic_star_click);
            holder.imgStar3.setImageResource(R.drawable.ic_star_click);
            holder.imgStar4.setImageResource(R.drawable.ic_star_click);
        }
        if(feedBack.getSao()==5){
            holder.imgStar1.setImageResource(R.drawable.ic_star_click);
            holder.imgStar2.setImageResource(R.drawable.ic_star_click);
            holder.imgStar3.setImageResource(R.drawable.ic_star_click);
            holder.imgStar4.setImageResource(R.drawable.ic_star_click);
            holder.imgStar5.setImageResource(R.drawable.ic_star_click);
        }
        try {
            Date b = format.parse(date);
            String txtTime =
                    (String) DateUtils.getRelativeTimeSpanString(
                            Long.parseLong(feedBack.getTime()), b.getTime(), 60000,
                            ((int) DateUtils.MINUTE_IN_MILLIS));
            holder.tvDate.setText(txtTime);
            holder.tvFeedback.setText(feedBack.getTextUser());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgUserFeedback;
        private TextView tvName;
        private LinearLayout linearLayout;
        private ImageView imgStar1;
        private ImageView imgStar2;
        private ImageView imgStar3;
        private ImageView imgStar4;
        private ImageView imgStar5;
        private TextView tvDate;
        private TextView tvFeedback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUserFeedback = itemView.findViewById(R.id.imgUserFeedback);
            tvName = itemView.findViewById(R.id.tvName);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            imgStar1 = itemView.findViewById(R.id.imgStar1);
            imgStar2 = itemView.findViewById(R.id.imgStar2);
            imgStar3 = itemView.findViewById(R.id.imgStar3);
            imgStar4 = itemView.findViewById(R.id.imgStar4);
            imgStar5 = itemView.findViewById(R.id.imgStar5);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
        }
    }
}
