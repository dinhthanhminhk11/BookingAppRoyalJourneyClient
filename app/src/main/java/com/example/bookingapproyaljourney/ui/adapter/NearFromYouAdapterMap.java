package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemNearFromYouMapBinding;
import com.example.bookingapproyaljourney.model.house.House;

import java.util.List;

public class NearFromYouAdapterMap extends RecyclerView.Adapter<NearFromYouAdapterMap.ViewHolder> {
    private List<House> data;
    private Callback callback;
    private boolean isClickSpeed = true;

    public NearFromYouAdapterMap(List<House> data, Callback callback) {
        this.data = data;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNearFromYouMapBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        House item = data.get(position);
        if (holder != null) {
            holder.itemNearFromYouMapBinding.name.setText(item.getName());
            holder.itemNearFromYouMapBinding.address.setText(item.getAddress());
            if (item.getStart() == 1) {
                holder.itemNearFromYouMapBinding.imageStar2.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar3.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 2) {
                holder.itemNearFromYouMapBinding.imageStar3.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 3) {
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 4) {
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            }

            holder.itemNearFromYouMapBinding.price.setText("$" + item.getPrice());
            holder.itemNearFromYouMapBinding.direct.setOnClickListener(v -> {
                callback.onDirect(item);
            });
            holder.itemNearFromYouMapBinding.bookmark.setOnClickListener(v -> {
                if(isClickSpeed){
                    holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                    isClickSpeed = false;
                }else {
                    holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                    isClickSpeed = true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNearFromYouMapBinding itemNearFromYouMapBinding;

        public ViewHolder(ItemNearFromYouMapBinding itemNearFromYouMapBinding) {
            super(itemNearFromYouMapBinding.getRoot());
            this.itemNearFromYouMapBinding = itemNearFromYouMapBinding;
        }
    }

    public interface Callback {
        void onClickBookMark(House house);

        void onDirect(House house);
    }
}
