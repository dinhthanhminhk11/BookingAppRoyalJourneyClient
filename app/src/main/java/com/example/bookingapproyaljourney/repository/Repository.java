package com.example.bookingapproyaljourney.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.hotel.Hotel;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.model.search.SearchModel;
import com.example.bookingapproyaljourney.request.BillRequest;
import com.example.bookingapproyaljourney.response.bill.BillResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
public class Repository {
    private ApiRequest apiRequest;

    public Repository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
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

    public void getListHotelNearByUser(LocationNearByRequest locationNearByRequest, Consumer<HotelReponseNearBy> consumer) {
        apiRequest.getListHotelNearBy(locationNearByRequest).enqueue(new Callback<HotelReponseNearBy>() {
            @Override
            public void onResponse(Call<HotelReponseNearBy> call, Response<HotelReponseNearBy> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelReponseNearBy> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getHotelById(String id, Consumer<HotelById> consumer) {
        apiRequest.getHotelById(id).enqueue(new Callback<HotelById>() {
            @Override
            public void onResponse(Call<HotelById> call, Response<HotelById> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelById> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getRoomById(String id, Consumer<Room> consumer) {
        apiRequest.getRoomById(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getHotelAndRoomByIdRoom(String id, String idUser, Consumer<HotelBillResponse> consumer) {
        apiRequest.getHotelAndRoomByIdRoom(id, idUser).enqueue(new Callback<HotelBillResponse>() {
            @Override
            public void onResponse(Call<HotelBillResponse> call, Response<HotelBillResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<HotelBillResponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void createBill(BillRequest billRequest, Consumer consumer) {
        apiRequest.createBooking(billRequest).enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<BillResponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getListSearchLocationHotel(String textLocation, Consumer consumer) {
        apiRequest.getListSearchLocationHotel(textLocation).enqueue(new Callback<List<SearchModel>>() {
            @Override
            public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<SearchModel>> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getListFilterHotelByHome(String textLocation, int ageChildren, int person, int children, int countRoom, Consumer consumer) {
        apiRequest.getListFilterHotelByHome(textLocation, ageChildren, person, children, countRoom).enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }
}
