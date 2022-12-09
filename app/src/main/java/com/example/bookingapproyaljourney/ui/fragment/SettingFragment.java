package com.example.bookingapproyaljourney.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentSettingBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;

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

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.switchComparTheme.setChecked(true);
        } else {
            binding.switchComparTheme.setChecked(false);
        }

        binding.switchComparTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.switchComparTheme.isChecked()) {
                    EventBus.getDefault().postSticky(new KeyEvent(AppConstant.SAVE_THEME_DARK));
                    changeTheme(1);
                } else {
                    EventBus.getDefault().postSticky(new KeyEvent(AppConstant.SAVE_THEME_LIGHT));
                    changeTheme(2);
                }
            }
        });
//
//        imgChangePass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, languages);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLanguage.setAdapter(adapter);
//        spinnerLanguage.setSelection(0);
//        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                String select = adapterView.getItemAtPosition(i).toString();
//                if (select.equals("English")) {
//                    idLang = 11;
//                    EventBus.getDefault().postSticky(new KeyEvent(idLang));
//                    MainActivity.instance.RestartMain();
//                } else if (select.equals("Tiếng Việt")) {
//                    idLang = 10;
//                    EventBus.getDefault().postSticky(new KeyEvent(idLang));
//                    MainActivity.instance.RestartMain();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentTheme.setText("Dark");
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
        } else {
            binding.contentTheme.setText("Light");
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
        }
    }
}