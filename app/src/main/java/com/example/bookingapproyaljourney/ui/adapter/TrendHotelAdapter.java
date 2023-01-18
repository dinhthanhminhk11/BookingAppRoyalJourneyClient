package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.databinding.ItemHotelTrendBinding;

import java.util.List;
import java.util.function.Consumer;

public class TrendHotelAdapter extends RecyclerView.Adapter<TrendHotelAdapter.ViewHolder> {
    private List<String> data;
    private Consumer consumer;

    public TrendHotelAdapter(List<String> data, Consumer consumer) {
        this.data = data;
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public TrendHotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHotelTrendBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrendHotelAdapter.ViewHolder holder, int position) {
        String item = data.get(position);
        holder.binding.nameCategoryItemCategory.setText(item);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemHotelTrendBinding binding;

        public ViewHolder(ItemHotelTrendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
