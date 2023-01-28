package com.example.bookingapproyaljourney.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.databinding.ItemSearchHotelBinding;
import com.example.bookingapproyaljourney.model.search.SearchModel;

import java.util.List;
import java.util.function.Consumer;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<SearchModel> data;
    private String textHighLight;

    private Consumer consumer;
    public SearchAdapter() {
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void setTextHighLight(String textHighLight) {
        this.textHighLight = textHighLight;
    }

    public void setData(List<SearchModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSearchHotelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        SearchModel searchModel = data.get(position);
        if (searchModel != null) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.imagehasmap)
                    .error(R.drawable.imagehasmap);
            Glide.with(holder.itemView.getContext()).load(searchModel.getImageHotel()).apply(options).into(holder.itemSearchHotelBinding.image);
            holder.itemSearchHotelBinding.address.setText(searchModel.getAddress());
            holder.itemSearchHotelBinding.nameHotel.setText(searchModel.getNameHotel());
            if (searchModel.getType() == 2) {
                holder.itemSearchHotelBinding.typeHotel.setVisibility(View.VISIBLE);
            } else {
                holder.itemSearchHotelBinding.typeHotel.setVisibility(View.GONE);
            }

            setHighLightedText(holder.itemSearchHotelBinding.address, textHighLight);

            holder.itemView.setOnClickListener(v->{
                consumer.accept(searchModel);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemSearchHotelBinding itemSearchHotelBinding;

        public ViewHolder(ItemSearchHotelBinding itemSearchHotelBinding) {
            super(itemSearchHotelBinding.getRoot());
            this.itemSearchHotelBinding = itemSearchHotelBinding;
        }
    }

    public void setHighLightedText(TextView tv, String textToHighlight) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToHighlight, 0);
        Spannable wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToHighlight, ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(new UnderlineSpan(), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordToSpan.setSpan(new StyleSpan(Typeface.BOLD), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordToSpan.setSpan(new ForegroundColorSpan(Color.BLACK), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }
}
