package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Category;

import java.util.ArrayList;
import java.util.List;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.MyViewHolder> {
    private List<Category> list = new ArrayList<>();

    public LoaiAdapter(List<Category> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = list.get(position);
        holder.imgIcon.setImageResource(Integer.parseInt(category.getId()));
        holder.tvNameLoai.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvNameLoai;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvNameLoai = itemView.findViewById(R.id.tvNameLoai);
        }
    }
}
