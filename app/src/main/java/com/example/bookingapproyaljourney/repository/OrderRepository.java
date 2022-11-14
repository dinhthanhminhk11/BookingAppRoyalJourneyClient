package com.example.bookingapproyaljourney.repository;


import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallbackListOrderById;
import com.example.bookingapproyaljourney.callback.InterfaceResponseOrder;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.OrderResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private ApiRequest apiRequest;

    public OrderRepository() {
        this.apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public void postOrder(OrderCreate orderCreate, InterfaceResponseOrder interfaceResponseOrder) {
        Call<OrderResponse> call = apiRequest.postOrder(orderCreate);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    interfaceResponseOrder.onResponse(response.body());
                } else {
                    interfaceResponseOrder.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                interfaceResponseOrder.onFailure(t);
            }
        });
    }

    public void getListOrderById(String id, CallbackListOrderById callbackListOrderById) {
        Call<ListOrderByIdUser> call = apiRequest.getListOrderByIdUser(id);
        call.enqueue(new Callback<ListOrderByIdUser>() {
            @Override
            public void onResponse(Call<ListOrderByIdUser> call, Response<ListOrderByIdUser> response) {
                if (response.isSuccessful()) {
                    callbackListOrderById.success(response.body());
                } else {
                    callbackListOrderById.failure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ListOrderByIdUser> call, Throwable t) {
                callbackListOrderById.failure(t);
            }
        });
    }
}
