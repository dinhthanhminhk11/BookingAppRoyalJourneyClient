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
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.order.OrderListResponse2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class HiredProfileAdapter extends RecyclerView.Adapter<HiredProfileAdapter.ViewHolder> {
    private List<OrderListResponse2> dataHouse;
    private NumberFormat fm = new DecimalFormat("#,###");
    private DetailProductRepository detailProductRepository;
    private Listernaer listernaer;
    private int color = Color.BLACK;
    private int colorBlack = Color.BLACK;
    public interface Listernaer {
        void onClickListChinh(HouseDetailResponse houseDetailResponse);
    }

    public HiredProfileAdapter() {
        detailProductRepository = new DetailProductRepository();
    }

    public void setDataHouse(List<OrderListResponse2> dataHouse) {
        this.dataHouse = dataHouse;
    }

    public void setListernaer(Listernaer listernaer) {
        this.listernaer = listernaer;
    }

    public void setColor(int color, int colorBlack) {
        this.color = color;
        this.colorBlack = colorBlack;
    }

    @NonNull
    @Override
    public HiredProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestforyou_hired, parent, false);
        return new HiredProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HiredProfileAdapter.ViewHolder holder, int position) {
        OrderListResponse2 orderListResponse = dataHouse.get(position);
        if (orderListResponse != null) {
            detailProductRepository.getProductById(orderListResponse.getIdPro(), new CallbackHouseById() {
                @Override
                public void success(HouseDetailResponse houseDetailResponse) {
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.soap)
                            .error(R.drawable.soap);
                    Glide.with(holder.itemView.getContext()).load(houseDetailResponse.getImages().get(0)).apply(options).into(holder.imgBestForYou);
                    holder.tvNameHouse.setText(houseDetailResponse.getName());
                    holder.tvPriceHouse.setText(fm.format(houseDetailResponse.getPrice()) + " VND");
                    holder.tvCountBedroom.setText(houseDetailResponse.getSleepingPlaces().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest1));
                    holder.tvCountBathroom.setText(houseDetailResponse.getBathrooms().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest2));

                    holder.tvNameHouse.setTextColor(colorBlack);
                    holder.tvCountBedroom.setTextColor(color);
                    holder.tvCountBathroom.setTextColor(color);
                    holder.itemView.setOnClickListener(v -> {
                        listernaer.onClickListChinh(houseDetailResponse);
                    });
                }

                @Override
                public void failure(Throwable t) {

                }
            });
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
