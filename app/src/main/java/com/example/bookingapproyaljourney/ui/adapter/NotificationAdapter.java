package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemLayoutNotificationBinding;
import com.example.bookingapproyaljourney.model.noti.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<Notification> data;
    private Callback callback;
    private int color = Color.BLACK;
    private int backgroundNotSeem = R.drawable.background_not_seem;
    private int backgroundSeem = R.drawable.background_seem;

    public NotificationAdapter() {
    }

    public void setData(List<Notification> data) {
        this.data = data;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setColor(int color, int backgroundNotSeem, int backgroundSeem) {
        this.color = color;
        this.backgroundNotSeem = backgroundNotSeem;
        this.backgroundSeem = backgroundSeem;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLayoutNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification notification = data.get(position);
        if (notification != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemView.getContext()).load(notification.getImageHoust()).apply(options).into(holder.binding.image);

            holder.binding.content.setTextColor(color);
            holder.binding.title.setTextColor(color);

            holder.binding.content.setText(notification.getContent());
            holder.binding.dateAndTime.setText(notification.getDate() + " " + notification.getTime());
            holder.binding.title.setText(notification.getTitle());
            holder.itemView.setOnClickListener(v -> {
                callback.onClick(notification);
            });

            if (notification.isSeem()) {
                holder.itemView.setBackgroundResource(backgroundNotSeem);
            } else {
                holder.itemView.setBackgroundResource(backgroundSeem);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutNotificationBinding binding;

        public ViewHolder(ItemLayoutNotificationBinding itemLayoutNotificationBinding) {
            super(itemLayoutNotificationBinding.getRoot());
            this.binding = itemLayoutNotificationBinding;
        }
    }

    public interface Callback {
        void onClick(Notification notification);
    }
}
