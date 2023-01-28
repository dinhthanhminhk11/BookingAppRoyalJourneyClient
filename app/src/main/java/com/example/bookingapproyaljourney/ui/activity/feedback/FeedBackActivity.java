package com.example.bookingapproyaljourney.ui.activity.feedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.base.BaseActivity;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityFeedBackBinding;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.ui.activity.DetailProductActivity;
import com.example.bookingapproyaljourney.ui.activity.Hotel.HotelActivity;
import com.example.bookingapproyaljourney.ui.activity.StatusBillActivity;
import com.example.bookingapproyaljourney.view_model.FeedbackViewModel;

import java.util.Calendar;

public class FeedBackActivity extends BaseActivity {
    private ActivityFeedBackBinding binding;
    private int sao = 0;
    private Boolean check = false;
    private FeedbackViewModel feedbackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        Intent intent = getIntent();
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        String id_House = intent.getStringExtra("ID_HOUSE");
        Glide.with(this).load(img_boss).into(binding.imgBoss);

        SharedPreferences sharedPreferencesTheme = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferencesTheme.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        binding.tvNameBoss.setText(name_boss);
        binding.imgBackFB.setOnClickListener(v -> onBackPressed());
        binding.imgStar1.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 1;
        });
        binding.imgStar2.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 2;
        });
        binding.imgStar3.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_no_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 3;
        });
        binding.imgStar4.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_no_click);
            sao = 4;
        });
        binding.imgStar5.setOnClickListener(v -> {
            binding.imgStar1.setImageResource(R.drawable.ic_star_click);
            binding.imgStar2.setImageResource(R.drawable.ic_star_click);
            binding.imgStar3.setImageResource(R.drawable.ic_star_click);
            binding.imgStar4.setImageResource(R.drawable.ic_star_click);
            binding.imgStar5.setImageResource(R.drawable.ic_star_click);
            sao = 5;
        });
        Calendar calendar = Calendar.getInstance();
        long time = calendar.getTimeInMillis();
        binding.btnSubmit.setOnClickListener(v -> {
            String text = binding.edComment.getText().toString();
            if (sao == 0) {
                new ToastCheck(this, R.style.StyleToast, "Bạn cho chúng tôi bao nhiêu sao", this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
                return;
            }if (text.isEmpty() || text.trim().equals("")) {
                new ToastCheck(this, R.style.StyleToast, "Hãy đóng góp ý kiến của bạn", this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
                return;
            }
            FeedBack feedBack = new FeedBack(id_House, UserClient.getInstance().getId(), UserClient.getInstance().getImage(), UserClient.getInstance().getName(),
                    UserClient.getInstance().getEmail(), sao, Long.toString(time), text, "");
            feedbackViewModel.getListId(id_House).observe(this, it -> {
                for (int i = 0; i < it.size(); i++) {
                    if (UserClient.getInstance().getId().equals(it.get(i).getIdUser())) {
                        check = true;
                    }
                }
                if (check) {
                    feedbackViewModel.updateFeedback(feedBack);
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra(AppConstant.CheckSuccess, "feedback");
                    startActivity(intent1);
                } else {
                    feedbackViewModel.insertFeedback(feedBack);
                    Intent intent1 = new Intent(this, MainActivity.class);
                    intent1.putExtra(AppConstant.CheckSuccess, "feedback");
                    startActivity(intent1);
                }
            });
        });
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            binding.imgBackFB.setColorFilter(getResources().getColor(R.color.white));
            binding.textView2.setTextColor(Color.WHITE);
            binding.textView7.setTextColor(Color.WHITE);
            binding.tvNameBoss.setTextColor(Color.WHITE);
            binding.textView8.setTextColor(Color.WHITE);

            binding.view5.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));
            binding.view5.setBackgroundColor(this.getResources().getColor(R.color.dark_282A37));

            binding.view5.setBackgroundResource(R.drawable.bg_chat_boss_dark);
        } else {
            binding.contentBackground.setBackgroundColor(this.getResources().getColor(R.color.color_EBEBEB));
        }
    }
}