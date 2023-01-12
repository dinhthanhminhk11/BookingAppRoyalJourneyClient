package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemOrderListBinding;
import com.example.bookingapproyaljourney.repository.DetailProductRepository;
import com.example.bookingapproyaljourney.response.bill.ListBillResponse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.Consumer;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private DetailProductRepository detailProductRepository;
    private List<ListBillResponse> data;
    private NumberFormat fm = new DecimalFormat("#,###");
    private Consumer callback;

    private int color = Color.BLACK;
    private int background = Color.WHITE;

    public OrderAdapter() {
        detailProductRepository = new DetailProductRepository();
    }

    public void setColor(int color, int background) {
        this.color = color;
        this.background = background;
    }

    public void setData(List<ListBillResponse> data) {
        this.data = data;
    }

    public void setCallback(Consumer consumer) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        ListBillResponse item = data.get(position);
        if (item != null) {
            holder.itemOrderListBinding.countPerson.setText(item.getCountPerson());
            holder.itemOrderListBinding.tvCodeBill.setText(item.getCodeBill());
            holder.itemOrderListBinding.time.setText(item.getTimeCreate());
            holder.itemOrderListBinding.startDate.setText(item.getStartDate());
            holder.itemOrderListBinding.tvNgayTra.setText(item.getEndDate());
            holder.itemOrderListBinding.price.setText(fm.format(item.getPrice()) + " ₫");
            holder.itemOrderListBinding.TVsoDem.setText(item.getCountDay() + "");
            holder.itemOrderListBinding.countPerson.setTextColor(color);
            holder.itemOrderListBinding.nameHouse.setTextColor(color);
            holder.itemOrderListBinding.time.setTextColor(color);
            holder.itemOrderListBinding.startDate.setTextColor(color);
            holder.itemOrderListBinding.tvNgayTra.setTextColor(color);
            holder.itemOrderListBinding.price.setTextColor(color);
            holder.itemOrderListBinding.viewLine1.setBackgroundColor(color);
            holder.itemOrderListBinding.viewLine2.setBackgroundColor(color);
            holder.itemOrderListBinding.viewLine3.setBackgroundColor(color);
            holder.itemOrderListBinding.titleSum.setTextColor(color);
            holder.itemOrderListBinding.contentCard.setCardBackgroundColor(background);
            holder.itemOrderListBinding.nameHouse.setText(item.getNameHotel());
            holder.itemOrderListBinding.tvTimeNhanPhong.setText(item.getTimeInRoom());
            holder.itemOrderListBinding.tvTimeTra.setText(item.getTimeOutRoom());
            holder.itemOrderListBinding.status.setText(item.getStatus());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img);
            Glide.with(holder.itemOrderListBinding.imageHouse.getContext()).
                    load(item.getImageHotel()).
                    apply(options).
                    dontAnimate().
                    into(holder.itemOrderListBinding.imageHouse);
            holder.itemView.setOnClickListener(v -> {
                callback.accept(item.getIdBill());
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
