package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackOrderClick;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentChatBinding;
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
    private FragmentChatBinding binding;
    private HostAdapter hostAdapter;

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
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intView();
    }

    private void intView() {
        hostAdapter = new HostAdapter();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rcvListHost.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.btnSearch.setOnClickListener(v -> callbackOrderClick.clickHome());
//        Spannable wordtoSpan = new SpannableString(getString(R.string.question_bookmard));
//        wordtoSpan.setSpan(new UnderlineSpan(), 53, 80,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new StyleSpan(Typeface.BOLD), 53, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.textHelps.setText(R.string.textBookmark);
        binding.textHelps.setOnClickListener(v -> callbackOrderClick.clickHelps());
        chatViewModel.getMsgId(UserClient.getInstance().getId()).observe(requireActivity(), it -> {
            if (it.size() == 0) {
                binding.rcvListHost.setVisibility(View.GONE);
                binding.contentNullList.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            } else {
                binding.contentNullList.setVisibility(View.GONE);
                for (int i = 0; i < it.size(); i++) {
                    mList.add(it.get(i).getSendTo());
                }
                Set<String> set = new HashSet<String>(mList);
                List<String> listIdHost = new ArrayList<String>(set);
                for (int i = 0; i < listIdHost.size(); i++) {
                    chatViewModel.getHost(listIdHost.get(i)).observe(requireActivity(), item -> {
                        mListHost.add(item.get(0));
                        binding.progressBar.setVisibility(View.GONE);
                        hostAdapter.setListHost(mListHost);
                        hostAdapter.setEventClick(this);
                        binding.rcvListHost.setAdapter(hostAdapter);
                    });
                }
            }
        });
    }

    @Override
    public void onClick(User user) {
        Intent intent = new Intent(requireActivity(), ChatMessageActivity.class);
        intent.putExtra("ID_BOSS", user.get_id());
        intent.putExtra("IMG_BOSS", user.getImage());
        intent.putExtra("NAME_BOSS", user.getName());
        startActivity(intent);
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            binding.titleProfile.setTextColor(Color.WHITE);
            binding.contentProfile.setTextColor(Color.WHITE);
            binding.btnSearch.setTextColor(Color.WHITE);
            binding.textHelps.setTextColor(Color.WHITE);
            binding.btnSearch.setBackgroundResource(R.drawable.textview_border_setting_white);
            hostAdapter.setColor(Color.WHITE, R.drawable.background_boss_chat_drak, Color.WHITE);
        } else {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            binding.titleProfile.setTextColor(Color.BLACK);
            binding.contentProfile.setTextColor(Color.BLACK);
            binding.btnSearch.setTextColor(Color.BLACK);
            binding.textHelps.setTextColor(Color.BLACK);
            binding.btnSearch.setBackgroundResource(R.drawable.textview_border_black);
            hostAdapter.setColor(Color.BLACK, R.drawable.background_boss_chat, getContext().getResources().getColor(R.color.black));
        }
    }
}