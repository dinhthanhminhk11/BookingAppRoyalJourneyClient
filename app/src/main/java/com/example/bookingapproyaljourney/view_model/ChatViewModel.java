package com.example.bookingapproyaljourney.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Data;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.user.User;
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
    public LiveData<List<Content>> getMsgId(String send) {
        return chatRepository.getMsgId(send);
    }
    public LiveData<List<User>> getHost(String id) {
        return chatRepository.getHost(id);
    }

    public void insertChat(Message message){
        chatRepository.insertMessage(message);
    }

}
