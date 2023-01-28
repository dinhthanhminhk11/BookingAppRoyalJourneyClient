package com.example.bookingapproyaljourney.repository;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.bookingapproyaljourney.api.ApiRequest;
import com.example.bookingapproyaljourney.callback.CallBackString;
import com.example.bookingapproyaljourney.callback.CallbackDeleteOrder;
import com.example.bookingapproyaljourney.callback.CallbackEditOrderByUser;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.callback.CallbackListOrderById;
import com.example.bookingapproyaljourney.callback.CallbackOrderById;
import com.example.bookingapproyaljourney.callback.InterfaceResponseOrder;
import com.example.bookingapproyaljourney.constants.AppConstant;
import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.bill.CancelBillResponse;
import com.example.bookingapproyaljourney.response.bill.ListBillResponse;
import com.example.bookingapproyaljourney.response.bill.StatusBillResponse;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderResponse;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;
import com.example.bookingapproyaljourney.retrofit.RetrofitRequest;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("NewApi")
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

    public void getOrderById(String id, CallbackOrderById callbackOrderById) {
        Call<OrderBill> call = apiRequest.getOrderById(id);
        call.enqueue(new Callback<OrderBill>() {
            @Override
            public void onResponse(Call<OrderBill> call, Response<OrderBill> response) {
                if (response.isSuccessful()) {
                    callbackOrderById.onResponse(response.body());
                } else {
                    callbackOrderById.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrderBill> call, Throwable t) {
                callbackOrderById.onFailure(t);
            }
        });
    }

    public void getProductById(String id, CallbackHouseById callbackHouseById) {
        apiRequest.getDetailProduct(id).enqueue(new Callback<HouseDetailResponse>() {
            @Override
            public void onResponse(Call<HouseDetailResponse> call, Response<HouseDetailResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(AppConstant.TAG_ERROR, "code ; " + response.code());
                } else {
                    Log.e(AppConstant.TAG, "data " + response.body().getContent());
                    callbackHouseById.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<HouseDetailResponse> call, Throwable t) {
                callbackHouseById.failure(t);
            }
        });
    }

    public void editOrderByUser(OrderRequest orderRequest, CallbackEditOrderByUser callbackEditOrderByUser) {
        apiRequest.editOrderByUser(orderRequest).enqueue(new Callback<OrderStatusResponse>() {
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {
                if (response.isSuccessful()) {
                    callbackEditOrderByUser.success(response.body());
                } else {
                    callbackEditOrderByUser.failure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                callbackEditOrderByUser.failure(t);
            }
        });
    }

    public void editOrderByUserUpdateOrderByIdNotSeem(OrderRequest orderRequest, CallbackEditOrderByUser callbackEditOrderByUser) {
        apiRequest.editOrderByUserUpdateOrderByIdNotSeem(orderRequest).enqueue(new Callback<OrderStatusResponse>() {
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {
                if (response.isSuccessful()) {
                    callbackEditOrderByUser.success(response.body());
                } else {
                    callbackEditOrderByUser.failure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                callbackEditOrderByUser.failure(t);
            }
        });
    }

    public void deleteOrderById(String id, CallbackDeleteOrder callbackDeleteOrder) {
        apiRequest.deleteOrderById(id).enqueue(new Callback<OrderStatusResponse>() {
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {
                if (response.isSuccessful()) {
                    callbackDeleteOrder.onResponse(response.body());
                } else {
                    callbackDeleteOrder.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
                callbackDeleteOrder.onFailure(t);
            }
        });
    }

    public void getHouseResponseByServer(CallBackString callBackString) {
        apiRequest.getHouseResponseByServer().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callBackString.onResponse(response.body());
                } else {
                    callBackString.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callBackString.onFailure(t);
            }
        });
    }

    public void getStatusBill(String id, Consumer consumer) {
        apiRequest.getStatusBill(id).enqueue(new Callback<StatusBillResponse>() {
            @Override
            public void onResponse(Call<StatusBillResponse> call, Response<StatusBillResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<StatusBillResponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getListBillByUserId(String id, Consumer<List<ListBillResponse>> consumer) {
        apiRequest.getListBillByUser(id).enqueue(new Callback<List<ListBillResponse>>() {
            @Override
            public void onResponse(Call<List<ListBillResponse>> call, Response<List<ListBillResponse>> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<ListBillResponse>> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

    public void getDataCancelBooking(String id, Consumer consumer) {
        apiRequest.getDataCancelBooking(id).enqueue(new Callback<CancelBillResponse>() {
            @Override
            public void onResponse(Call<CancelBillResponse> call, Response<CancelBillResponse> response) {
                if (response.isSuccessful()) {
                    consumer.accept(response.body());
                } else {
                    Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CancelBillResponse> call, Throwable t) {
                Log.e(AppConstant.TAG_MINHCHEK, AppConstant.TAG_ERROR);
            }
        });
    }

}
