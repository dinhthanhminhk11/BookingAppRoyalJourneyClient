package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Category;

import java.util.List;

public class SortedByAdapter extends RecyclerView.Adapter<SortedByAdapter.ViewHolder>{
    private List<Category> data;
    private int row_index = -1;
    private boolean selected = true;
    private boolean check = true;
    public static int index = 1;
    private Callback callback;


    public SortedByAdapter(List<Category> data, Callback callback) {
        this.callback = callback;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sorted_by, parent, false);
        return new SortedByAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category item = data.get(position);
        if (item != null) {
            holder.name.setText(item.getName());
        }

        if (check) {

            check = false;
        }
        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            if (position == 0) {
                index = 1;
            } else if (position == 1) {
                index = 2;
            }
        });

        if (selected) {
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.background_item_sortedby);
                holder.name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            }
            selected = false;
        } else {
            if (row_index == position) {
                holder.itemView.setBackgroundResource(R.drawable.background_item_sortedby);
                holder.name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
            } else {
                holder.itemView.setBackgroundResource(R.drawable.background_item_sortedby_no_selected);
                holder.name.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.blue));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);

        }
    }

    public interface Callback {
        void onClick();
    }
}
