package com.example.bookingapproyaljourney.ui.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.FragmentSettingBinding;
import com.example.bookingapproyaljourney.event.KeyEvent;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.activity.AddPassPinActivity;
import com.example.bookingapproyaljourney.ui.activity.ChangePasswordActivity;
import com.example.bookingapproyaljourney.ui.activity.ContactActivity;
import com.example.bookingapproyaljourney.ui.activity.LoginActivity;
import com.example.bookingapproyaljourney.ui.activity.PayCashYourActivity;
import com.example.bookingapproyaljourney.ui.bottomsheet.BottomSheetPassPayment;
import com.example.bookingapproyaljourney.ui.custom.RippleAnimation;
import com.example.bookingapproyaljourney.view_model.CashPayViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;


public class SettingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private String mParam1;
    private String mParam2;
    private BottomSheetPassPayment bottomSheetPassPayment;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isDark;
    private CashPayViewModel cashPayViewModel;
    public static final String[] languages = {"Choose Language", "Tiếng Việt", "English"};
    public int idLang = 10;
    public int idTheme = 13;
    private FragmentSettingBinding binding;
    private String checkPass;
    private int theme2;
    private SharedPreferences sharedPreferences3;
    private String token;
    private CountDownTimer countDownTimer;
    private boolean checkCountDown = true;
    private SharedPreferences.Editor editorCheckPassCash;
    private boolean checkPassCash;

    static SettingFragment newInstance(String param1, String param2) {
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

        cashPayViewModel = new ViewModelProvider(this).get(CashPayViewModel.class);

        executor = ContextCompat.getMainExecutor(getActivity());
        biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                // dùng mật khẩu
                if (checkPass.equals("")) {
                    startActivity(new Intent(getActivity(), AddPassPinActivity.class));
                } else {
                    bottomSheetPassPayment = new BottomSheetPassPayment(getActivity(), R.style.MaterialDialogSheet, new BottomSheetPassPayment.CallBack() {
                        @Override
                        public void onCLickCLose() {

                        }

                        @Override
                        public void onClickPayment(String s) {
                            if (checkPass.equals(s)) {
                                bottomSheetPassPayment.dismiss();
                                startActivity(new Intent(getActivity(), PayCashYourActivity.class));
                            } else {
                                Toast.makeText(getActivity(), "Mã pin không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    bottomSheetPassPayment.show();
                    bottomSheetPassPayment.setCanceledOnTouchOutside(false);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(getActivity(), PayCashYourActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Xác thực vân tay").setSubtitle("Uỷ quyền thông tin Sinh trắc").setNegativeButtonText("Dùng mật khẩu ví").build();

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

        Button login = (Button) dialog.findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent checkLogin = new Intent(getActivity(), LoginActivity.class);
            checkLogin.putExtra(AppConstant.CHECK_LOGIN_TOKEN_NULL, "checkNotSignIn");
            startActivity(checkLogin);
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


        binding.layoutContentPayment.setOnClickListener(v -> {
            if (token.equals("")) {
                dialog.show();
            } else if (checkPass.equals("")) {
                startActivity(new Intent(getActivity(), AddPassPinActivity.class));
            } else {
                if (checkPassCash) {
                    if (theme2 == AppConstant.POS_VANTAY) {
                        biometricPrompt.authenticate(promptInfo);
                    } else {
                        bottomSheetPassPayment = new BottomSheetPassPayment(getActivity(), R.style.MaterialDialogSheet, new BottomSheetPassPayment.CallBack() {
                            @Override
                            public void onCLickCLose() {

                            }

                            @Override
                            public void onClickPayment(String s) {
                                if (checkPass.equals(s)) {
                                    bottomSheetPassPayment.dismiss();
                                    startActivity(new Intent(getActivity(), PayCashYourActivity.class));
                                } else {
                                    Toast.makeText(getActivity(), "Mã pin không chính xác", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        bottomSheetPassPayment.show();
                        bottomSheetPassPayment.setCanceledOnTouchOutside(false);
                    }
                    editorCheckPassCash.putBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, false);
                    editorCheckPassCash.commit();
                    countDownTimer.start();
                } else {
                    startActivity(new Intent(getActivity(), PayCashYourActivity.class));
                }

            }
        });

        cashPayViewModel.getPassCashMutableLiveData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("MinhCheck", s.length() + " length");
                checkPass = s;
            }
        });
        //cứ 5 phút bắt nhập lại pass 1 lần

        countDownTimer = new CountDownTimer(300000, 1000 / 100) {
            @Override
            public void onTick(long l) {
                // call when timer start
            }

            @Override
            public void onFinish() {
                editorCheckPassCash.putBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, true);
                editorCheckPassCash.commit();
            }
        };
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

            binding.layoutContentPayment.setBackgroundResource(R.drawable.background_setting_item_dark);
            binding.iconPayment.setColorFilter(getResources().getColor(R.color.white));
            binding.titlePayment.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.contentPayment.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.iconChangePayment.setColorFilter(getResources().getColor(R.color.white));

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

            binding.layoutContentPayment.setBackgroundResource(R.drawable.background_setting_item);
            binding.iconPayment.setColorFilter(getResources().getColor(R.color.black));
            binding.titlePayment.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.contentPayment.setTextColor(getContext().getResources().getColor(R.color.black));
            binding.iconChangePayment.setColorFilter(getResources().getColor(R.color.black));

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        cashPayViewModel.getPassCash(UserClient.getInstance().getId());
        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_PASS, MODE_PRIVATE);
        theme2 = sharedPreferences2.getInt(AppConstant.SHAREDPREFERENCES_PASS, 2655);
        sharedPreferences3 = getActivity().getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER, Context.MODE_PRIVATE);
        token = sharedPreferences3.getString(AppConstant.TOKEN_USER, "");
        SharedPreferences sharedPreferencesCheckPassCash = getContext().getSharedPreferences(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, MODE_PRIVATE);
        editorCheckPassCash = sharedPreferencesCheckPassCash.edit();
        checkPassCash = sharedPreferencesCheckPassCash.getBoolean(AppConstant.SHAREDPREFERENCES_CHECK_PASS_CASH, true);
    }
}