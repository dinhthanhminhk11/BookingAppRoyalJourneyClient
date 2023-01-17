package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.house.Bookmark;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.repository.CategoryRepository;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.repository.Repository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.view_model.HotelInfoViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private boolean isClickSpeed = true;
    private List<Bookmark> data;
    private Callback callback;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Repository repository;
    private BookmarkRepository bookmarkRepository;

    private int color = Color.BLACK;
    private int background = Color.WHITE;

    public BookmarkAdapter() {
        repository = new Repository();
        bookmarkRepository = new BookmarkRepository();
    }

    public void setData(List<Bookmark> data) {
        this.data = data;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setColor(int color, int background) {
        this.color = color;
        this.background = background;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBookmarkByUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = data.get(position);
        if (bookmark instanceof Bookmark) {
            callback.onLoading(View.VISIBLE);
            repository.getHotelById(bookmark.getIdHotel(), new Consumer<HotelById>() {
                @Override
                public void accept(HotelById hotelById) {
                    if (hotelById instanceof HotelById) {
                        callback.onLoading(View.GONE);

                        holder.itemView.setOnClickListener(v -> {
                            callback.onClick(bookmark.getIdHotel());
                        });
                        holder.itemBookmarkByUserBinding.name.setTextColor(color);
                        holder.itemBookmarkByUserBinding.address.setTextColor(color);
                        holder.itemBookmarkByUserBinding.contentCard.setCardBackgroundColor(background);

                        holder.itemBookmarkByUserBinding.address.setText(hotelById.getDataHotel().getSonha() + ", " + hotelById.getDataHotel().getXa() + ", " + hotelById.getDataHotel().getHuyen() + ", " + hotelById.getDataHotel().getTinh());
                        holder.itemBookmarkByUserBinding.name.setText(hotelById.getDataHotel().getName());

                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.img)
                                .error(R.drawable.img);
                        Glide.with(holder.itemBookmarkByUserBinding.ivAnhKhachSan.getContext()).
                                load(hotelById.getDataHotel().getImages().get(0)).
                                apply(options).
                                dontAnimate().
                                into(holder.itemBookmarkByUserBinding.ivAnhKhachSan);

                        holder.itemBookmarkByUserBinding.price.setText(hotelById.getDataHotel().getGiaDaoDong());
                        holder.itemBookmarkByUserBinding.direct.setOnClickListener(v -> {
                            callback.onDirect(hotelById);
                        });


                    }

                }
            });
            if (bookmark.isCheck()) {
                holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                isClickSpeed = false;
            }

            holder.itemBookmarkByUserBinding.bookmark.setOnClickListener(v -> {
                if (isClickSpeed) {
                    bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), bookmark.getIdHotel()), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                        }

                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });
                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                    isClickSpeed = false;
                } else {
                    data.remove(position);
                    bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), bookmark.getIdHotel(), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                    isClickSpeed = true;
                }
            });




//
//            detailProductRepository.getProductById(bookmark.getIdHouse(), new CallbackHouseById() {
//                @Override
//                public void success(HouseDetailResponse houseDetailResponse) {
//                    callback.onLoading(View.GONE);
//
//                    holder.itemView.setOnClickListener(v -> {
//                        callback.onClick(bookmark.getIdHouse());
//                    });
//
//                    holder.itemBookmarkByUserBinding.name.setTextColor(color);
//                    holder.itemBookmarkByUserBinding.address.setTextColor(color);
//                    holder.itemBookmarkByUserBinding.contentCard.setCardBackgroundColor(background);
//
//                    holder.itemBookmarkByUserBinding.address.setText(houseDetailResponse.getNameLocation());
//                    holder.itemBookmarkByUserBinding.name.setText(houseDetailResponse.getName());
//
//                    RequestOptions options = new RequestOptions()
//                            .centerCrop()
//                            .placeholder(R.drawable.img)
//                            .error(R.drawable.img);
//                    Glide.with(holder.itemBookmarkByUserBinding.ivAnhKhachSan.getContext()).
//                            load(houseDetailResponse.getImages().get(0)).
//                            apply(options).
//                            dontAnimate().
//                            into(holder.itemBookmarkByUserBinding.ivAnhKhachSan);
//
//                    holder.itemBookmarkByUserBinding.price.setText("$ " + fm.format(houseDetailResponse.getPrice()));
//                    holder.itemBookmarkByUserBinding.direct.setOnClickListener(v -> {
//                        callback.onDirect(houseDetailResponse);
//                    });
//                    categoryRepository.getCategoryById(houseDetailResponse.getCategory().getId(), new CategoryCallBack() {
//                        @Override
//                        public void success(String result) {
//                            holder.itemBookmarkByUserBinding.nameCategory.setText(result);
//                        }
//
//                        @Override
//                        public void failure(Throwable t) {
//
//                        }
//                    });
//                }
//
//                @Override
//                public void failure(Throwable t) {
//                    callback.onLoading(View.GONE);
//                }
//            });
//
//            if (bookmark.isCheck()) {
//                holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
//                isClickSpeed = false;
//            }
//
//            holder.itemBookmarkByUserBinding.bookmark.setOnClickListener(v -> {
//                if (isClickSpeed) {
//                    bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), bookmark.getIdHouse()), new InterfacePostBookmark() {
//                        @Override
//                        public void onResponse(BookmarkResponse bookmarkResponse) {
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t) {
//
//                        }
//                    });
//                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
//                    isClickSpeed = false;
//                } else {
//                    data.remove(position);
//                    bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), bookmark.getIdHouse(), new InterfacePostBookmark() {
//                        @Override
//                        public void onResponse(BookmarkResponse bookmarkResponse) {
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t) {
//
//                        }
//                    });
//                    holder.itemBookmarkByUserBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
//                    isClickSpeed = true;
//                }
//            });
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

        void onDirect(HotelById hotelById);

        void onClick(String id);
    }
}
