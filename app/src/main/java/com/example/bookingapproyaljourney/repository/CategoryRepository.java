package com.example.bookingapproyaljourney.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CategoryCallBack;
import com.example.bookingapproyaljourney.callback.HouseByCategoryCallback;
import com.example.bookingapproyaljourney.callback.InterfaceResponseHouseNearestByUser;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private ApiRequest apiRequest;

    public CategoryRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void getListAllHotel(Consumer<HotelReponse> consumer) {
        apiRequest.getAllListHotel().enqueue(new Callback<HotelReponse>() {
            @Override
            public void onResponse(Call<HotelReponse> call, Response<HotelReponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelReponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }


    public MutableLiveData<List<Category>> getCategory() {
        final MutableLiveData<List<Category>> data = new MutableLiveData<>();
        apiRequest.getCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    Log.d(AppConstant.TAG, "Category total result:: " + response.body().get(0).getName());
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }

        });
        return data;
    }

    public void getCategoryById(String id, CategoryCallBack categoryCallBack) {
        apiRequest.getNameCategoryById(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG, "code ; " + response.code());
                } else {
                    categoryCallBack.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                categoryCallBack.failure(t);
            }
        });
    }

    public void getHouseByCategory(String id, HouseByCategoryCallback houseByCategoryCallback) {
        apiRequest.getHouseByCategory(id).enqueue(new Callback<CategoryBestForYouResponse>() {
            @Override
            public void onResponse(Call<CategoryBestForYouResponse> call, Response<CategoryBestForYouResponse> response) {
                if (!response.isSuccessful()) {
                    Log.e(AppConstant.TAG, " error");
                } else {
                    houseByCategoryCallback.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<CategoryBestForYouResponse> call, Throwable t) {
                houseByCategoryCallback.failure(t);
            }
        });
    }

    public void getHouseNearestByUserAndLocationUser(HouseNearestByUser houseNearestByUser, InterfaceResponseHouseNearestByUser interfaceResponseHouseNearestByUser) {
        Call<HouseNearestByUserResponse> houseNearestByUserResponseCall = apiRequest.getHouseNearFromYou(houseNearestByUser);
        houseNearestByUserResponseCall.enqueue(new Callback<HouseNearestByUserResponse>() {
            @Override
            public void onResponse(Call<HouseNearestByUserResponse> call, Response<HouseNearestByUserResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseHouseNearestByUser.onResponse(response.body());
                    Log.e(AppConstant.TAGZZZZZZZZ, "dataa  " + response.body().getDataMaps().size());
                } else {
                    interfaceResponseHouseNearestByUser.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<HouseNearestByUserResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }

    public void getHouseNearestByUserAndLocationUserAndCategory(HouseNearestByUser houseNearestByUser, InterfaceResponseHouseNearestByUser interfaceResponseHouseNearestByUser) {
        Call<HouseNearestByUserResponse> houseNearestByUserResponseCall = apiRequest.getHouseNearFromYou(houseNearestByUser);
        houseNearestByUserResponseCall.enqueue(new Callback<HouseNearestByUserResponse>() {
            @Override
            public void onResponse(Call<HouseNearestByUserResponse> call, Response<HouseNearestByUserResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseHouseNearestByUser.onResponse(response.body());
                    Log.e(AppConstant.TAGZZZZZZZZ, "dataa  " + response.body().getDataMaps().size());
                } else {
                    interfaceResponseHouseNearestByUser.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<HouseNearestByUserResponse> call, Throwable t) {
                Log.d(AppConstant.TAG_ERROR, t.getMessage() + " error");
            }
        });
    }
}
