package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.model.house.DataMap;
import com.example.bookingapproyaljourney.model.house.House;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class NearFromYouAdapter extends RecyclerView.Adapter<NearFromYouAdapter.ViewHolder> {

    private List<DataMap> dataHouse;
    private Listerner listerner;
    private NumberFormat fm = new DecimalFormat("#,###");

    public void setDataHouse(List<DataMap> dataHouse) {
        this.dataHouse = dataHouse;
    }

    public NearFromYouAdapter( Listerner listerner ) {
        this.listerner = listerner;
    }

    public interface Listerner {
        public void onClick(House house);
    }

    public void setData(List<DataMap> dataHouse) {
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

        DataMap house = dataHouse.get(position);
        if (house != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemView.getContext()).load(house.getData().getImages().get(0)).apply(options).into(holder.imgNearFromYou);

            holder.tvAddressNearFromYou.setText(house.getData().getNameLocation());
            holder.tvDistance.setText(house.getDistance()+ " Km");
            holder.tvNameNearFromYou.setText(house.getData().getName());

            holder.itemView.setOnClickListener(v -> {
                listerner.onClick(dataHouse.get(position).getData());
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

