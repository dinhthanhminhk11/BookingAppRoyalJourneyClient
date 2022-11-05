package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Convenient;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {
    private List<Convenient> data;
    private boolean isClickSpeed = true;
    public FacilitiesAdapter(List<Convenient> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facilities, parent, false);
        return new FacilitiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Convenient convenient = data.get(position);
        if (convenient != null) {
            holder.nameConvenient.setText(convenient.getName());
            holder.checkbox.setImageResource(convenient.getImageCheck());
        }

        holder.itemView.setOnClickListener(v -> {
            if(isClickSpeed){
                holder.checkbox.setImageResource(R.drawable.ic_subtract_check_box);
                isClickSpeed = false;
            }else {
                holder.checkbox.setImageResource(R.drawable.ic_ellipse_96);
                isClickSpeed = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView checkbox;
        private TextView nameConvenient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkbox = (ImageView) itemView.findViewById(R.id.checkbox);
            nameConvenient = (TextView) itemView.findViewById(R.id.nameConvenient);

        }
    }
}