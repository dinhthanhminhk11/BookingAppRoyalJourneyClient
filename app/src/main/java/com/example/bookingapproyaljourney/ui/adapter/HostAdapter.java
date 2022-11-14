package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserLogin;

import java.util.List;

public class HostAdapter extends RecyclerView.Adapter<HostAdapter.MyViewHolder> {
    private List<User> listHost;
    private EventClick eventClick;

    public HostAdapter(List<User> listHost, EventClick eventClick) {
        this.listHost = listHost;
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public HostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_host_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HostAdapter.MyViewHolder holder, int position) {
        User userLogin = listHost.get(position);
        holder.nameHost.setText(userLogin.getName());
        Glide.with(holder.itemView.getContext()).load(userLogin.getImage()).transform(new CenterCrop(), new RoundedCorners(20)).into(holder.imgHost);
        holder.itemView.setOnClickListener(v -> eventClick.onClick(userLogin));
    }

    @Override
    public int getItemCount() {
        return listHost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHost;
        private TextView nameHost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHost = itemView.findViewById(R.id.imgHost);
            nameHost = itemView.findViewById(R.id.tvNameHost);
        }
    }
    public interface EventClick {
        void onClick(User user);
    }
}
