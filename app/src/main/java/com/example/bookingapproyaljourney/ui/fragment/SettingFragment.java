package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentSettingBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.ui.activity.ChangePasswordActivity;
import com.example.bookingapproyaljourney.ui.activity.ContactActivity;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.PayCashYourActivity;
import com.example.bookingapproyaljourney.ui.custom.RippleAnimation;

import org.greenrobot.eventbus.EventBus;


public class SettingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isDark;

    public static final String[] languages = {"Choose Language", "Tiếng Việt", "English"};
    public int idLang = 10;
    public int idTheme = 13;
    private FragmentSettingBinding binding;


    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.getView().setBackgroundColor(android.R.attr.colorBackground);
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
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intView();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerLanguage.setAdapter(adapter);
        binding.spinnerLanguage.setSelection(0);
        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String select = adapterView.getItemAtPosition(i).toString();
                if (select.equals("English")) {
                    idLang = 11;
                    EventBus.getDefault().postSticky(new KeyEvent(idLang));
                    MainActivity.instance.RestartMain();
                } else if (select.equals("Tiếng Việt")) {
                    idLang = 10;
                    EventBus.getDefault().postSticky(new KeyEvent(idLang));
                    MainActivity.instance.RestartMain();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void intView() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            binding.switchComparTheme.setChecked(true);
        } else {
            binding.switchComparTheme.setChecked(false);
        }

        if (binding.switchComparTheme.isChecked()) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.switchComparTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.switchComparTheme.isChecked()) {
//                    try {
//                        int[] pos = (int[]) args[2];
//                        int w = binding.contentBackground.getMeasuredWidth();
//                        int h = binding.contentBackground.getMeasuredHeight();
//                        Bitmap bitmap = Bitmap.createBitmap(binding.contentBackground.getMeasuredWidth(), binding.contentBackground.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//                        Canvas canvas = new Canvas(bitmap);
//                        binding.contentBackground.draw(canvas);
////                        themeSwitchImageView.setImageBitmap(bitmap);
////                        themeSwitchImageView.setVisibility(View.VISIBLE);
//                        float finalRadius = (float) Math.max(Math.sqrt((w - pos[0]) * (w - pos[0]) + (h - pos[1]) * (h - pos[1])), Math.sqrt(pos[0] * pos[0] + (h - pos[1]) * (h - pos[1])));
//                        Animator anim = ViewAnimationUtils.createCircularReveal(binding.contentBackground, pos[0], pos[1], 0, finalRadius);
//                        anim.setDuration(400);
////                        anim.setInterpolator(CubicBezierInterpolator.EASE_IN_OUT_QUAD);
//                        anim.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
////                                themeSwitchImageView.setImageDrawable(null);
////                                themeSwitchImageView.setVisibility(View.GONE);
//                            }
//                        });
//                        anim.start();
////                        instant = true;
//                    } catch (Throwable ignore) {
//
//                    }
                    editor.putInt(AppConstant.SHAREDPREFERENCES_USER_THEME, AppConstant.POS_DARK);
                    editor.commit();
                    RippleAnimation.create(view).setDuration(500).start();
                    changeTheme(1);
                } else {
                    editor.putInt(AppConstant.SHAREDPREFERENCES_USER_THEME, AppConstant.POS_LIGHT);
                    editor.commit();
                    RippleAnimation.create(view).setDuration(500).start();
                    changeTheme(2);
                }
            }
        });

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dia_log_comfirm_logout);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        String token = sharedPreferences3.getString(AppConstant.TOKEN_USER, "");
        Button login = (Button) dialog.findViewById(R.id.login);
        login.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            dialog.dismiss();
        });

        binding.contentChangePass.setOnClickListener(v -> {
            if (token.equals("")) {
                dialog.show();
            } else {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        binding.contentByRoyal.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ContactActivity.class));
        });

        binding.contentPayment.setOnClickListener(v -> {
            if (token.equals("")) {
                dialog.show();
            } else {
                startActivity(new Intent(getActivity(), PayCashYourActivity.class));
            }
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            EventBus.getDefault().postSticky(new KeyEvent(AppConstant.SAVE_THEME_DARK));

            binding.contentTheme.setText(R.string.Dark);
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.dark_212332));
            binding.contentItemChildTheme.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.contentItemChildLang.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.contentChangePass.setBackgroundResource(R.drawable.background_setting_item_dark);

            binding.titleLang.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.titleTheme.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.contentLang.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.contentTheme.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.titleChangePass.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.contentChangePassText.setTextColor(getContext().getResources().getColor(R.color.white));

            binding.iconChangePassLast.setColorFilter(getResources().getColor(R.color.white));
            binding.iconChangepass.setColorFilter(getResources().getColor(R.color.white));
            binding.iconLang.setColorFilter(getResources().getColor(R.color.white));
            binding.iconTheme.setImageResource(R.drawable.ic_group_sun_setting_white);

            binding.iconTheme.setBackgroundResource(R.drawable.textview_border_setting_white);
            binding.iconLang.setBackgroundResource(R.drawable.textview_border_setting_white);

            binding.contentByRoyal.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.titleRoyal.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.contentRoyal.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.iconRoyal.setColorFilter(getResources().getColor(R.color.white));
            binding.iconChangePassLast2.setColorFilter(getResources().getColor(R.color.white));
        } else {
            EventBus.getDefault().postSticky(new KeyEvent(AppConstant.SAVE_THEME_LIGHT));

            binding.contentTheme.setText(R.string.Light);
            binding.contentBackground.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            binding.contentItemChildTheme.setBackgroundResource(R.drawable.background_setting_item);
            binding.contentItemChildLang.setBackgroundResource(R.drawable.background_setting_item);
            binding.contentChangePass.setBackgroundResource(R.drawable.background_setting_item);

            binding.titleLang.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.titleTheme.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.contentLang.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.contentTheme.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.titleChangePass.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.contentChangePassText.setTextColor(getContext().getResources().getColor(R.color.black));

            binding.iconChangePassLast.setColorFilter(getResources().getColor(R.color.black));
            binding.iconChangepass.setColorFilter(getResources().getColor(R.color.black));
            binding.iconLang.setColorFilter(getResources().getColor(R.color.black));
            binding.iconTheme.setImageResource(R.drawable.ic_group_moon_setting);

            binding.iconTheme.setBackgroundResource(R.drawable.textview_border_setting_xam);
            binding.iconLang.setBackgroundResource(R.drawable.textview_border_setting_xam);

            binding.contentByRoyal.setBackgroundResource(R.drawable.background_setting_item);
            binding.titleRoyal.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.contentRoyal.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.iconRoyal.setColorFilter(getResources().getColor(R.color.black));
            binding.iconChangePassLast2.setColorFilter(getResources().getColor(R.color.black));
        }
    }
}