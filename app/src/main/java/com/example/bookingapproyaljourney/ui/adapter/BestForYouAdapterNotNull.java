package com.example.bookingapproyaljourney.ui.adapter;

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
import com.example.bookingapproyaljourney.model.house.House;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BestForYouAdapterNotNull extends RecyclerView.Adapter<BestForYouAdapterNotNull.ViewHolder> {
    private List<House> dataHouse;
    private NumberFormat fm = new DecimalFormat("#,###");
    private ImageAutoSliderAdapter imageAutoSliderAdapter;

    public void setDataHouse(List<House> dataHouse) {
        notifyDataSetChanged();
        this.dataHouse = dataHouse;
    }

    private Listernaer listernaer;

    public BestForYouAdapterNotNull(Listernaer listernaer) {
        this.listernaer = listernaer;
    }

    public interface Listernaer {
        void onClick(House house);
    }

    @NonNull
    @Override
    public BestForYouAdapterNotNull.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bestforyou_homefragment_not_null, parent, false);
        return new BestForYouAdapterNotNull.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestForYouAdapterNotNull.ViewHolder holder, int position) {
        House house = dataHouse.get(position);
        if (house != null) {
            imageAutoSliderAdapter = new ImageAutoSliderAdapter(house.getImages());
            holder.imageItem.setSliderAdapter(imageAutoSliderAdapter);
            holder.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            holder.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            holder.imageItem.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            holder.imageItem.setIndicatorSelectedColor(Color.WHITE);
            holder.imageItem.setIndicatorUnselectedColor(Color.GRAY);
            holder.imageItem.setScrollTimeInSec(4); //set scroll delay in seconds :
            holder.imageItem.startAutoCycle();

            NumberFormat format = new DecimalFormat("#,###");
            holder.price.setText(format.format(house.getPrice()) + " VNĐ");
            holder.tvTenPhong.setText(house.getName());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemView.getContext()).
                    load(house.getSupplement().get(0).getIconImage()).
                    apply(options).
                    dontAnimate().
                    into(holder.imageConvenient);
            holder.tvNameConvenient.setText(house.getSupplement().get(0).getName());
            holder.tvAmountBedRoom.setText(house.getBathrooms().size() + " " + holder.tvCountBathroom.getContext().getString(R.string.textBest1));
            holder.tvCountBathroom.setText(house.getBathrooms().size() + " " + holder.tvCountBathroom.getContext().getString(R.string.textBest2));
            holder.tvPerson.setText(house.getLimitPerson() + " " + holder.tvCountBathroom.getContext().getString(R.string.textBest3));
            holder.tvStart.setText(house.getSao() + "");
            holder.itemView.setOnClickListener(v -> {
                listernaer.onClick(house);
            });
            holder.btnDat.setOnClickListener(v -> {
                listernaer.onClick(house);
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataHouse == null ? 0 : dataHouse.size();
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
        }
    }
}
