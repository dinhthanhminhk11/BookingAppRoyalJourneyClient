package com.example.bookingapproyaljourney.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.ui.activity.chat_message.ChatMessageActivity;
import com.example.bookingapproyaljourney.ui.adapter.HostAdapter;
import com.example.bookingapproyaljourney.view_model.ChatViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChatFragment extends Fragment implements HostAdapter.EventClick{

    private List<User> mListHost =new ArrayList<>();
    private List<String> mList =new ArrayList<>();
    private ChatViewModel chatViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rcvListHost = view.findViewById(R.id.rcvListHost);
        rcvListHost.setLayoutManager(new LinearLayoutManager(view.getContext()));
        chatViewModel.getMsgId(UserClient.getInstance().getId()).observe(requireActivity(),it ->{
           for (int i=0; i<it.size();i++){
               mList.add(it.get(i).getSendTo());
           }
            Set<String> set = new HashSet<String>(mList);
            List<String> listIdHost = new ArrayList<String>(set);
            for (int i=0; i<listIdHost.size(); i++){
                chatViewModel.getHost(listIdHost.get(i)).observe(requireActivity(),item ->{
                    mListHost.add(item.get(0));
                    rcvListHost.setAdapter(new HostAdapter(mListHost,this));
                });
            }


        });

        return view;
    }

    @Override
    public void onClick(User user) {
        Intent intent = new Intent(requireActivity(), ChatMessageActivity.class);
        intent.putExtra("ID_BOSS", user.get_id());
        intent.putExtra("IMG_BOSS", user.getImage());
        intent.putExtra("NAME_BOSS", user.getName());
        startActivity(intent);
    }
}