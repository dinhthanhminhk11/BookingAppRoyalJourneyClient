package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.callback.CallbackOrderClick;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentBookmarkBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.adapter.BookmarkAdapter;
import com.example.bookingapproyaljourney.view_model.BookmarkViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BookmarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentBookmarkBinding binding;
    private BookmarkViewModel bookmarkViewModel;
    private BookmarkAdapter bookmarkAdapter;
    private CallbackOrderClick callbackOrderClick;

    public BookmarkFragment(CallbackOrderClick callbackOrderClick) {
        this.callbackOrderClick = callbackOrderClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        bookmarkAdapter = new BookmarkAdapter();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.textHelps.setText(R.string.textBookmark);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        bookmarkViewModel.getListBookmarkById(UserClient.getInstance().getId());

        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookmarkViewModel.getListBookmarkById(UserClient.getInstance().getId());
                binding.reLoad.setRefreshing(false);
            }
        });

        binding.btnSearch.setOnClickListener(v -> callbackOrderClick.clickHome());
        binding.textHelps.setOnClickListener(v -> callbackOrderClick.clickHelps());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        bookmarkViewModel.getBookmarkResponseMutableLiveData().observe(getActivity(), new Observer<BookmarkResponse>() {
            @Override
            public void onChanged(BookmarkResponse bookmarkResponse) {
                if (bookmarkResponse.getData().size() > 0) {
                    binding.contentNullList.setVisibility(View.GONE);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                }
                bookmarkAdapter.setData(bookmarkResponse.getData());
                bookmarkAdapter.setCallback(new BookmarkAdapter.Callback() {
                    @Override
                    public void onLoading(Integer integer) {
                        binding.progressBar.setVisibility(integer);
                    }

                    @Override
                    public void onDirect(HotelById hotelById) {

                    }

                    @Override
                    public void onClick(String id) {
                        Intent intent = new Intent(getActivity(), HotelActivity.class);
                        intent.putExtra(AppConstant.HOTEL_EXTRA, id);
                        startActivity(intent);
                    }
                });
                binding.recyclerView.setAdapter(bookmarkAdapter);
            }
        });

        bookmarkViewModel.getmProgressMutableData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(KeyEvent event) {
        if (event.getIdEven() == AppConstant.SAVE_THEME_DARK) {
            changeTheme(1);
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_LIGHT) {
            changeTheme(2);
        }
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            binding.titleProfile.setTextColor(Color.WHITE);
            binding.contentProfile.setTextColor(Color.WHITE);
            binding.btnSearch.setTextColor(Color.WHITE);
            binding.textHelps.setTextColor(Color.WHITE);
            binding.btnSearch.setBackgroundResource(R.drawable.textview_border_setting_white);
            bookmarkAdapter.setColor(Color.WHITE, this.getResources().getColor(R.color.dark_282A37));
        } else {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            binding.titleProfile.setTextColor(Color.BLACK);
            binding.contentProfile.setTextColor(Color.BLACK);
            binding.btnSearch.setTextColor(Color.BLACK);
            binding.textHelps.setTextColor(Color.BLACK);
            binding.btnSearch.setBackgroundResource(R.drawable.textview_border_black);
            bookmarkAdapter.setColor(Color.BLACK, Color.WHITE);
        }
    }
}