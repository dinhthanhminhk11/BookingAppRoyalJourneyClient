package com.example.bookingapproyaljourney.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.bookingapproyaljourney.MainActivity;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.databinding.ActivityFeedBackBinding;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.Toast.ToastCheck;
import com.example.bookingapproyaljourney.view_model.FeedbackViewModel;

import java.util.Calendar;

public class FeedBackActivity extends AppCompatActivity {
    private ActivityFeedBackBinding binding;
    private int sao = 0;
    private String id_boss = "";
    private FeedbackViewModel feedbackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        Intent intent = getIntent();
        id_boss = intent.getStringExtra("ID_BOSS");
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        String id_House = intent.getStringExtra("ID_HOUSE");
        Glide.with(this).load(img_boss).into(binding.imgBoss);
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
            FeedBack feedBack = new FeedBack(id_House, UserClient.getInstance().getId(),UserClient.getInstance().getImage(), UserClient.getInstance().getName(),sao, Long.toString(time), text, "");
            if(sao == 0){
                new ToastCheck(this, R.style.StyleToast, "Bạn cho chúng tôi bao nhiêu sao", this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
            }else if(text.isEmpty() || text.trim().equals("")){
                new ToastCheck(this, R.style.StyleToast, "Hãy đóng góp ý kiến của bạn", this.getString(R.string.dialogcontentnomal), R.drawable.ic_warning_icon_check);
            }else {
                feedbackViewModel.insertFeedback(feedBack);
                Intent intent1 = new Intent(this, DetailProductActivity.class);
                intent1.putExtra(AppConstant.HOUSE_EXTRA, id_House);
                intent1.putExtra("CHECK_FEEDBACK",true);
                startActivity(intent1);
            }
        });
    }
}