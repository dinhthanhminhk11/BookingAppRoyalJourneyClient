package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Gallery;
import com.example.bookingapproyaljourney.ui.activity.ImageActivity;


import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHoler> {
    private List<Gallery> galleryList;
    private Context context;

    public GalleryAdapter(Context context, List<Gallery> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        final Gallery gallery = galleryList.get(position);
        if (gallery == null) {
            return;
        }
        holder.ivimgHotel.setImageResource(galleryList.get(position).getIvimgHotel());
        holder.layout_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(gallery);
            }
        });
        if (position == 3) {
            if (galleryList.size() - 4 == 0) {
                holder.btnAmount.setVisibility(View.GONE);
            } else {
                holder.btnAmount.setVisibility(View.VISIBLE);
                holder.btnAmount.setText("+" + (galleryList.size() - 4));
            }
        }
    }

    private void onClickGoToDetail(Gallery gallery) {
        Intent intent = new Intent(context, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("", gallery);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return galleryList.size() < 4 ? galleryList.size() : 4;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private RelativeLayout layout_Item;
        private ImageView ivimgHotel;
        private TextView btnAmount;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layout_Item = (RelativeLayout) itemView.findViewById(R.id.layout_Item);
            ivimgHotel = (ImageView) itemView.findViewById(R.id.ivimgHotel);
            btnAmount = (TextView) itemView.findViewById(R.id.btnAmount);
        }
    }
}
