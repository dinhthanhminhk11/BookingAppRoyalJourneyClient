package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.model.house.House;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class NearFromYouAdapter extends RecyclerView.Adapter<NearFromYouAdapter.ViewHolder> {

    private List<House> dataHouse;
    private Listerner listerner;
    private NumberFormat fm = new DecimalFormat("#,###");

    public NearFromYouAdapter(List<House> dataHouse , Listerner listerner) {
        this.dataHouse = dataHouse;
        this.listerner = listerner;
    }

    public interface Listerner {
        public void onClick(View v, int position);
    }

    public void setData(List<House> dataHouse) {
        this.dataHouse = dataHouse;
    }


    @NonNull
    @Override
    public NearFromYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearfromyou_homefragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearFromYouAdapter.ViewHolder holder, int position) {

        House house = dataHouse.get(position);
        if (house != null) {
            holder.imgNearFromYou.setImageLevel(house.getImage());
            holder.tvAddressNearFromYou.setText(house.getAddress());
            holder.tvDistance.setText(house.getDistance() + " Km");
            holder.tvNameNearFromYou.setText(house.getName());

            holder.itemView.setOnClickListener(v -> {
                listerner.onClick(v, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataHouse == null ? 0 : dataHouse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgNearFromYou;
        private TextView tvNameNearFromYou;
        private TextView tvAddressNearFromYou;
        private TextView tvDistance;

        public ViewHolder(@NonNull View view) {
            super(view);
            imgNearFromYou = (ImageView) view.findViewById(R.id.imgItemNearFromYou);
            tvNameNearFromYou = (TextView) view.findViewById(R.id.tvNameItemNearFromYou);
            tvAddressNearFromYou = (TextView) view.findViewById(R.id.tvAddressItemNearFromYou);
            tvDistance = (TextView) view.findViewById(R.id.tvDistanceItemNearFromYou);
        }
    }


}

