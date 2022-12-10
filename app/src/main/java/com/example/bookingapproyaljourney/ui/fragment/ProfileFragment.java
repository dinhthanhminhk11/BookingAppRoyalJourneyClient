package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentProfileBinding;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser2;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.activity.EditProfileActivity;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.RegisterActivity;
import com.example.bookingapproyaljourney.ui.adapter.HiredProfileAdapter;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private LoginViewModel loginViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private HiredProfileAdapter hiredProfileAdapter;
    private ArrayList<House> listHouse;
    private List<Convenient> convenientList;
    private LottieAnimationView progressBar;

    private FragmentProfileBinding binding;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        progressBar = (LottieAnimationView) view.findViewById(R.id.progressBar);

        hiredProfileAdapter = new HiredProfileAdapter();

        binding.tvSignUpProfile.setPaintFlags(binding.tvSignUpProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.editProfile.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(i);
        });

        binding.tvSignUpProfile.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), RegisterActivity.class));
        });

        SharedPreferences sharedPreferencesTheme = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

    }

    private void initData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        Log.e("MinhToken", token + " text");

        if (token == null || token.equals("")) {
            binding.login.setVisibility(View.VISIBLE);
            binding.profileVisiable.setVisibility(View.VISIBLE);
            binding.profileGone.setVisibility(View.GONE);

        } else {
            binding.login.setVisibility(View.GONE);
            binding.profileVisiable.setVisibility(View.GONE);
            binding.profileGone.setVisibility(View.VISIBLE);
        }
        if (token != null || !token.equals("")) {
            loginViewModel.getUserByToken(token);
        }

        loginViewModel.getLoginResultMutableDataToKen().observe(getActivity(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {
                loginViewModel.getListOrderAccessById(s.getUser().getId());
                binding.tvNameUserProfile.setText(s.getUser().getName());
                binding.tvEmailUserProfile.setText(s.getUser().getEmail());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.soap)
                        .error(R.drawable.soap);
                Glide.with(getContext()).load(s.getUser().getImage()).apply(options).into(binding.imageProfile);
            }
        });

        loginViewModel.getListOrderByIdUserMutableLiveData().observe(this, new Observer<ListOrderByIdUser2>() {
            @Override
            public void onChanged(ListOrderByIdUser2 listOrderByIdUser) {
                hiredProfileAdapter.setDataHouse(listOrderByIdUser.getData());
                hiredProfileAdapter.setListernaer(new HiredProfileAdapter.Listernaer() {
                    @Override
                    public void onClickListChinh(HouseDetailResponse houseDetailResponse) {
                        Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                        intent.putExtra(AppConstant.HOUSE_EXTRA, houseDetailResponse.get_id());
                        startActivity(intent);
                    }
                });
                binding.recycleViewProfile.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                binding.recycleViewProfile.setAdapter(hiredProfileAdapter);
            }
        });

        binding.login.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        loginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                progressBar.setVisibility(integer);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(com.example.bookingapproyaljourney.event.KeyEvent event) {
        if (event.getIdEven() == AppConstant.SAVE_THEME_DARK) {
            changeTheme(1);
        } else if (event.getIdEven() == AppConstant.SAVE_THEME_LIGHT) {
            changeTheme(2);
        }
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

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.titleText.setTextColor(Color.WHITE);
            binding.contentText.setTextColor(Color.WHITE);
            binding.textRegister.setTextColor(Color.WHITE);
            binding.tvSignUpProfile.setTextColor(Color.WHITE);

            binding.tvNameUserProfile.setTextColor(Color.WHITE);
            binding.tvEmailUserProfile.setTextColor(Color.WHITE);
            binding.textThue.setTextColor(Color.WHITE);

            binding.profileGone.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            binding.bottomSheetProfile.setBackgroundResource(R.drawable.background_card_profile);

            hiredProfileAdapter.setColor(Color.WHITE, Color.WHITE);
        } else {
            binding.titleText.setTextColor(Color.BLACK);
            binding.contentText.setTextColor(Color.BLACK);
            binding.textRegister.setTextColor(Color.BLACK);
            binding.tvSignUpProfile.setTextColor(Color.BLACK);

            binding.tvNameUserProfile.setTextColor(Color.BLACK);
            binding.tvEmailUserProfile.setTextColor(Color.BLACK);
            binding.textThue.setTextColor(Color.BLACK);

            binding.profileGone.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            binding.bottomSheetProfile.setBackgroundResource(R.drawable.background_card_profile_white);

            hiredProfileAdapter.setColor(getContext().getResources().getColor(R.color.color_858585), Color.BLACK);
        }
    }
}