package com.example.bookingapproyaljourney.ui.activity.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.bookingapproyaljourney.databinding.ActivityFeedbackListBinding;
import com.example.bookingapproyaljourney.ui.adapter.FeedbackListAdapter;
import com.example.bookingapproyaljourney.view_model.FeedbackViewModel;

import java.text.DecimalFormat;

public class FeedbackListActivity extends AppCompatActivity {
    private ActivityFeedbackListBinding binding;
    private FeedbackViewModel feedbackViewModel;
    private String id_boss = "";
    private String name_boss = "";
    private String img_boss = "";
    private String id_House = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        Intent intent = getIntent();
        id_boss = intent.getStringExtra("ID_BOSS");
        name_boss = intent.getStringExtra("NAME_BOSS");
        img_boss = intent.getStringExtra("IMG_BOSS");
        id_House = intent.getStringExtra("ID_HOUSE");
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.rcvListFeedback.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvListFeedback.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        feedbackViewModel.getFeedbackId(id_House).observe(this, it ->{
            Float total = 0f;
            if(it.size() == 0){
                binding.progressBar.setAlpha(0f);
                binding.tvDetail.setText("Chưa có đánh giá nào");
            }else {
                binding.progressBar.setAlpha(0f);
                binding.rcvListFeedback.setAdapter(new FeedbackListAdapter(it));
                for (int i = 0; i < it.size(); i++) {
                    total = total + it.get(i).getSao();
                }
                float average = total / it.size();
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                if (average % 1 == 0) {
                    binding.tvDetail.setText(decimalFormat.format(average) + ",0 . "+it.size()+" Đánh giá");
                } else {
                    binding.tvDetail.setText(decimalFormat.format(average) + " . "+it.size()+" Đánh giá");
                }
            }
        });

        binding.btnFeedback.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, FeedBackActivity.class);
            intent1.putExtra("ID_BOSS", id_boss);
            intent1.putExtra("ID_HOUSE", id_House);
            intent1.putExtra("IMG_BOSS", img_boss);
            intent1.putExtra("NAME_BOSS", name_boss);
            startActivity(intent1);
        });
        binding.imgBack.setOnClickListener(v -> onBackPressed());
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.progressBar.setAlpha(1f);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                feedbackViewModel.getFeedbackTk(id_House,charSequence.toString()).observe(FeedbackListActivity.this,v->{
                    binding.rcvListFeedback.setAdapter(new FeedbackListAdapter(v));
                    binding.progressBar.setAlpha(0f);
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.progressBar.setAlpha(0f);
            }
        });
    }
}