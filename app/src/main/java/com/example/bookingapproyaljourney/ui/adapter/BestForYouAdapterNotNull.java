package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.House;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BestForYouAdapterNotNull extends RecyclerView.Adapter<BestForYouAdapterNotNull.ViewHolder>{

    private List<House> dataHouse;
    private List<House> data;

    private NumberFormat fm = new DecimalFormat("#,###");
    private final int TYPE_ITEM_DEFAULT = 0;
    private final int TYPE_ITEM_IF_NULL = 1;

    public void setDataHouse(List<House> dataHouse) {
        this.dataHouse = dataHouse;
    }

    public BestForYouAdapterNotNull() {
    }

    @NonNull
    @Override
    public BestForYouAdapterNotNull.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestforyou_homefragment_not_null, parent, false);
        return new BestForYouAdapterNotNull.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestForYouAdapterNotNull.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataHouse == null ? 0 : dataHouse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
