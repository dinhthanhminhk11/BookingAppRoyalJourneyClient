package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Image;


import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> imageList;
    Context context;

    private int row_index = -1;
    private boolean selected = true;
    private boolean check = true;


    public ImageAdapter(Context context,List<String> imageList) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imager,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.soap)
                .error(R.drawable.soap);
        Glide.with(holder.itemView.getContext()).load(imageList.get(position)).apply(options).into(holder.imgPicture);

        if(selected){
            if(position == 0){
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFF00"));
            }
            selected = false;
        }else{
            if(row_index==position){
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFF00"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPicture;
        private View imgBackground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture =itemView.findViewById(R.id.imgPicture);
            imgBackground = itemView.findViewById(R.id.imgBackground);
        }
    }
}
