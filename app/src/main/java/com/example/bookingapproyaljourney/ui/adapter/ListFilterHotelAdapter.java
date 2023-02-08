package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackGetBookmark;
import com.example.bookingapproyaljourney.callback.InterfacePostBookmark;
import com.example.bookingapproyaljourney.databinding.ItemFilterHotelListBinding;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.repository.BookmarkRepository;
import com.example.bookingapproyaljourney.response.BookmarkResponse;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

public class ListFilterHotelAdapter extends RecyclerView.Adapter<ListFilterHotelAdapter.ViewHolder> {

    private List<Hotel> dataHotel;
    private static final DecimalFormat df = new DecimalFormat("0.0");
    private boolean isClickSpeed = true;
    private Consumer<Hotel> hotelConsumer;
    private BookmarkRepository bookmarkRepository;

    public void setHotelConsumer(Consumer<Hotel> hotelConsumer) {
        this.hotelConsumer = hotelConsumer;
    }

    public ListFilterHotelAdapter() {
        bookmarkRepository = new BookmarkRepository();
    }

    public void setDataHotel(List<Hotel> dataHotel) {
        this.dataHotel = dataHotel;
    }

    @NonNull
    @Override
    public ListFilterHotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFilterHotelListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ListFilterHotelAdapter.ViewHolder holder, int position) {
        Hotel item = dataHotel.get(position);
        if (item instanceof Hotel) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(
                            holder.binding.ivAnhKhachSan.getContext()).
                    load(item.getImages().get(0)).
                    apply(options).
                    dontAnimate().
                    into(holder.binding.ivAnhKhachSan);
            holder.binding.name.setText(item.getName());
            holder.binding.address.setText(item.getSonha() + ", " + item.getXa() + ", " + item.getHuyen() + ", " + item.getTinh());

            holder.binding.nameCategory.setText( item.getCalculated() == 0.0 ? "Khách sạn" :  "Cách " +df.format(item.getCalculated()/1000) + " Km");

//            holder.binding.name.setTextColor(color);
//            holder.binding.address.setTextColor(color);
//            holder.binding.contentCard.setCardBackgroundColor(background);
            if (item.getTbSao() == 1) {
                holder.binding.imageStar2.setVisibility(View.INVISIBLE);
                holder.binding.imageStar3.setVisibility(View.INVISIBLE);
                holder.binding.imageStar4.setVisibility(View.INVISIBLE);
                holder.binding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getTbSao() == 2) {
                holder.binding.imageStar3.setVisibility(View.INVISIBLE);
                holder.binding.imageStar4.setVisibility(View.INVISIBLE);
                holder.binding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getTbSao() == 3) {
                holder.binding.imageStar4.setVisibility(View.INVISIBLE);
                holder.binding.imageStar5.setVisibility(View.INVISIBLE);
            } else if (item.getTbSao() == 4) {
                holder.binding.imageStar5.setVisibility(View.INVISIBLE);
            }
            holder.binding.price.setText(item.getGiaDaoDong());

            bookmarkRepository.getBookmarkByIdUserAndIdHouse(UserClient.getInstance().getId(), item.get_id(), new CallbackGetBookmark() {
                @Override
                public void onResponse(BookmarkResponse bookmarkResponse) {
                    if (bookmarkResponse.getData().size() > 0) {
                        if (bookmarkResponse.getData().get(0).isCheck()) {
                            holder.binding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
                            isClickSpeed = false;
                        }
                    } else {
                        holder.binding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                        isClickSpeed = true;
                    }
                }

                @Override
                public void onFailure(BookmarkResponse bookmarkResponse) {

                }
            });

            holder.binding.bookmark.setOnClickListener(v -> {
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
                    holder.binding.bookmark.setImageResource(R.drawable.ic_rectangle_1_map);
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
                    holder.binding.bookmark.setImageResource(R.drawable.ic_bookmarkoutline);
                    isClickSpeed = true;
                }
            });

            holder.binding.linearLayout.setOnClickListener(v -> {
                hotelConsumer.accept(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataHotel == null ? 0 : dataHotel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemFilterHotelListBinding binding;

        public ViewHolder(ItemFilterHotelListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
