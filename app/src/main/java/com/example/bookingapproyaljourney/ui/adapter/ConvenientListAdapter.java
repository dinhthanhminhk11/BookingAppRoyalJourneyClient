package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.bookingapproyaljourney.model.hotel.TienNghiK;
import com.example.bookingapproyaljourney.model.house.Convenient;

import java.util.ArrayList;
import java.util.List;

public class ConvenientListAdapter extends RecyclerView.Adapter<ConvenientListAdapter.ViewHoler> {
    private ArrayList<TienNghiK> convenientTestList;
    private Context context;
    private int color = Color.BLACK;

    public ConvenientListAdapter(Context context) {
        this.context = context;
    }
    public void setConvenientTestList(ArrayList<TienNghiK> convenientTestList) {
        this.convenientTestList = convenientTestList;
    }
    public void setColor(int color) {
        this.color = color;
    }
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_convenient, parent, false);
        return new ViewHoler(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);
        Glide.with(context).load(convenientTestList.get(position).getIconImage()).apply(options).into(holder.imgConvenien);
        holder.tvConvenien.setText(convenientTestList.get(position).getName());
        holder.tvConvenien.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        if (convenientTestList != null) {
            return convenientTestList.size();
        }
        return 0;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        private RelativeLayout layoutItem;
        private ImageView imgConvenien;
        private TextView tvConvenien;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imgConvenien = (ImageView) itemView.findViewById(R.id.image);
            tvConvenien = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
