package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.databinding.ItemRoomHotelBinding;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class RoomHotelAdapter extends RecyclerView.Adapter<RoomHotelAdapter.ViewHolder> {
    private ArrayList<Room> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private Consumer<Room> consumer;

    public RoomHotelAdapter() {
    }

    public void setConsumer(Consumer<Room> consumer) {
        this.consumer = consumer;
    }

    public void setData(ArrayList<Room> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RoomHotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemRoomHotelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RoomHotelAdapter.ViewHolder holder, int position) {
        Room item = data.get(position);
        if (item instanceof Room) {
            imageAutoSliderAdapter = new ImageAutoSliderAdapter(item.getImages());
            holder.binding.imageItem.setSliderAdapter(imageAutoSliderAdapter);
            holder.binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            holder.binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            holder.binding.imageItem.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            holder.binding.imageItem.setIndicatorSelectedColor(Color.WHITE);
            holder.binding.imageItem.setIndicatorUnselectedColor(Color.GRAY);
            holder.binding.imageItem.setScrollTimeInSec(4); //set scroll delay in seconds :
            holder.binding.imageItem.startAutoCycle();

            holder.binding.tvTenPhong.setText(item.getName());
            holder.binding.tvAmountBedRoom.setText(item.getMaxNguoiLon() + " nguời lớn, " + item.getMaxTreEm() + " trẻ em");
            holder.binding.tvArea.setText(item.getDienTich() + " m²");
            holder.binding.tvCountBathroom.setText(item.getBedroom().get(0).getName());
            holder.binding.price.setText(fm.format(item.getPrice()) + "");
            holder.itemView.setOnClickListener(v -> {
                consumer.accept(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemRoomHotelBinding binding;

        public ViewHolder(ItemRoomHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
