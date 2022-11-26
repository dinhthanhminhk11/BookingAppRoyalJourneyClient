package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.databinding.ItemOrderListBinding;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.order.OrderListResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private DetailProductRepository detailProductRepository;
    private List<OrderListResponse> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Callback callback;

    public OrderAdapter(List<OrderListResponse> data, Callback callback) {
        this.data = data;
        detailProductRepository = new DetailProductRepository();
        this.callback = callback;
    }

    public interface Callback {
        void onClick(OrderListResponse orderListResponse);
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderListResponse item = data.get(position);
        if (item != null) {

            if (item.getStatus().equals("Đã xác nhận") && !item.isBanking() && item.isCashMoney() && item.isBackingPercent()) {
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_done);
                holder.itemOrderListBinding.status.setText(item.getStatus());
            } else if (item.getStatus().equals("Chủ đã huỷ")) {
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_cancel);
                holder.itemOrderListBinding.status.setText(item.getStatus());

            } else if (item.getStatus().equals("Đã xác nhận") && item.isBanking()) {
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_paid);
                holder.itemOrderListBinding.status.setText("Đã thanh toán");
            }else if(item.getStatus().equals("Khách huỷ") && !item.isCancellationDate()){
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_cancel);
                holder.itemOrderListBinding.status.setText("Đang chờ huỷ");
            }else if(item.getStatus().equals("Khách huỷ") && item.isCancellationDate()){
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_paid);
                holder.itemOrderListBinding.status.setText("Đã huỷ phòng");
            }else if(item.getStatus().equals("Đã xác nhận") && item.isBackingPercent()){
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_paid);
                holder.itemOrderListBinding.status.setText("Đã đặt cọc");
            }else  if (item.getStatus().equals("Đã xác nhận") && !item.isBanking() && item.isCashMoney() && !item.isBackingPercent()) {
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_done);
                holder.itemOrderListBinding.status.setText(item.getStatus());
            }else if(item.getStatus().equals("Đã trả phòng") && item.isCheckedOut()){
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_checkout);
                holder.itemOrderListBinding.status.setText(item.getStatus());
            }
            else {
                holder.itemOrderListBinding.status.setBackgroundResource(R.drawable.background_pendding);
                holder.itemOrderListBinding.status.setText(item.getStatus());
            }

            holder.itemOrderListBinding.countPerson.setText(item.getPerson() + " khách");
            holder.itemOrderListBinding.tvCodeBill.setText(item.getIdOder());
            holder.itemOrderListBinding.time.setText(item.getTime());
            holder.itemOrderListBinding.startDate.setText(item.getStartDate());
            holder.itemOrderListBinding.tvNgayTra.setText(item.getEndDate());
            holder.itemOrderListBinding.price.setText(fm.format(Integer.parseInt(item.getPrice())) + " VND");
            holder.itemOrderListBinding.TVsoDem.setText(item.getDay() + "");
            detailProductRepository.getProductById(item.getIdProduct(), new CallbackHouseById() {
                @Override
                public void success(HouseDetailResponse houseDetailResponse) {
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.img)
                            .error(R.drawable.img);
                    Glide.with(
                                    holder.itemOrderListBinding.imageHouse.getContext()).
                            load(houseDetailResponse.getImages().get(0)).
                            apply(options).
                            dontAnimate().
                            into(holder.itemOrderListBinding.imageHouse);
                    holder.itemOrderListBinding.nameHouse.setText(houseDetailResponse.getName());
                    holder.itemOrderListBinding.tvTimeNhanPhong.setText(houseDetailResponse.getOpening());
                    holder.itemOrderListBinding.tvTimeTra.setText(houseDetailResponse.getEnding());
                }

                @Override
                public void failure(Throwable t) {

                }
            });
            holder.itemView.setOnClickListener(v -> {
                callback.onClick(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderListBinding itemOrderListBinding;

        public ViewHolder(ItemOrderListBinding itemOrderListBinding) {
            super(itemOrderListBinding.getRoot());
            this.itemOrderListBinding = itemOrderListBinding;
        }
    }
}
