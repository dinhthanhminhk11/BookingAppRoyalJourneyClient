package com.example.bookingapproyaljourney.ui.activity.chat_message;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.model.chat.Content;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.chat.MessageSocket;
import com.example.bookingapproyaljourney.model.chat.Text;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.example.bookingapproyaljourney.ui.adapter.ChatAdapter;
import com.example.bookingapproyaljourney.view_model.ChatViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatMessageActivity extends AppCompatActivity {
    private Socket mSocket;
    private MaterialToolbar toolBar;
    private ImageView imgBossChat;
    private TextView tvNameBossChat;
    private ImageView imgOnlineChat;
    private TextView tvCheckOnline;
    private RecyclerView rcvChatMessage;
    private EditText edContentChat;
    private ImageView imgSendChat;
    private ChatViewModel chatViewModel;
    private List<Content> listChat = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private String id_boss ="";

    {
        try {
            mSocket = IO.socket("https://d43d-113-160-5-74.ap.ngrok.io");
        } catch (URISyntaxException e) {
            e.getMessage();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        toolBar = (MaterialToolbar) findViewById(R.id.include);
        imgBossChat = (ImageView) findViewById(R.id.imgBossChat);
        tvNameBossChat = (TextView) findViewById(R.id.tvNameBossChat);
        imgOnlineChat = (ImageView) findViewById(R.id.imgOnlineChat);
        tvCheckOnline = (TextView) findViewById(R.id.tvCheckOnline);
        rcvChatMessage = (RecyclerView) findViewById(R.id.rcvChatMessage);
        edContentChat = (EditText) findViewById(R.id.edContentChat);
        imgSendChat = (ImageView) findViewById(R.id.imgSendChat);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setTitle("Chat Message");
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        mSocket.connect();
        Intent intent = getIntent();
        id_boss = intent.getStringExtra("ID_BOSS");
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        Glide.with(this).load(img_boss).transform(new CenterCrop(), new RoundedCorners(20)).into(imgBossChat);
        tvNameBossChat.setText(name_boss);
        chatViewModel.getContentChatLiveData(UserClient.getInstance().getId(), id_boss).observe(this, it -> {
            for (int i=0; i <= it.size()-1; i++){
                listChat.add(it.get(i));
            }
            chatAdapter = new ChatAdapter(listChat);
            rcvChatMessage.setAdapter(chatAdapter);
            rcvChatMessage.setLayoutManager(new LinearLayoutManager(ChatMessageActivity.this));
            rcvChatMessage.smoothScrollToPosition(listChat.size());
        });

        mSocket.on("new message", onNewMessage);
        mSocket.emit("join", UserClient.getInstance().getId());
        mSocket.on("join", checkOnline);
        imgSendChat.setOnClickListener(v ->{sendChat();});


    }

    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("message");
                listChat.add(new Content(new Text(mess),id_boss,UserClient.getInstance().getId()));
                chatAdapter.notifyDataSetChanged();
                rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
            });
        }
    };
    private final Emitter.Listener checkOnline= new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("id");
                if(mess.equals(id_boss)){
                    tvCheckOnline.setText("Online");
                    imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
                }else {
                    tvCheckOnline.setText("Offline");
                    imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
                }
            });
        }
    };

    public void sendChat(){
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Gson gson = new Gson();
        MessageSocket ms = new MessageSocket(
                UserClient.getInstance().getId(),
                id_boss,
                UserClient.getInstance().getId(),
                edContentChat.getText().toString(),date);
        try {
            JSONObject jObject = new JSONObject(gson.toJson(ms));
            mSocket.emit("message", jObject);
            Message message = new Message(id_boss,UserClient.getInstance().getId(),edContentChat.getText().toString());
            chatViewModel.insertChat(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listChat.add(new Content(new Text(edContentChat.getText().toString()),UserClient.getInstance().getId(),id_boss));
        chatAdapter.notifyDataSetChanged();
        rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
        edContentChat.setText("");
    }

    @Override
    protected void onPause() {
        mSocket.disconnect();
        super.onPause();
    }
}