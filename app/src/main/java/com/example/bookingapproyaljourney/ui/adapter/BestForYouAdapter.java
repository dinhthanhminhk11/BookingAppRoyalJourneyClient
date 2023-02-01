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
import com.example.bookingapproyaljourney.databinding.ItemBestforyouHomefragmentNotNullBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.libraryautoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.example.libraryautoimageslider.SliderAnimations;
import com.example.libraryautoimageslider.SliderView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class BestForYouAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Hotel> dataHotel;
    private NumberFormat fm = new DecimalFormat("#,###");
    private DecimalFormat decimalFormat = new DecimalFormat("#.#");
    private int color = Color.BLACK;
    private int colorBlue = Color.BLUE;
    private Consumer<Hotel> consumer;
    private int type;
    private ImageAutoSliderAdapter imageAutoSliderAdapter;

    public void setColor(int color, int colorBlue) {
        this.color = color;
        this.colorBlue = colorBlue;
    }

    public void setDataHotel(ArrayList<Hotel> dataHotel) {
        this.dataHotel = dataHotel;
        notifyDataSetChanged();
    }

    public BestForYouAdapter(Consumer<Hotel> consumer) {
        this.consumer = consumer;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(ItemBestforyouHomefragmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new ViewHolderNearByNull(ItemBestforyouHomefragmentNotNullBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type == 0) {
            ViewHolder viewHolder = (ViewHolder) holder;
            Hotel item = dataHotel.get(position);
            if (item instanceof Hotel) {
                RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.img).error(R.drawable.img);
                Glide.with(holder.itemView.getContext()).load(item.getImages().get(0)).apply(options).into(viewHolder.binding.imgItemBestForYou);

                if (item.getTienNghiKS().size() > 2) {
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(0).getIconImage()).apply(options).into(viewHolder.binding.icon1);
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(1).getIconImage()).apply(options).into(viewHolder.binding.icon2);
                    viewHolder.binding.nameIcon1.setText(item.getTienNghiKS().get(0).getName());
                    viewHolder.binding.tvNameIcon1.setText(item.getTienNghiKS().get(1).getName());
                }
                viewHolder.binding.tvNameHouseItemBestforyou.setText(item.getName());
                viewHolder.binding.tvNameHouseItemBestforyou.setTextColor(color);
                viewHolder.binding.tvPriceHouseItemBestforyou.setText(item.getGiaDaoDong());
                viewHolder.binding.tvPriceHouseItemBestforyou.setTextColor(colorBlue);
                viewHolder.binding.tvNameIcon1.setTextColor(color);
                viewHolder.binding.nameIcon1.setTextColor(color);
                viewHolder.binding.icon1.setColorFilter(color);
                viewHolder.binding.icon2.setColorFilter(color);

                viewHolder.itemView.setOnClickListener(v -> {
                    consumer.accept(item);
                });
            }

        } else {
            ViewHolderNearByNull viewHolderNearByNull = (ViewHolderNearByNull) holder;
            Hotel item = dataHotel.get(position);
            if (item instanceof Hotel) {
                RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.img).error(R.drawable.img);

                imageAutoSliderAdapter = new ImageAutoSliderAdapter(item.getImages());
                viewHolderNearByNull.binding.imageItem.setSliderAdapter(imageAutoSliderAdapter);
                viewHolderNearByNull.binding.imageItem.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                viewHolderNearByNull.binding.imageItem.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                viewHolderNearByNull.binding.imageItem.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                viewHolderNearByNull.binding.imageItem.setIndicatorSelectedColor(Color.WHITE);
                viewHolderNearByNull.binding.imageItem.setIndicatorUnselectedColor(Color.GRAY);
                viewHolderNearByNull.binding.imageItem.setScrollTimeInSec(4); //set scroll delay in seconds :
                viewHolderNearByNull.binding.imageItem.startAutoCycle();
                if (item.getTbSao() % 1 == 0) {
                    viewHolderNearByNull.binding.tvStart.setText(item.getTbSao() + "");
                } else {
                    viewHolderNearByNull.binding.tvStart.setText(decimalFormat.format(item.getTbSao()));
                }
                viewHolderNearByNull.binding.price.setText(item.getGiaDaoDong());
                if (item.getTienNghiKS().size() > 4) {
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(0).getIconImage()).apply(options).into(viewHolderNearByNull.binding.icon1);
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(1).getIconImage()).apply(options).into(viewHolderNearByNull.binding.icon2);
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(2).getIconImage()).apply(options).into(viewHolderNearByNull.binding.icon3);
                    Glide.with(holder.itemView.getContext()).load(item.getTienNghiKS().get(3).getIconImage()).apply(options).into(viewHolderNearByNull.binding.imageConvenient);

                    viewHolderNearByNull.binding.tvAmountBedRoom.setText(item.getTienNghiKS().get(0).getName());
                    viewHolderNearByNull.binding.tvCountBathroom.setText(item.getTienNghiKS().get(1).getName());
                    viewHolderNearByNull.binding.tvNameConvenient.setText(item.getTienNghiKS().get(2).getName());
                    viewHolderNearByNull.binding.tvPerson.setText(item.getTienNghiKS().get(3).getName());


                }
//                viewHolderNearByNull.binding.tvStart.setText(item.getTbSao().toString());
                viewHolderNearByNull.binding.tvTenPhong.setText(item.getName());

                viewHolderNearByNull.itemView.setOnClickListener(v -> {
                    consumer.accept(item);
                });
                viewHolderNearByNull.binding.btnDat.setOnClickListener(v -> {
                    consumer.accept(item);
                });

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return 1;
        }
        return 0;
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

    public class ViewHolderNearByNull extends RecyclerView.ViewHolder {
        public ItemBestforyouHomefragmentNotNullBinding binding;

        public ViewHolderNearByNull(ItemBestforyouHomefragmentNotNullBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

