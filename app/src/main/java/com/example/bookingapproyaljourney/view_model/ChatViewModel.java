package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private ChatRepository chatRepository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatRepository = new ChatRepository();
    }

    public LiveData<List<Content>> getContentChatLiveData(String sendId, String sendToId) {
        return chatRepository.getContentChat(sendId,sendToId);
    }

    public void insertChat(Message message){
        chatRepository.insertMessage(message);
    }
}
