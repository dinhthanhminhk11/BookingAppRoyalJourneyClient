package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.user.User;

import java.util.List;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.MyViewHolder> {
    private List<User> listHost;
    private EventClick eventClick;

    private int color = Color.BLACK;
    private int color2;
    private int background;

    public HostAdapter() {
    }

    public void setColor(int color, int background,int color2) {
        this.color = color;
        this.background = background;
        this.color2 = color2;
    }

    public void setListHost(List<User> listHost) {
        this.listHost = listHost;
    }

    public void setEventClick(EventClick eventClick) {
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public HostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_host_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HostAdapter.MyViewHolder holder, int position) {
        User userLogin = listHost.get(position);

        holder.nameHost.setTextColor(color);
        holder.contentBackground.setBackgroundResource(background);
        holder.contentText.setTextColor(color2);

        holder.nameHost.setText(userLogin.getName());
        Glide.with(holder.itemView.getContext()).load(userLogin.getImage()).transform(new CenterCrop(), new RoundedCorners(20)).into(holder.imgHost);
        holder.itemView.setOnClickListener(v -> eventClick.onClick(userLogin));
    }

    @Override
    public int getItemCount() {
        return listHost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHost;
        private TextView nameHost;
        private TextView contentText;
        private LinearLayout contentBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHost = itemView.findViewById(R.id.imgHost);
            nameHost = itemView.findViewById(R.id.tvNameHost);
            contentText = itemView.findViewById(R.id.contentText);
            contentBackground = (LinearLayout) itemView.findViewById(R.id.contentBackground);
        }
    }

    public interface EventClick {
        void onClick(User user);
    }
}
