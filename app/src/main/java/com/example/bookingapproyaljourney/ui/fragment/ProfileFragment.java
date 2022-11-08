package com.example.bookingapproyaljourney.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private AppCompatButton login;
    private TextView checktest;
    private LoginViewModel loginViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView tvSignUpProfile, content;
    private ImageView imageProfile;

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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        checktest = (TextView) view.findViewById(R.id.checktest);
        login = (AppCompatButton) view.findViewById(R.id.login);
        tvSignUpProfile = view.findViewById(R.id.tvSignUpProfile);
        tvSignUpProfile.setPaintFlags(tvSignUpProfile.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        imageProfile = view.findViewById(R.id.imageProfile);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        Log.e("MinhToken", token + " text");

        if (token == null || token.equals("")) {
            login.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.GONE);
        }
        if (token != null || !token.equals("")) {
            loginViewModel.getUserByToken(token);
        }
        loginViewModel.getLoginResultMutableDataToKen().observe(getActivity(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {
                checktest.setText(s.getUser().getName());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img);
                Glide.with(getActivity()).load(s.getUser().getImage()).apply(options).into(imageProfile);

            }
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}