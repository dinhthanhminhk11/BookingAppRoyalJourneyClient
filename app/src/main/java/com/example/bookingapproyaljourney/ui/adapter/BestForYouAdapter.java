package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.House;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BestForYouAdapter extends RecyclerView.Adapter<BestForYouAdapter.ViewHolder> {

    private List<House> dataHouse;
    private List<House> data;
    Listernaer mListerner;
    private NumberFormat fm = new DecimalFormat("#,###");
    private int color = Color.BLACK;
    private int colorBlack = Color.BLACK;

    public void setColor(int color, int colorBlack) {
        this.color = color;
        this.colorBlack = colorBlack;
    }

    public void setDataHouse(List<House> dataHouse) {
        this.dataHouse = dataHouse;
        notifyDataSetChanged();
    }

    public interface Listernaer {
        public void onClickListChinh(House house);
    }

    public BestForYouAdapter(Listernaer listernaer) {
        this.mListerner = listernaer;
    }


    @NonNull
    @Override
    public BestForYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestforyou_homefragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestForYouAdapter.ViewHolder holder, int position) {
        House house = dataHouse.get(position);
        if (house != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemView.getContext()).load(house.getImages().get(0)).apply(options).into(holder.imgBestForYou);

            holder.tvNameHouse.setText(house.getName());
            holder.tvPriceHouse.setText(fm.format(house.getPrice()) + " VND");
            holder.tvCountBedroom.setText(house.getSleepingPlaces().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest1));
            holder.tvCountBathroom.setText(house.getBathrooms().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest2));

            holder.itemView.setOnClickListener(v -> {
                mListerner.onClickListChinh(house);
            });

            holder.tvNameHouse.setTextColor(colorBlack);
            holder.tvCountBedroom.setTextColor(color);
            holder.tvCountBathroom.setTextColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return dataHouse == null ? 0 : dataHouse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBestForYou;
        private TextView tvNameHouse;
        private TextView tvPriceHouse;
        private TextView tvCountBedroom;
        private TextView tvCountBathroom;

        public ViewHolder(@NonNull View view) {
            super(view);
            imgBestForYou = (ImageView) view.findViewById(R.id.imgItemBestForYou);
            tvNameHouse = (TextView) view.findViewById(R.id.tvNameHouseItemBestforyou);
            tvPriceHouse = (TextView) view.findViewById(R.id.tvPriceHouseItemBestforyou);
            tvCountBedroom = (TextView) view.findViewById(R.id.tvCountBedroomItemBestforyou);
            tvCountBathroom = (TextView) view.findViewById(R.id.tvCountBathroomItemBestforyou);
        }
    }
}

