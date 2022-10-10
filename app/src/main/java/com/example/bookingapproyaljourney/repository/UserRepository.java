package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.user.User;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {


    private ApiRequest apiRequest;

    public UserRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getUser(String username, String password) {
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("email", username);
            paramObject.put("password", password);

            Call<UserLogin> userCall = apiRequest.getUser(paramObject.toString());
            userCall.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    if (!response.isSuccessful()) {
                        Log.d(AppConstant.TAG_ERROR, "code ; " + response.code());
                    } else {
                        Log.e(AppConstant.TAG, "Login success");
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
