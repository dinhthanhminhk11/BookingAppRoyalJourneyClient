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
import com.example.bookingapproyaljourney.model.house.Convenient;



import java.util.List;

public class ConvenientAdapter extends RecyclerView.Adapter<ConvenientAdapter.ViewHodel> {
    private List<Convenient> convenientTestList;
    private Context context;

    public ConvenientAdapter(List<Convenient> convenientTestList, Context context) {
        this.convenientTestList = convenientTestList;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return convenientTestList == null ? 0 : convenientTestList.size();
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
