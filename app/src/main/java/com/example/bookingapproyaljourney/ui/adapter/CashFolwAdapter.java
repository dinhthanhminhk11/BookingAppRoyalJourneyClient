package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.databinding.ItemCashFolwBinding;
import com.example.bookingapproyaljourney.response.user.CashFolwResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CashFolwAdapter extends RecyclerView.Adapter<CashFolwAdapter.ViewHolder> {
    private List<CashFolwResponse> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private int colorBlack;
    private int colorXam;

    public void setColor(int colorBlack, int colorXam) {
        this.colorBlack = colorBlack;
        this.colorXam = colorXam;
    }

    public CashFolwAdapter(List<CashFolwResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CashFolwAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCashFolwBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CashFolwAdapter.ViewHolder holder, int position) {
        CashFolwResponse cashFolwResponse = data.get(position);
        if (cashFolwResponse != null) {
            holder.binding.title.setText(cashFolwResponse.getTitle());
            holder.binding.title.setTextColor(colorBlack);
            holder.binding.content.setText(cashFolwResponse.getContent());
            holder.binding.content.setTextColor(colorBlack);
            holder.binding.dateAndTime.setText(cashFolwResponse.getDataTime());
            holder.binding.dateAndTime.setTextColor(colorXam);
            if (cashFolwResponse.isStatus()) {
                holder.binding.price.setTextColor(Color.GREEN);
                holder.binding.price.setText("+" + fm.format(Integer.parseInt(cashFolwResponse.getPrice())));
            } else {
                holder.binding.price.setText("-" + fm.format(Integer.parseInt(cashFolwResponse.getPrice())));
                holder.binding.price.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCashFolwBinding binding;

        public ViewHolder(ItemCashFolwBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
