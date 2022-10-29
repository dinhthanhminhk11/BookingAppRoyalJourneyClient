package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.house.Room;


import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHodel> {
    private List<Room> roomList;
    private Context context;

    public RoomAdapter(Context context, List<Room> roomList ) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);

        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        final Room room = roomList.get(position);
        if (room == null){
            return;
        }
        holder.room.setImageResource(room.getRoom());
        holder.room1.setText(room.getRoom1());
        holder.room2.setText(room.getRoom2());
    }

    @Override
    public int getItemCount() {
        if (roomList != null){
            return roomList.size();
        }
        return 0;
    }

    public class ViewHodel extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private ImageView room;
        private TextView room1;
        private  TextView room2;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);

            room = (ImageView) itemView.findViewById(R.id.room);
            room1 = (TextView) itemView.findViewById(R.id.room1);
            room2 = (TextView) itemView.findViewById(R.id.room2);
        }
    }
}
