package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
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

    public NotificationAdapter(List<Notification> data, Callback callback) {
        this.data = data;
        this.callback = callback;
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
                    .placeholder(R.drawable.soap)
                    .error(R.drawable.soap);
            Glide.with(holder.itemView.getContext()).load(notification.getImageHoust()).apply(options).into(holder.binding.image);

            holder.binding.content.setText(notification.getContent());
            holder.binding.dateAndTime.setText(notification.getDate() + " " + notification.getTime());
            holder.binding.title.setText(notification.getTitle());
            holder.itemView.setOnClickListener(v -> {
                callback.onClick(notification);
            });

            if (notification.isSeem()) {
                holder.itemView.setBackgroundResource(R.drawable.background_not_seem);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.background_seem);
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
