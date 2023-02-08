package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemBestforyouHomefragmentBinding;
import com.example.bookingapproyaljourney.model.bill.Bill;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.repository.Repository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class HiredProfileAdapter extends RecyclerView.Adapter<HiredProfileAdapter.ViewHolder> {
    private List<Bill> dataHotel;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Repository repository;
    private Listernaer listernaer;
    private Consumer consumer;
    private int color = Color.BLACK;
    private int colorBlack = Color.BLACK;

    public interface Listernaer {
        void onClickListChinh(HotelBillResponse houseDetailResponse);
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public HiredProfileAdapter() {
        repository = new Repository();
    }

    public void setDataHouse(List<Bill> dataHotel) {
        this.dataHotel = dataHotel;
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
        return new ViewHolder(ItemBestforyouHomefragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull HiredProfileAdapter.ViewHolder viewHolder, int position) {
        Bill bill = dataHotel.get(position);
        repository.getHotelById(bill.getIdHotel(), item -> {
            if (item instanceof HotelById) {
                RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.img).error(R.drawable.img);
                Glide.with(viewHolder.itemView.getContext()).load(item.getDataHotel().getImages().get(0)).apply(options).into(viewHolder.binding.imgItemBestForYou);

                if (item.getDataHotel().getTienNghiKS().size() > 2) {
                    Glide.with(viewHolder.itemView.getContext()).load(item.getDataHotel().getTienNghiKS().get(0).getIconImage()).apply(options).into(viewHolder.binding.icon1);
                    Glide.with(viewHolder.itemView.getContext()).load(item.getDataHotel().getTienNghiKS().get(1).getIconImage()).apply(options).into(viewHolder.binding.icon2);
                    viewHolder.binding.nameIcon1.setText(item.getDataHotel().getTienNghiKS().get(0).getName());
                    viewHolder.binding.tvNameIcon1.setText(item.getDataHotel().getTienNghiKS().get(1).getName());
                }
                viewHolder.binding.tvNameHouseItemBestforyou.setText(item.getDataHotel().getName());
                viewHolder.binding.tvPriceHouseItemBestforyou.setText(item.getDataHotel().getGiaDaoDong());
                viewHolder.binding.tvNameHouseItemBestforyou.setTextColor(colorBlack);
                viewHolder.binding.tvNameIcon1.setTextColor(colorBlack);
                viewHolder.binding.nameIcon1.setTextColor(colorBlack);
                viewHolder.binding.tvPriceHouseItemBestforyou.setTextColor(color);

                viewHolder.itemView.setOnClickListener(v -> {
                    consumer.accept(item.getDataHotel().get_id());
                });
            }
        });
//        Hotel item = dataHotel.get(position);
//        if (item instanceof Hotel) {
//
//        }
//        if (orderListResponse != null) {
//            detailProductRepository.getProductById(orderListResponse.getIdPro(), new CallbackHouseById() {
//                @Override
//                public void success(HouseDetailResponse houseDetailResponse) {
//                    RequestOptions options = new RequestOptions()
//                            .centerCrop()
//                            .placeholder(R.drawable.img)
//                            .error(R.drawable.img);
//                    Glide.with(holder.itemView.getContext()).load(houseDetailResponse.getImages().get(0)).apply(options).into(holder.imgBestForYou);
//                    holder.tvNameHouse.setText(houseDetailResponse.getName());
//                    holder.tvPriceHouse.setText(fm.format(houseDetailResponse.getPrice()) + " VND");
//                    holder.tvCountBedroom.setText(houseDetailResponse.getSleepingPlaces().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest1));
//                    holder.tvCountBathroom.setText(houseDetailResponse.getBathrooms().size() + " " + holder.tvCountBedroom.getContext().getString(R.string.textBest2));
//
//                    holder.tvNameHouse.setTextColor(colorBlack);
//                    holder.tvCountBedroom.setTextColor(color);
//                    holder.tvCountBathroom.setTextColor(color);
//                    holder.itemView.setOnClickListener(v -> {
//                        listernaer.onClickListChinh(houseDetailResponse);
//                    });
//                }
//
//                @Override
//                public void failure(Throwable t) {
//
//                }
//            });
//        }


    }

    @Override
    public int getItemCount() {
        return dataHotel == null ? 0 : dataHotel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBestforyouHomefragmentBinding binding;

        public ViewHolder(ItemBestforyouHomefragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView imgBestForYou;
//        private TextView tvNameHouse;
//        private TextView tvPriceHouse;
//        private TextView tvCountBedroom;
//        private TextView tvCountBathroom;
//
//        public ViewHolder(@NonNull View view) {
//            super(view);
//            imgBestForYou = (ImageView) view.findViewById(R.id.imgItemBestForYou);
//            tvNameHouse = (TextView) view.findViewById(R.id.tvNameHouseItemBestforyou);
//            tvPriceHouse = (TextView) view.findViewById(R.id.tvPriceHouseItemBestforyou);
//            tvCountBedroom = (TextView) view.findViewById(R.id.tvCountBedroomItemBestforyou);
//            tvCountBathroom = (TextView) view.findViewById(R.id.tvCountBathroomItemBestforyou);
//        }
//    }
}
