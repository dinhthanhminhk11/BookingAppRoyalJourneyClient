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

import java.util.ArrayList;

public class ConvenientAdapter extends RecyclerView.Adapter<ConvenientAdapter.ViewHodel> {
    private ArrayList<TienNghiK> convenientTestList;
    private Context context;
    private int color = Color.BLACK;

    public ConvenientAdapter(Context context) {
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
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_convenient, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
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
        return convenientTestList.size() < 5 ? convenientTestList.size() : 5;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        private RelativeLayout layoutItem;
        private ImageView imgConvenien;
        private TextView tvConvenien;

        public ViewHodel(@NonNull View itemView) {
            super(itemView);

            imgConvenien = (ImageView) itemView.findViewById(R.id.image);
            tvConvenien = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
