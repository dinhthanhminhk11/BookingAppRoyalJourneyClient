package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.InterfaceResponseOrder;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.repository.OrderRepository;
import com.example.bookingapproyaljourney.response.order.OrderResponse;

public class OrderViewModel extends AndroidViewModel {
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
    MutableLiveData<OrderResponse> orderResponseMutableLiveData = new MutableLiveData<>();

    private OrderRepository orderRepository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
    }

    public void postOrder(OrderCreate orderCreate) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.postOrder(orderCreate, new InterfaceResponseOrder() {
            @Override
            public void onResponse(OrderResponse orderResponse) {
                mProgressMutableData.postValue(View.GONE);
                orderResponseMutableLiveData.postValue(orderResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public MutableLiveData<Integer> getmProgressMutableData() {
        return mProgressMutableData;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public MutableLiveData<OrderResponse> getOrderResponseMutableLiveData() {
        return orderResponseMutableLiveData;
    }
}
