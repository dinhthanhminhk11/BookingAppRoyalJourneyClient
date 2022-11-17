package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.chat.Data;
import com.example.bookingapproyaljourney.model.chat.DataUser;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.map.Root;
import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("listCategory")
    Call<List<Category>> getCategory();

    @POST("signin")
    Call<LoginResponse> getUser(@Body UserLogin userLogin);

    @POST("signup")
    Call<RegisterResponse> getUserRegister(@Body UserRegister userRegister);

    @POST("signup/verify")
    Call<TestResponse> getUserRegisterOTP(@Body Verify verify);

    @GET("getNameCategory/{id}")
    Call<String> getNameCategoryById(@Path("id") String idCategory);

    @POST("signup/verify/sendAgain")
    Call<TestResponse> sendAgain(@Body Email email);

    @GET("maps/api/directions/json")
    Call<Root> getRoot(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String key
    );

    @GET("listProduct/{id}")
    Call<HouseDetailResponse> getDetailProduct(@Path("id") String id);

    @GET("listCategory/{id}")
    Call<CategoryBestForYouResponse> getHouseByCategory(@Path("id") String id);

    @POST("nearmylocation")
    Call<HouseNearestByUserResponse> getHouseNearFromYou(@Body HouseNearestByUser houseNearestByUser);

    @POST("nearByUserLocationAllCategory")
    Call<HouseNearestByUserResponse> getHouseNearestByUser(@Body HouseNearestByUser houseNearestByUser);

    @GET("getUserByToken")
    Call<LoginResponse> getUserByToken(@Header("x-access-token") String token);

    @POST("checkEmailForgot")
    Call<TestResponse> checkEmail(@Body Email email);

    @POST("validateUserPass")
    Call<TestResponse> checkOtpPass(@Body Verify verify);

    @POST("newPass")
    Call<TestResponse> newPassWord(@Body UserLogin userLogin);

    //chat
    @GET("Message/getmsg/{send}&{sendTo}")
    Call<Data> getDataChat(@Path("send") String sendId, @Path("sendTo") String sendToId);

    @GET("Message/getMessage/{send}")
    Call<Data> getMsgId(@Path("send") String send);

    @GET("Message/getHost/{id}")
    Call<DataUser> getHost(@Path("id") String id);

    @POST("Message/addmsg/")
    Call<Message> addMessage(@Body Message message);

    @POST("addorder")
    Call<OrderResponse> postOrder(@Body OrderCreate orderCreate);


    @GET("listOrderByIdUser/{id}")
    Call<ListOrderByIdUser> getListOrderByIdUser(@Path("id") String id);

    @GET("listBookmarkById/{id}")
    Call<BookmarkResponse> getListBookMarkByIdUser(@Path("id") String id);

    @POST("createBookmark")
    Call<BookmarkResponse> addBookmark(@Body PostIDUserAndIdHouse postIDUserAndIdHouse);

    @DELETE("deleteBookmark/{idUser}/{idHouse}")
    Call<BookmarkResponse> deleteBookmark(@Path("idUser") String idUser, @Path("idHouse") String idHouse);

    @GET("getBookmarkByIdUserAndIdHouse/{idUser}/{idHouse}")
    Call<BookmarkResponse> getBookmarkByIdUserAndIdHouse(@Path("idUser") String idUser, @Path("idHouse") String idHouse);

    @GET("getOrderById/{id}")
    Call<OrderBill> getOrderById(@Path("id") String id);
}
