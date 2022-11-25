package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.Loai;

import java.util.ArrayList;
import java.util.List;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.MyViewHolder> {
    private List<Loai> list = new ArrayList<>();
    private EventClick eventClick;
    public LoaiAdapter(List<Loai> list, EventClick eventClick) {
        this.list = list;
        this.eventClick = eventClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Loai loai = list.get(position);
        final boolean[] check = {true};
        holder.imgIcon.setImageResource(Integer.parseInt(loai.getImg()));
        holder.tvNameLoai.setText(loai.getName());
        holder.itemView.setOnClickListener(v -> {
            if(check[0]){
                holder.bgLoai.setBackgroundResource(R.drawable.bg_loai_click);
                eventClick.onClick(loai);
                holder.imgIcon.setAlpha(1f);
                holder.tvNameLoai.setAlpha(1f);
                check[0] = false;
            } else{
                holder.bgLoai.setBackgroundResource(R.drawable.bg_loai);
                holder.imgIcon.setAlpha(0.6f);
                holder.tvNameLoai.setAlpha(0.6f);
                eventClick.deleteOnClick(loai);
                check[0] = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgIcon;
        private TextView tvNameLoai;
        private LinearLayout bgLoai;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvNameLoai = itemView.findViewById(R.id.tvNameLoai);
            bgLoai = itemView.findViewById(R.id.bgLoai);
        }
    }

    public interface EventClick {
        void onClick(Loai loai);
        void deleteOnClick(Loai loai);
    }
}
