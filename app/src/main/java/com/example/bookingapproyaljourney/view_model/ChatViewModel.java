package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private ChatRepository chatRepository;
    private LiveData<List<Content>> contentChatLiveData;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new ChatRepository();
        contentChatLiveData = chatRepository.getContentChat();
    }
    public LiveData<List<Content>> getContentChatLiveData() {
        return contentChatLiveData;
    }

}
