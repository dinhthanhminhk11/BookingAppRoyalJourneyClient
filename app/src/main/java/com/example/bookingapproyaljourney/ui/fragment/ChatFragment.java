package com.example.bookingapproyaljourney.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackOrderClick;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.activity.chat_message.ChatMessageActivity;
import com.example.bookingapproyaljourney.ui.adapter.HostAdapter;
import com.example.bookingapproyaljourney.view_model.ChatViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatFragment extends Fragment implements HostAdapter.EventClick {

    private List<User> mListHost = new ArrayList<>();
    private List<String> mList = new ArrayList<>();
    private ChatViewModel chatViewModel;
    private CallbackOrderClick callbackOrderClick;

    public ChatFragment(CallbackOrderClick callbackOrderClick) {
        this.callbackOrderClick = callbackOrderClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatViewModel = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rcvListHost = view.findViewById(R.id.rcvListHost);
        LottieAnimationView progressBar = view.findViewById(R.id.progressBar);
        LinearLayout contentNullList = view.findViewById(R.id.contentNullList);
        Button btnSearch = view.findViewById(R.id.btnSearch);
        TextView textHelps = view.findViewById(R.id.textHelps);

        progressBar.setVisibility(View.VISIBLE);
        rcvListHost.setLayoutManager(new LinearLayoutManager(view.getContext()));
        btnSearch.setOnClickListener(v -> callbackOrderClick.clickHome());
        Spannable wordtoSpan = new SpannableString("Bạn không tìm thấy đặt phòng/đặt chỗ của mình ở đây? Truy cập Trung tâm trợ giúp");
        wordtoSpan.setSpan(new UnderlineSpan(), 53, 80,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textHelps.setText(wordtoSpan);
        textHelps.setOnClickListener(v-> callbackOrderClick.clickHelps() );
        chatViewModel.getMsgId(UserClient.getInstance().getId()).observe(requireActivity(), it -> {
            if (it.size() == 0) {
                rcvListHost.setVisibility(View.GONE);
                contentNullList.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }else {
                contentNullList.setVisibility(View.GONE);
                for (int i = 0; i < it.size(); i++) {
                    mList.add(it.get(i).getSendTo());
                }
                Set<String> set = new HashSet<String>(mList);
                List<String> listIdHost = new ArrayList<String>(set);
                for (int i = 0; i < listIdHost.size(); i++) {
                    chatViewModel.getHost(listIdHost.get(i)).observe(requireActivity(), item -> {
                        mListHost.add(item.get(0));
                        progressBar.setVisibility(View.GONE);
                        rcvListHost.setAdapter(new HostAdapter(mListHost, this));
                    });
                }
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