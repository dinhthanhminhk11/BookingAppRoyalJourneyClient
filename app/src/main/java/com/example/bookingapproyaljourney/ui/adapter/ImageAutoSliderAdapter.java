package com.example.bookingapproyaljourney.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.libraryautoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageAutoSliderAdapter extends SliderViewAdapter<ImageAutoSliderAdapter.Holder> {
    private List<String> list;

    public ImageAutoSliderAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);
        return new ImageAutoSliderAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img)
                .error(R.drawable.img);
        Glide.with(viewHolder.itemView.getContext()).
                load(list.get(position)).
                apply(options).
                dontAnimate().
                into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class Holder extends ViewHolder {
        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);

        }
    }
}
