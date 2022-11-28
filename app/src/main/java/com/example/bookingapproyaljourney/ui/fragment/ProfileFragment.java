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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.house.Convenient;
import com.example.bookingapproyaljourney.model.house.House;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.ui.activity.EditProfile;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.SeeMoreBestForYouActivity;
import com.example.bookingapproyaljourney.ui.adapter.HiredProfileAdapter;
import com.example.bookingapproyaljourney.view_model.LoginViewModel;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private AppCompatButton login;
    private LoginViewModel loginViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TextView tvSignUpProfile, nameUser, emailUser;
    private ImageView imageProfile, editProfile;
    private LinearLayout profileVisialbe;
    private CoordinatorLayout profileGone;
    private RecyclerView recyclerViewHiredProfile;
    private HiredProfileAdapter hiredProfileAdapter;
    private ArrayList<House> listHouse;
    private List<Convenient> convenientList;

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
        login = (AppCompatButton) view.findViewById(R.id.login);
        tvSignUpProfile = view.findViewById(R.id.tvSignUpProfile);
        nameUser = view.findViewById(R.id.tvNameUserProfile);
        emailUser = view.findViewById(R.id.tvEmailUserProfile);
        tvSignUpProfile.setPaintFlags(tvSignUpProfile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        profileGone = view.findViewById(R.id.profileGone);
        profileVisialbe = view.findViewById(R.id.profileVisiable);
        imageProfile = view.findViewById(R.id.imageProfile);
        recyclerViewHiredProfile = view.findViewById(R.id.recycleView_profile);
        editProfile = view.findViewById(R.id.editProfile);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        editProfile.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), EditProfile.class);
            startActivity(i);
        });
    }

    private void initData() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(AppConstant.TOKEN_USER, "");
        Log.e("MinhToken", token + " text");

        if (token == null || token.equals("")) {
            login.setVisibility(View.VISIBLE);
            profileVisialbe.setVisibility(View.VISIBLE);
            profileGone.setVisibility(View.GONE);

        } else {
            login.setVisibility(View.GONE);
            profileVisialbe.setVisibility(View.GONE);
            profileGone.setVisibility(View.VISIBLE);
        }
        if (token != null || !token.equals("")) {
            loginViewModel.getUserByToken(token);
        }
        loginViewModel.getLoginResultMutableDataToKen().observe(getActivity(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse s) {
                nameUser.setText(s.getUser().getName());
                emailUser.setText(s.getUser().getEmail());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.img)
                        .error(R.drawable.img);
                Glide.with(getContext()).load(s.getUser().getImage()).apply(options).into(imageProfile);
            }
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

    }

    private void FakeData() {
        listHouse = new ArrayList<>();
        hiredProfileAdapter = new HiredProfileAdapter(listHouse);
        recyclerViewHiredProfile.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewHiredProfile.setAdapter(hiredProfileAdapter);
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