package com.example.bookingapproyaljourney.view_model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bookingapproyaljourney.callback.CallbackDeleteOrder;
import com.example.bookingapproyaljourney.callback.CallbackEditOrderByUser;
import com.example.bookingapproyaljourney.callback.CallbackHouseById;
import com.example.bookingapproyaljourney.callback.CallbackOrderById;
import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.repository.OrderRepository;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;

public class StatusOrderViewModel extends AndroidViewModel {
    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<OrderBill> orderResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<HouseDetailResponse> houseDetailResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<OrderStatusResponse> orderStatusResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<OrderStatusResponse> deleteOrderResponse = new MutableLiveData<>();
    private OrderRepository orderRepository;

    public StatusOrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository();
    }

    public void getOrderById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.getOrderById(id, new CallbackOrderById() {
            @Override
            public void onResponse(OrderBill orderResponse) {
                mProgressMutableData.postValue(View.GONE);
                orderResponseMutableLiveData.postValue(orderResponse);
            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
    }

    public void getDetailHouseById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.getProductById(id, new CallbackHouseById() {
            @Override
            public void success(HouseDetailResponse houseDetailResponse) {
                mProgressMutableData.postValue(View.GONE);
                houseDetailResponseMutableLiveData.postValue(houseDetailResponse);
            }

            @Override
            public void failure(Throwable t) {
                mProgressMutableData.postValue(View.GONE);
            }
        });
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

    public void deleteOrderById(String id) {
        mProgressMutableData.postValue(View.VISIBLE);
        orderRepository.deleteOrderById(id, new CallbackDeleteOrder() {
            @Override
            public void onResponse(OrderStatusResponse orderStatusResponse) {
                mProgressMutableData.postValue(View.GONE);
                deleteOrderResponse.postValue(orderStatusResponse);
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

    public MutableLiveData<OrderBill> getOrderResponseMutableLiveData() {
        return orderResponseMutableLiveData;
    }

    public MutableLiveData<HouseDetailResponse> getHouseDetailResponseMutableLiveData() {
        return houseDetailResponseMutableLiveData;
    }

    public MutableLiveData<OrderStatusResponse> getOrderStatusResponseMutableLiveData() {
        return orderStatusResponseMutableLiveData;
    }

    public MutableLiveData<OrderStatusResponse> getDeleteOrderResponse() {
        return deleteOrderResponse;
    }
}
