package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookingapproyaljourney.callback.InterfaceResponseFeedBack;
import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.bookingapproyaljourney.model.feedback.ListIdUser;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.repository.ChatRepository;
import com.example.bookingapproyaljourney.repository.FeedBackRepository;

import java.util.List;

public class FeedbackViewModel extends AndroidViewModel {
    private FeedBackRepository feedBackRepository;

    public FeedbackViewModel(@NonNull Application application) {
        super(application);
        feedBackRepository = new FeedBackRepository();
    }

    public void insertFeedback(FeedBack feedBack){
        feedBackRepository.insertFeedback(feedBack, new InterfaceResponseFeedBack() {
            @Override
            public void onResponse(FeedBack feedBack) {

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("zzzzzzzzzzzzz", t.getMessage() );
            }
        });
    }

    public LiveData<List<FeedBack>> getFeedbackId(String idHouse){
        return feedBackRepository.getFeedbackId(idHouse);
    }
    public LiveData<List<ListIdUser>> getListId(String idHouse){
        return feedBackRepository.getListIdUser(idHouse);
    }
    public void updateFeedback(FeedBack feedBack){
        feedBackRepository.updateFeedback(feedBack);
    }

}
