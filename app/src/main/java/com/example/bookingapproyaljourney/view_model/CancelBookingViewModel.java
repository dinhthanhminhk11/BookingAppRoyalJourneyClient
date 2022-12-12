package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallBackString;
import com.example.bookingapproyaljourney.callback.CallbackEditOrderByUser;
import com.example.bookingapproyaljourney.repository.OrderRepository;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;

public class CancelBookingViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;

    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<OrderStatusResponse> orderStatusResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> getDate = new MutableLiveData<>();

    public CancelBookingViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
    }

    public void updateOrderByUser(OrderRequest orderRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.editOrderByUser(orderRequest, new CallbackEditOrderByUser() {
            @Override
            public void success(OrderStatusResponse orderStatusResponse) {
                mProgressMutableData.postValue(View.GONE);
                orderStatusResponseMutableLiveData.postValue(orderStatusResponse);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void editOrderByUserUpdateOrderByIdNotSeem(OrderRequest orderRequest) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.editOrderByUserUpdateOrderByIdNotSeem(orderRequest, new CallbackEditOrderByUser() {
            @Override
            public void success(OrderStatusResponse orderStatusResponse) {
                mProgressMutableData.postValue(View.GONE);
                orderStatusResponseMutableLiveData.postValue(orderStatusResponse);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<OrderStatusResponse> getOrderStatusResponseMutableLiveData() {
        return orderStatusResponseMutableLiveData;
    }

    public void getHouseResponseByServer() {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.getHouseResponseByServer(new CallBackString() {
            @Override
            public void onResponse(String string) {
                mProgressMutableData.postValue(View.GONE);
                getDate.postValue(string);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<String> getGetDate() {
        return getDate;
    }
}
