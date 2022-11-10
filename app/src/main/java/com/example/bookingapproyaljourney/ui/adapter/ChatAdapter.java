package com.example.bookingapproyaljourney.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class chatUserViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMessageUser;
        public chatUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageUser = itemView.findViewById(R.id.tvMessageUser);
        }
    }
    public class chatBossViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMessageBoss;
        public chatBossViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageBoss = itemView.findViewById(R.id.tvMessageBoss);
        }
    }
}
