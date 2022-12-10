package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Bathroom;

import java.util.List;

public class BathdRoomAdapter extends RecyclerView.Adapter<BathdRoomAdapter.ViewHoler> {
    private List<Bathroom> bathroomList;
    private Context context;

    public BathdRoomAdapter(List<Bathroom> bathroomList, Context context) {
        this.bathroomList = bathroomList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bathdrooms,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.soap)
                .error(R.drawable.soap);
        Glide.with(context).load(bathroomList.get(position).getIconImage()).apply(options).into(holder.imgBathdRoom);
        holder.nameBathdRoom.setText(bathroomList.get(position).getName());
//        holder.contentBathdRoom.setText(bathroomList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return bathroomList.size() < 4 ? bathroomList.size() : 4;
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        private RelativeLayout layoutImgbathdroom;
        private ImageView imgBathdRoom;
        private TextView nameBathdRoom;
//        private TextView contentBathdRoom;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            layoutImgbathdroom = (RelativeLayout) itemView.findViewById(R.id.layout_Imgbathdroom);
            imgBathdRoom = (ImageView) itemView.findViewById(R.id.imgBathdRoom);
            nameBathdRoom = (TextView) itemView.findViewById(R.id.nameBathdRoom);
//            contentBathdRoom = (TextView) itemView.findViewById(R.id.contentBathdRoom);

        }
    }
}
