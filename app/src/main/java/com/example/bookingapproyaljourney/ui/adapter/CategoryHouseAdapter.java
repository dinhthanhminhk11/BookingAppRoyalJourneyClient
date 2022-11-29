package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.callback.HouseByCategoryCallback;
import com.example.bookingapproyaljourney.callback.InterfaceResponseHouseNearestByUser;
import com.example.bookingapproyaljourney.callback.UpdateRecyclerView;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.repository.CategoryRepository;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CategoryHouseAdapter extends RecyclerView.Adapter<CategoryHouseAdapter.ViewHolder> {
    private UpdateRecyclerView updateRecyclerView;
    private int row_index = -1;
    private boolean selected = true;
    private boolean check = true;
    public static int index = 1;
    private List<Category> dataCategory;
    private List<House> dataHouse;
    private CategoryRepository categoryRepository;
    private Location location;

    public CategoryHouseAdapter(UpdateRecyclerView updateRecyclerView, List<Category> dataCategory, Location location) {
        this.updateRecyclerView = updateRecyclerView;
        this.dataCategory = dataCategory;
        this.location = location;
        categoryRepository = new CategoryRepository();
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
            initData(item, position);
            check = false;
        }

        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            notifyDataSetChanged();
            updateRecyclerView.callLoading(View.VISIBLE);
            if (position == 0) {
                // trueyefn list nhà ở all
                initData(item, position);
                index = 1;
            } else if (position == 1) {
                initData(item, position);
                index = 2;
            } else if (position == 2) {
                initData(item, position);
                index = 3;
            } else if (position == 3) {
                initData(item, position);
                index = 4;
            } else if (position == 4) {
                initData(item, position);
                index = 5;
            }
        });

        if (selected) {
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.gradient_blue);
                holder.nameCategory.setTextColor(Color.parseColor("#FFFFFFFF"));
            }
            selected = false;
        } else {
            if (row_index == position) {
                holder.itemView.setBackgroundResource(R.drawable.gradient_blue);
                holder.nameCategory.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                holder.nameCategory.setTextColor(Color.parseColor("#858585"));
            }
        }
    }

    private void initData(Category item, int position) {

        if (location != null) {
            categoryRepository.getHouseNearestByUserAndLocationUser(new HouseNearestByUser(location.getLongitude(), location.getLatitude(), 10000, item.getId()), new InterfaceResponseHouseNearestByUser() {
                @Override
                public void onResponse(HouseNearestByUserResponse houseNearestByUserResponse) {
                    updateRecyclerView.callbacksNearFromYou(position, houseNearestByUserResponse);
                    updateRecyclerView.callLoading(View.GONE);
                }

                @Override
                public void onFailure(Throwable t) {

                }

            });

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    categoryRepository.getHouseByCategory(item.getId(), new HouseByCategoryCallback() {
                        @Override
                        public void success(CategoryBestForYouResponse categoryBestForYouResponse) {
                            updateRecyclerView.callbacksBestForYou(position, categoryBestForYouResponse);
                            updateRecyclerView.callLoading(View.GONE);
                        }

                        @Override
                        public void failure(Throwable t) {

                        }
                    });
                }
            }, 200);


        }else {
            updateRecyclerView.callLoading(View.VISIBLE);
            categoryRepository.getHouseByCategory(item.getId(), new HouseByCategoryCallback() {
                @Override
                public void success(CategoryBestForYouResponse categoryBestForYouResponse) {
                    updateRecyclerView.callBackNull( categoryBestForYouResponse);
                    updateRecyclerView.callLoading(View.GONE);
                }

                @Override
                public void failure(Throwable t) {

                }
            });
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
}

