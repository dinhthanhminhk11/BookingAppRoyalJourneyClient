package com.example.bookingapproyaljourney.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.callback.CategoryCallBack;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.databinding.ItemBookmarkByUserBinding;
import com.example.bookingapproyaljourney.model.house.Bookmark;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.repository.CategoryRepository;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
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
    private CategoryRepository categoryRepository;
    private BookmarkRepository bookmarkRepository;

    public BookmarkAdapter(List<Bookmark> data, Callback callback) {
        this.data = data;
        this.callback = callback;
        detailProductRepository = new DetailProductRepository();
        categoryRepository = new CategoryRepository();
        bookmarkRepository = new BookmarkRepository();
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

                    holder.itemView.setOnClickListener(v -> {
                        callback.onClick(bookmark.getIdHouse());
                    });

                    holder.itemBookmarkByUserBinding.address.setText(houseDetailResponse.getNameLocation());
                    holder.itemBookmarkByUserBinding.name.setText(houseDetailResponse.getName());

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.soap)
                            .error(R.drawable.soap);
                    Glide.with(holder.itemBookmarkByUserBinding.ivAnhKhachSan.getContext()).
                            load(houseDetailResponse.getImages().get(0)).
                            apply(options).
                            dontAnimate().
                            into(holder.itemBookmarkByUserBinding.ivAnhKhachSan);

                    holder.itemBookmarkByUserBinding.price.setText("$ " + fm.format(houseDetailResponse.getPrice()));
                    holder.itemBookmarkByUserBinding.direct.setOnClickListener(v -> {
                        callback.onDirect(houseDetailResponse);
                    });
                    categoryRepository.getCategoryById(houseDetailResponse.getCategory().getId(), new CategoryCallBack() {
                        @Override
                        public void success(String result) {
                            holder.itemBookmarkByUserBinding.nameCategory.setText(result);
                        }

                        @Override
                        public void failure(Throwable t) {

                        }
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
                    bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), bookmark.getIdHouse()), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                            Log.e("Minh", bookmarkResponse.getData().toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                    isClickSpeed = false;
                } else {
                    data.remove(position);
                    bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), bookmark.getIdHouse(), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                            Log.e("Minh", "Xoá bookmark thành công");
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
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

        void onClick(String id);
    }
}
