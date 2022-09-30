package com.example.bookingapproyaljourney.ui.view.menu;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder> {

    private int spaceDp;

    public SpaceItem(int spaceDp) {
        this.spaceDp = spaceDp;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density * spaceDp) / (int) (c.getResources().getDisplayMetrics().density * spaceDp);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.MATCH_PARENT,
                height);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.WHITE);
        params.setMargins(0, 0, 40, 0);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
