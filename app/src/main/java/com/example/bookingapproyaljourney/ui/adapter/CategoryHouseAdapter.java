package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;

import java.util.List;

public class CategoryHouseAdapter extends RecyclerView.Adapter<CategoryHouseAdapter.ViewHolder> {
    private UpdateRecyclerView updateRecyclerView;
    private int row_index = -1;
    private boolean selected = true;
    private boolean check = true;
    public static int index = 1;
    private List<Category> dataCategory;
    private List<House> dataHouse;


    public CategoryHouseAdapter(UpdateRecyclerView updateRecyclerView, List<Category> dataCategory) {
        this.updateRecyclerView = updateRecyclerView;
        this.dataCategory = dataCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_homefragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category item = dataCategory.get(position);
        holder.nameCategory.setText(item.getName());

        if (check) {

            check = false;
        }

        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();

            if (position == 0) {
                // trueyefn list nhà ở all

                index = 1;
            } else if (position == 1) {

                index = 2;
            } else if (position == 2) {

                index = 3;
            } else if (position == 3) {

                index = 4;
            } else if (position == 4) {

                index = 5;
            }
        });

        if (selected) {
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.gradient_blue);
                holder.nameCategory.setTextColor(Color.parseColor("#FAFAFA"));
            }
            selected = false;
        } else {
            if (row_index == position) {
                holder.itemView.setBackgroundResource(R.drawable.gradient_blue);
                holder.nameCategory.setTextColor(Color.parseColor("#FAFAFA"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"));
                holder.nameCategory.setTextColor(Color.parseColor("#858585"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataCategory == null ? 0 : dataCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCategory = (TextView) itemView.findViewById(R.id.nameCategoryItemCategory);
        }
    }

    public interface UpdateRecyclerView {
        public void callbacksNearFromYou(int position, List<House> list);

        public void callbacksBestForYou(int position, List<House> list);
    }
}

