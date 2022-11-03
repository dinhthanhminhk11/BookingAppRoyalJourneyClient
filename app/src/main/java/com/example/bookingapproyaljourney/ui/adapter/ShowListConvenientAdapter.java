package com.example.bookingapproyaljourney.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowListConvenientAdapter extends RecyclerView.Adapter<ShowListConvenientAdapter.ViewHodel>  {

    @NonNull
    @Override
    public ShowListConvenientAdapter.ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowListConvenientAdapter.ViewHodel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
        }
    }
}
