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
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentNotificationBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.model.noti.Notification;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.response.NotiResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.ui.activity.StatusBillActivity;
import com.example.bookingapproyaljourney.ui.adapter.NotificationAdapter;
import com.example.bookingapproyaljourney.view_model.NotificationViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private NotificationAdapter notificationAdapter;
    private NotificationViewModel notificationViewModel;
    private String idOrder;

    public NotificationFragment() {
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        notificationAdapter = new NotificationAdapter();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        notificationViewModel.getListNotification(UserClient.getInstance().getId());

        binding.reLoad.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationViewModel.getListNotification(UserClient.getInstance().getId());
                binding.reLoad.setRefreshing(false);
            }
        });

        notificationViewModel.getNotiResponseMutableLiveData().observe(getActivity(), new Observer<NotiResponse>() {
            @Override
            public void onChanged(NotiResponse notiResponse) {
                if (notiResponse.getData().size() > 0) {
                    binding.contentNullList.setVisibility(View.GONE);
                } else {
                    binding.contentNullList.setVisibility(View.VISIBLE);
                }
                notificationAdapter.setData(notiResponse.getData());
                notificationAdapter.setCallback(new NotificationAdapter.Callback() {
                    @Override
                    public void onClick(Notification notification) {
                        idOrder = notification.getIdOder();
                        if (notification.isSeem()) {
                            notificationViewModel.updateNotiSeen(notification.getId());
                        } else {
                            Intent intent = new Intent(getContext(), StatusBillActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(AppConstant.ID_ORDER, idOrder);
                            startActivity(intent);
                        }
                    }
                });
                binding.recyclerView.setAdapter(notificationAdapter);
            }
        });

        notificationViewModel.getmProgressMutableData().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        });

        notificationViewModel.getTestResponseMutableLiveData().observe(getActivity(), new Observer<TestResponse>() {
            @Override
            public void onChanged(TestResponse testResponse) {
                if (testResponse.isStatus()) {
                    EventBus.getDefault().postSticky(new KeyEvent(AppConstant.CHECK_EVENT_CLICK_NOTIFICATION));
                    Intent intent = new Intent(getContext(), StatusBillActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(AppConstant.ID_ORDER, idOrder);
                    startActivity(intent);
                }
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
        if (event.getIdEven() == AppConstant.CHECK_EVENT_CLICK_NOTIFICATION) {
            notificationViewModel.getListNotification(UserClient.getInstance().getId());
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_DARK) {
            changeTheme(1);
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_LIGHT) {
            changeTheme(2);
        }
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            binding.titleText.setTextColor(Color.WHITE);
            binding.contentProfile.setTextColor(Color.WHITE);
            notificationAdapter.setColor(Color.WHITE ,R.drawable.background_not_seem_dark , R.drawable.background_seem_dark );
//            bookmarkAdapter.setColor(Color.WHITE, this.getResources().getColor(R.color.dark_282A37));
        } else {
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            binding.titleText.setTextColor(Color.BLACK);
            binding.contentProfile.setTextColor(Color.BLACK);
            notificationAdapter.setColor(Color.BLACK ,R.drawable.background_not_seem , R.drawable.background_seem );
//            bookmarkAdapter.setColor(Color.BLACK, Color.WHITE);
        }
    }
}