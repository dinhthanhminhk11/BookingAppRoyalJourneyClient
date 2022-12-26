package com.example.bookingapproyaljourney.ui.activity.chat_message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
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
    private String id_boss = "";
    private ConstraintLayout contentBackground;
    private View view2;

    {
        try {
            mSocket = IO.socket(AppConstant.BASE_URL_CHAT);
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
        contentBackground = (ConstraintLayout) findViewById(R.id.contentBackground);
        view2 = (View) findViewById(R.id.view2);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolBar.setTitle("Chat Message");
        toolBar.setPadding(15, 0, 0, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        SharedPreferences sharedPreferences = getSharedPreferences(AppConstant.SHAREDPREFERENCES_USER_THEME, MODE_PRIVATE);
        int theme = sharedPreferences.getInt(AppConstant.SHAREDPREFERENCES_USER_THEME, 0);

        if (theme == AppConstant.POS_DARK) {
            changeTheme(1);
        } else {
            changeTheme(2);
        }

        mSocket.connect();
        Intent intent = getIntent();
        id_boss = intent.getStringExtra("ID_BOSS");
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        Glide.with(this).load(img_boss).transform(new CenterCrop(), new RoundedCorners(20)).into(imgBossChat);
        tvNameBossChat.setText(name_boss);
        chatViewModel.getContentChatLiveData(UserClient.getInstance().getId(), id_boss).observe(this, it -> {
            for (int i = 0; i <= it.size() - 1; i++) {
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
        imgSendChat.setOnClickListener(v -> {
            sendChat();
        });


    }

    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("message");
                listChat.add(new Content(new Text(mess), id_boss, UserClient.getInstance().getId()));
                chatAdapter.notifyDataSetChanged();
                rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
            });
        }
    };
    private final Emitter.Listener checkOnline = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("id");
                if (mess.equals(id_boss)) {
                    tvCheckOnline.setText("Online");
                    imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_online));
                } else {
                    tvCheckOnline.setText("Offline");
                    imgOnlineChat.setImageDrawable(getResources().getDrawable(R.drawable.ic_offline));
                }
            });
        }
    };

    public void sendChat() {
        if (edContentChat.getText().toString().isEmpty() || edContentChat.getText().toString().trim().equals("")) {
            edContentChat.setText("");
            return;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Gson gson = new Gson();
        MessageSocket ms = new MessageSocket(
                UserClient.getInstance().getId(),
                id_boss,
                UserClient.getInstance().getId(),
                edContentChat.getText().toString(), date);
        try {
            JSONObject jObject = new JSONObject(gson.toJson(ms));
            mSocket.emit("message", jObject);
            Message message = new Message(id_boss, UserClient.getInstance().getId(), edContentChat.getText().toString());
            chatViewModel.insertChat(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listChat.add(new Content(new Text(edContentChat.getText().toString()), UserClient.getInstance().getId(), id_boss));
        chatAdapter.notifyDataSetChanged();
        rcvChatMessage.smoothScrollToPosition(listChat.size() - 1);
        edContentChat.setText("");
    }

    @Override
    protected void onPause() {
        mSocket.disconnect();
        super.onPause();
    }

    private void changeTheme(int idTheme) {
        if (idTheme == 1) {

            toolBar.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            contentBackground.setBackgroundColor(this.getResources().getColor(R.color.dark_212332));
            toolBar.setTitleTextColor(Color.WHITE);
            toolBar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            view2.setBackgroundResource(R.drawable.background_boss_chat_drak);
            tvNameBossChat.setTextColor(Color.WHITE);
            tvCheckOnline.setTextColor(Color.WHITE);
        } else {
            tvNameBossChat.setTextColor(Color.BLACK);
            tvCheckOnline.setTextColor(this.getResources().getColor(R.color.black));
            view2.setBackgroundResource(R.drawable.background_boss_chat);
            toolBar.setBackgroundColor(Color.WHITE);
            contentBackground.setBackgroundColor(this.getResources().getColor(R.color.color_f5f5f5));
            toolBar.setTitleTextColor(Color.BLACK);
            toolBar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        }
    }
}