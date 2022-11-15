package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.databinding.ItemBookmarkByUserBinding;
import com.example.bookingapproyaljourney.model.house.Bookmark;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private boolean isClickSpeed = true;
    private List<Bookmark> data;
    private DetailProductRepository detailProductRepository;
    private Callback callback;
    private NumberFormat fm = new DecimalFormat("#,###");
    public BookmarkAdapter(List<Bookmark> data, Callback callback) {
        this.data = data;
        this.callback = callback;
        detailProductRepository = new DetailProductRepository();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBookmarkByUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = data.get(position);
        if (bookmark instanceof Bookmark) {
            callback.onLoading(View.VISIBLE);
            detailProductRepository.getProductById(bookmark.getIdHouse(), new CallbackHouseById() {
                @Override
                public void success(HouseDetailResponse houseDetailResponse) {
                    callback.onLoading(View.GONE);
                    holder.itemBookmarkByUserBinding.address.setText(houseDetailResponse.getNameLocation());
                    holder.itemBookmarkByUserBinding.name.setText(houseDetailResponse.getName());

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.img)
                            .error(R.drawable.img);
                    Glide.with(
                                    holder.itemBookmarkByUserBinding.ivAnhKhachSan.getContext()).
                            load(houseDetailResponse.getImages().get(0)).
                            apply(options).
                            dontAnimate().
                            into(holder.itemBookmarkByUserBinding.ivAnhKhachSan);

                    holder.itemBookmarkByUserBinding.price.setText("$ " + fm.format(houseDetailResponse.getPrice()));
                    holder.itemBookmarkByUserBinding.direct.setOnClickListener(v -> {
                        callback.onDirect(houseDetailResponse);
                    });
                }

                @Override
                public void failure(Throwable t) {
                    callback.onLoading(View.GONE);
                }
            });

            if (bookmark.isCheck()) {
                holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                isClickSpeed = false;
            }

            holder.itemBookmarkByUserBinding.bookmark.setOnClickListener(v -> {
                if (isClickSpeed) {
                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                    isClickSpeed = false;
                } else {
                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                    isClickSpeed = true;
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBookmarkByUserBinding itemBookmarkByUserBinding;

        public ViewHolder(ItemBookmarkByUserBinding binding) {
            super(binding.getRoot());
            itemBookmarkByUserBinding = binding;
        }
    }

    public interface Callback {
        void onLoading(Integer integer);

        void onDirect(HouseDetailResponse houseDetailResponse);
    }
}
