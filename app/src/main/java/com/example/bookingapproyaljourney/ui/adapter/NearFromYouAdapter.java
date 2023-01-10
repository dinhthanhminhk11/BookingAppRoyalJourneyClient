package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemNearfromyouHomefragmentBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

public class NearFromYouAdapter extends RecyclerView.Adapter<NearFromYouAdapter.ViewHolder> {

    private List<Hotel> dataHotel;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private Consumer<Hotel> hotelConsumer;

    public void setHotelConsumer(Consumer<Hotel> hotelConsumer) {
        this.hotelConsumer = hotelConsumer;
    }

    public NearFromYouAdapter(List<Hotel> dataHotel) {
        this.dataHotel = dataHotel;
    }

    @NonNull
    @Override
    public NearFromYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNearfromyouHomefragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull NearFromYouAdapter.ViewHolder holder, int position) {

        Hotel item = dataHotel.get(position);
        if (item instanceof Hotel) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemView.getContext()).load(item.getImages().get(0)).apply(options).into(holder.binding.imgItemNearFromYou);

            holder.binding.tvAddressItemNearFromYou.setText(item.getSonha() + ", " + item.getXa() + ", " + item.getHuyen() + ", " + item.getTinh());
            holder.binding.tvDistanceItemNearFromYou.setText(df.format(item.getCalculated()) + " Km");
            holder.binding.tvNameItemNearFromYou.setText(item.getName());

            holder.itemView.setOnClickListener(v->{
                hotelConsumer.accept(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataHotel == null ? 0 : dataHotel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemNearfromyouHomefragmentBinding binding;
        public ViewHolder(ItemNearfromyouHomefragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

