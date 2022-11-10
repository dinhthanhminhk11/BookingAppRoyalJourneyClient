package com.example.bookingapproyaljourney.ui.activity.chat_message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.bookingapproyaljourney.R;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.chat.MessageSocket;
import com.example.bookingapproyaljourney.model.user.UserClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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


    {
        try {
            mSocket = IO.socket("http://192.168.0.108:8080");
        } catch (URISyntaxException e) {
            e.getMessage();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar);
        imgBossChat = (ImageView) findViewById(R.id.imgBossChat);
        tvNameBossChat = (TextView) findViewById(R.id.tvNameBossChat);
        imgOnlineChat = (ImageView) findViewById(R.id.imgOnlineChat);
        tvCheckOnline = (TextView) findViewById(R.id.tvCheckOnline);
        rcvChatMessage = (RecyclerView) findViewById(R.id.rcvChatMessage);
        edContentChat = (EditText) findViewById(R.id.edContentChat);
        imgSendChat = (ImageView) findViewById(R.id.imgSendChat);
        mSocket.connect();
        Intent intent = getIntent();
        String id_boss = intent.getStringExtra("ID_BOSS");
        String name_boss = intent.getStringExtra("NAME_BOSS");
        String img_boss = intent.getStringExtra("IMG_BOSS");
        Glide.with(this).load(img_boss).transform(new CenterCrop(), new RoundedCorners(20)).into(imgBossChat);
        tvNameBossChat.setText(name_boss);
        mSocket.on("new message", onNewMessage);
        mSocket.emit("join", UserClient.getInstance().getId());
        imgSendChat.setOnClickListener(v ->{
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
            edContentChat.setText("");
        });

    }

    private final Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String mess = data.optString("message");
            });
        }
    };

    @Override
    protected void onPause() {
        mSocket.disconnect();
        super.onPause();
    }
}