package com.example.bookingapproyaljourney.ui.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.databinding.ItemNearFromYouMapBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.repository.CategoryRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class NearFromYouAdapterMap extends RecyclerView.Adapter<NearFromYouAdapterMap.ViewHolder> {
    private List<Hotel> data;
    private Callback callback;
    private boolean isClickSpeed = true;
    private NumberFormat fm = new DecimalFormat("#,###");
    private CategoryRepository categoryRepository;
    private BookmarkRepository bookmarkRepository;
    private int color = Color.BLACK;
    private int background = Color.WHITE;
    private Consumer consumer;

    public NearFromYouAdapterMap() {
        categoryRepository = new CategoryRepository();
        bookmarkRepository = new BookmarkRepository();
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setData(List<Hotel> data) {
        this.data = data;
    }


    public void setColor(int color, int background) {
        this.color = color;
        this.background = background;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNearFromYouMapBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel item = data.get(position);
        if (holder != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(
                            holder.itemNearFromYouMapBinding.ivAnhKhachSan.getContext()).
                    load(item.getImages().get(0)).
                    apply(options).
                    dontAnimate().
                    into(holder.itemNearFromYouMapBinding.ivAnhKhachSan);
            holder.itemNearFromYouMapBinding.name.setText(item.getName());
            holder.itemNearFromYouMapBinding.address.setText(item.getSonha() + ", " + item.getXa() + ", " + item.getHuyen() + ", " + item.getTinh());

            holder.itemNearFromYouMapBinding.name.setTextColor(color);
            holder.itemNearFromYouMapBinding.address.setTextColor(color);
            holder.itemNearFromYouMapBinding.contentCard.setCardBackgroundColor(background);
           /* if (item.getStart() == 1) {
                holder.itemNearFromYouMapBinding.imageStar2.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar3.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 2) {
                holder.itemNearFromYouMapBinding.imageStar3.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 3) {
                holder.itemNearFromYouMapBinding.imageStar4.setVisibility(View.INVISIBLE);
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getStart() == 4) {
                holder.itemNearFromYouMapBinding.imageStar5.setVisibility(View.INVISIBLE);
            }*/
            holder.itemNearFromYouMapBinding.price.setText(item.getGiaDaoDong());
            holder.itemNearFromYouMapBinding.direct.setOnClickListener(v -> {
                callback.onDirect(item);
            });

            bookmarkRepository.getBookmarkByIdUserAndIdHouse(UserClient.getInstance().getId(), item.get_id(), new CallbackGetBookmark() {
                @Override
                public void onResponse(BookmarkResponse bookmarkResponse) {
                    if (bookmarkResponse.getData().size() > 0) {
                        if (bookmarkResponse.getData().get(0).isCheck()) {
                            holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                            isClickSpeed = false;
                        }
                    } else {
                        holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                        isClickSpeed = true;
                    }
                }

                @Override
                public void onFailure(BookmarkResponse bookmarkResponse) {

                }
            });


            holder.itemNearFromYouMapBinding.bookmark.setOnClickListener(v -> {
                if (isClickSpeed) {
                    bookmarkRepository.addBookMark(new PostIDUserAndIdHouse(UserClient.getInstance().getId(), item.get_id()), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                            Log.e("Minh", bookmarkResponse.getData().toString());
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                    isClickSpeed = false;
                } else {
                    bookmarkRepository.deleteBookmark(UserClient.getInstance().getId(), item.get_id(), new InterfacePostBookmark() {
                        @Override
                        public void onResponse(BookmarkResponse bookmarkResponse) {
                            Log.e("Minh", "Xoá bookmark thành công");
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    holder.itemNearFromYouMapBinding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                    isClickSpeed = true;
                }
            });

            holder.itemNearFromYouMapBinding.linearLayout.setOnClickListener(v -> {
                callback.clickItem(item.get_id());
            });

        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNearFromYouMapBinding itemNearFromYouMapBinding;

        public ViewHolder(ItemNearFromYouMapBinding itemNearFromYouMapBinding) {
            super(itemNearFromYouMapBinding.getRoot());
            this.itemNearFromYouMapBinding = itemNearFromYouMapBinding;
        }
    }

    public interface Callback {
        void onClickBookMark(House house);

        void onDirect(Hotel hotel);

        void clickItem(String id);

    }
}
