package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class BestForYouAdapterNotNull extends RecyclerView.Adapter<BestForYouAdapterNotNull.ViewHolder> {
    private List<Hotel> dataHotel;
    private NumberFormat fm = new DecimalFormat("#,###");
    private DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private ImageAutoSliderAdapter imageAutoSliderAdapter;
    private int color;
    private int color1;
    private Consumer<Hotel> consumer;

    public void setConsumer(Consumer<Hotel> consumer) {
        this.consumer = consumer;
    }

    public void setColor(int color, int color1) {
        this.color = color;
        this.color1 = color1;
    }

    public void setDataHouse(List<Hotel> dataHotel) {
        notifyDataSetChanged();
        this.dataHotel = dataHotel;
    }

    private Listernaer listernaer;

    public BestForYouAdapterNotNull() {
    }

    public void setListernaer(Listernaer listernaer) {
        this.listernaer = listernaer;
    }

    public interface Listernaer {
        void onClick(Hotel hotel);
    }

    @NonNull
    @Override
    public BestForYouAdapterNotNull.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestforyou_homefragment_not_null, parent, false);
        return new BestForYouAdapterNotNull.ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull BestForYouAdapterNotNull.ViewHolder holder, int position) {
        Hotel hotel = dataHotel.get(position);
        if (hotel != null) {
            RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.img).error(R.drawable.img);

            imageAutoSliderAdapter = new ImageAutoSliderAdapter(hotel.getImages());
            holder.imageItem.setSliderAdapter(imageAutoSliderAdapter);
            holder.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            holder.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            holder.imageItem.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            holder.imageItem.setIndicatorSelectedColor(Color.WHITE);
            holder.imageItem.setIndicatorUnselectedColor(Color.GRAY);
            holder.imageItem.setScrollTimeInSec(4); //set scroll delay in seconds :
            holder.imageItem.startAutoCycle();
            if (hotel.getTbSao() % 1 == 0) {
                holder.tvStart.setText(hotel.getTbSao() + "");
            } else {
                holder.tvStart.setText(decimalFormat.format(hotel.getTbSao()));
            }
            holder.price.setText(hotel.getGiaDaoDong());
            if (hotel.getTienNghiKS().size() > 4) {
                Glide.with(holder.itemView.getContext()).load(hotel.getTienNghiKS().get(0).getIconImage()).apply(options).into(holder.icon1);
                Glide.with(holder.itemView.getContext()).load(hotel.getTienNghiKS().get(1).getIconImage()).apply(options).into(holder.icon2);
                Glide.with(holder.itemView.getContext()).load(hotel.getTienNghiKS().get(2).getIconImage()).apply(options).into(holder.icon3);
                Glide.with(holder.itemView.getContext()).load(hotel.getTienNghiKS().get(3).getIconImage()).apply(options).into(holder.imageConvenient);

                holder.tvAmountBedRoom.setText(hotel.getTienNghiKS().get(0).getName());
                holder.tvCountBathroom.setText(hotel.getTienNghiKS().get(1).getName());
                holder.tvNameConvenient.setText(hotel.getTienNghiKS().get(2).getName());
                holder.tvPerson.setText(hotel.getTienNghiKS().get(3).getName());


            }
//                viewHolderNearByNull.binding.tvStart.setText(item.getTbSao().toString());
            holder.tvTenPhong.setText(hotel.getName());

            holder.itemView.setOnClickListener(v -> {
                consumer.accept(hotel);
            });
            holder.btnDat.setOnClickListener(v -> {
                consumer.accept(hotel);
            });


        }
    }

    @Override
    public int getItemCount() {
        return dataHotel == null ? 0 : dataHotel.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SliderView imageItem;
        private TextView tvTenPhong;
        private TextView tvStart;
        private TextView tvAmountBedRoom;
        private LinearLayout liTreEm;
        private ImageView imageConvenient;
        private TextView tvNameConvenient;
        private TextView tvCountBathroom;
        private TextView tvPerson;
        private TextView price;
        private Button btnDat;
        private ImageView icon1, icon2, icon3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem = (SliderView) itemView.findViewById(R.id.imageItem);
            tvTenPhong = (TextView) itemView.findViewById(R.id.tv_tenPhong);
            tvStart = (TextView) itemView.findViewById(R.id.tv_start);
            tvAmountBedRoom = (TextView) itemView.findViewById(R.id.tvAmountBedRoom);
            liTreEm = (LinearLayout) itemView.findViewById(R.id.li_treEm);
            imageConvenient = (ImageView) itemView.findViewById(R.id.imageConvenient);
            tvNameConvenient = (TextView) itemView.findViewById(R.id.tvNameConvenient);
            tvCountBathroom = (TextView) itemView.findViewById(R.id.tvCountBathroom);
            tvPerson = (TextView) itemView.findViewById(R.id.tvPerson);
            price = (TextView) itemView.findViewById(R.id.price);
            btnDat = (Button) itemView.findViewById(R.id.btn_dat);
            icon1 = (ImageView) itemView.findViewById(R.id.icon1);
            icon2 = (ImageView) itemView.findViewById(R.id.icon2);
            icon3 = (ImageView) itemView.findViewById(R.id.icon3);

        }
    }
}
