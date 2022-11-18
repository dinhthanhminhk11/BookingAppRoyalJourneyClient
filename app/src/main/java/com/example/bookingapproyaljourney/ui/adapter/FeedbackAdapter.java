package com.example.bookingapproyaljourney.ui.adapter;

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
        if(feedbackList.size()==0){
            return;
        }
        FeedBack feedBack = feedbackList.get(position);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        String date = format.format(time);
        holder.tvNameUser.setText(feedBack.getName());
        Glide.with(holder.itemView.getContext()).load(feedBack.getImgUser()).placeholder(R.drawable.img_contact).into(holder.imgUser);
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
            holder.tvTime.setText(txtTime);
            holder.tvContent.setText(feedBack.getTextUser());
            holder.tvShowFeedback.setText("Hiển thị tất cả " + feedbackList.size() + " đánh giá");
            holder.tvShowFeedback.setPaintFlags(holder.tvShowFeedback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (position == feedbackList.size()-1 && position!=0) {
                holder.tvShowFeedback.setVisibility(View.VISIBLE);
                holder.lnDetail.setVisibility(View.GONE);
                holder.lnSao.setVisibility(View.GONE);
                holder.tvContent.setVisibility(View.GONE);
        }
        if (position == 4) {
            if (feedbackList.size() - 5 == 0) {
                holder.tvShowFeedback.setVisibility(View.VISIBLE);
            } else {
                holder.tvShowFeedback.setVisibility(View.VISIBLE);
                holder.lnDetail.setVisibility(View.GONE);
                holder.lnSao.setVisibility(View.GONE);
                holder.tvContent.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(feedbackList.size(), 5);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lnDetail;
        private CircleImageView imgUser;
        private TextView tvNameUser;
        private TextView tvTime;
        private LinearLayout lnSao;
        private ImageView imgStar1;
        private ImageView imgStar2;
        private ImageView imgStar3;
        private ImageView imgStar4;
        private ImageView imgStar5;
        private TextView tvContent;
        private TextView tvShowFeedback;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lnDetail = itemView.findViewById(R.id.lnDetail);
            imgUser = itemView.findViewById(R.id.imgUser);
            tvNameUser = itemView.findViewById(R.id.tvNameUser);
            tvTime = itemView.findViewById(R.id.tvTime);
            lnSao = itemView.findViewById(R.id.lnSao);
            imgStar1 = itemView.findViewById(R.id.imgStar1);
            imgStar2 = itemView.findViewById(R.id.imgStar2);
            imgStar3 = itemView.findViewById(R.id.imgStar3);
            imgStar4 = itemView.findViewById(R.id.imgStar4);
            imgStar5 = itemView.findViewById(R.id.imgStar5);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvShowFeedback = itemView.findViewById(R.id.tvShowFeedback);

        }
    }
}
