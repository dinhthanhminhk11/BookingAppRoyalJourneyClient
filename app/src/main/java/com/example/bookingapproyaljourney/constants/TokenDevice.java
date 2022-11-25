package com.example.bookingapproyaljourney.constants;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class TokenDevice {
    private static String token;
    private static TokenDevice instance;

    private TokenDevice() {
    }

    public static TokenDevice getInstance() {
        if (instance == null) {
            instance = new TokenDevice();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static void setInstance(TokenDevice instance) {
        TokenDevice.instance = instance;
    }


}
