package com.example.bookingapproyaljourney.api;

import com.example.bookingapproyaljourney.model.cash.CashFolwRequest;
import com.example.bookingapproyaljourney.model.chat.Data;
import com.example.bookingapproyaljourney.model.chat.DataUser;
import com.example.bookingapproyaljourney.model.chat.Message;
import com.example.bookingapproyaljourney.model.feedback.DataFeedBack;
import com.example.bookingapproyaljourney.model.feedback.DataId;
import com.example.bookingapproyaljourney.model.feedback.FeedBack;
import com.example.bookingapproyaljourney.model.hotel.HotelBillResponse;
import com.example.bookingapproyaljourney.model.hotel.HotelById;
import com.example.bookingapproyaljourney.model.hotel.HotelReponse;
import com.example.bookingapproyaljourney.model.hotel.HotelReponseNearBy;
import com.example.bookingapproyaljourney.model.hotel.LocationNearByRequest;
import com.example.bookingapproyaljourney.model.hotel.Room;
import com.example.bookingapproyaljourney.model.house.Category;
import com.example.bookingapproyaljourney.model.house.HouseNearestByUser;
import com.example.bookingapproyaljourney.model.house.PostIDUserAndIdHouse;
import com.example.bookingapproyaljourney.model.map.Root;
import com.example.bookingapproyaljourney.model.order.OrderBill;
import com.example.bookingapproyaljourney.model.order.OrderCreate;
import com.example.bookingapproyaljourney.model.user.ChangePasswordRequest;
import com.example.bookingapproyaljourney.model.user.Email;
import com.example.bookingapproyaljourney.model.user.UserEditProfileRequest;
import com.example.bookingapproyaljourney.model.user.UserLogin;
import com.example.bookingapproyaljourney.model.user.UserPin;
import com.example.bookingapproyaljourney.model.user.UserRegister;
import com.example.bookingapproyaljourney.model.user.UserRequestTokenDevice;
import com.example.bookingapproyaljourney.model.user.Verify;
import com.example.bookingapproyaljourney.request.BillRequest;
import com.example.bookingapproyaljourney.response.BookmarkResponse;
import com.example.bookingapproyaljourney.response.CategoryBestForYouResponse;
import com.example.bookingapproyaljourney.response.CountNotiResponse;
import com.example.bookingapproyaljourney.response.HouseDetailResponse;
import com.example.bookingapproyaljourney.response.HouseNearestByUserResponse;
import com.example.bookingapproyaljourney.response.LoginResponse;
import com.example.bookingapproyaljourney.response.NotiResponse;
import com.example.bookingapproyaljourney.response.RegisterResponse;
import com.example.bookingapproyaljourney.response.TestResponse;
import com.example.bookingapproyaljourney.response.bill.BillResponse;
import com.example.bookingapproyaljourney.response.bill.CancelBillResponse;
import com.example.bookingapproyaljourney.response.bill.ListBillResponse;
import com.example.bookingapproyaljourney.response.bill.StatusBillResponse;
import com.example.bookingapproyaljourney.response.order.ListFilterResponse;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser;
import com.example.bookingapproyaljourney.response.order.ListOrderByIdUser2;
import com.example.bookingapproyaljourney.response.order.OrderRequest;
import com.example.bookingapproyaljourney.response.order.OrderResponse;
import com.example.bookingapproyaljourney.response.order.OrderStatusResponse;
import com.example.bookingapproyaljourney.response.user.CashFolwResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
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

    //Bookmark
    @GET("listBookmarkById/{id}")
    Call<BookmarkResponse> getListBookMarkByIdUser(@Path("id") String id);

    @POST("createBookmark")
    Call<BookmarkResponse> addBookmark(@Body PostIDUserAndIdHouse postIDUserAndIdHouse);

    @DELETE("deleteBookmark/{id}/{idHotel}")
    Call<BookmarkResponse> deleteBookmark(@Path("id") String idUser, @Path("idHotel") String idHotel);

    @GET("getBookmarkByIdUserAndIdHouse/{id}/{idHotel}")
    Call<BookmarkResponse> getBookmarkByIdUserAndIdHouse(@Path("id") String idUser, @Path("idHotel") String idHotel);

    @GET("getOrderById/{id}")
    Call<OrderBill> getOrderById(@Path("id") String id);

    // FeedBack
    @POST("createFeedBack")
    Call<FeedBack> createFeedBack(@Body FeedBack feedBack);

    @GET("listFeedBack/{idHouse}")
    Call<DataFeedBack> getFeedBack(@Path("idHouse") String idHouse);

    @GET("listIdUserFeedBack/{idHouse}")
    Call<DataId> getListUserFeedback(@Path("idHouse") String idHouse);

    @POST("updateFeedBackUser")
    Call<FeedBack> updateUser(@Body FeedBack feedBack);

    @GET("searchFeedBack/{idHouse}&{tk}")
    Call<DataFeedBack> getFeedbackTk(@Path("idHouse") String idHouse, @Path("tk") String tk);

    @PATCH("updateOrderById")
    Call<OrderStatusResponse> editOrderByUser(@Body OrderRequest orderRequest);

    // update sao
    @GET("updateSaoHotel/{id}&{TbSao}")
    Call<HouseDetailResponse> updateSaoProduct(@Path("id") String id, @Path("TbSao") Double sao);

    @PATCH("updateOrderByIdNotSeem")
    Call<OrderStatusResponse> editOrderByUserUpdateOrderByIdNotSeem(@Body OrderRequest orderRequest);

    @DELETE("deleteOrderById/{id}")
    Call<OrderStatusResponse> deleteOrderById(@Path("id") String id);

    // filter
    @GET("listFilterProduct/{startPrice}&{endPrice}&{sao}&{idCategory}")
    Call<ListFilterResponse> getListFilter(
            @Path("startPrice") String startPrice,
            @Path("endPrice") String endPrice,
            @Path("sao") String sao,
            @Path("idCategory") String idCategory
    );

    @POST("updateCheckTokenDevice")
    Call<TestResponse> updateTokenDevice(@Body UserRequestTokenDevice userRequestTokenDevice);

    @GET("listNotificationByUser/{id}")
    Call<NotiResponse> getListNotification(@Path("id") String id);

    @GET("listSearchProduct/{nameLocation}")
    Call<ListFilterResponse> getListSearch(@Path("nameLocation") String nameLocation);

    @PATCH("updateNotiSeen/{id}")
    Call<TestResponse> updateNotiSeen(@Path("id") String id);

    @GET("listNotibyUserIdNotSeem/{id}")
    Call<CountNotiResponse> getCountNotification(@Path("id") String id);

    @GET("listProductAccessByUserId/{id}")
    Call<ListOrderByIdUser2> getListProductAccessById(@Path("id") String id);

    @PATCH("updateInfoUser")
    Call<TestResponse> updateInfoUser(@Body UserEditProfileRequest userEditProfileRequest);

    @PATCH("updatePassword")
    Call<TestResponse> changePassword(@Body ChangePasswordRequest changePasswordRequest);

    @GET("getHouseResponseByServer")
    Call<String> getHouseResponseByServer();

    @GET("getCash/{id}")
    Call<String> getPriceCash(@Path("id") String id);

    @GET("listCashFlow/{id}")
    Call<List<CashFolwResponse>> getListCashFolw(@Path("id") String id);

    @POST("createCashFlow")
    Call<TestResponse> createCashFolw(@Body CashFolwRequest cashFolwRequest);

    @GET("getPassPin/{id}")
    Call<String> getPassCash(@Path("id") String id);

    @POST("createPinPass")
    Call<TestResponse> createPassCash(@Body UserPin userPin);

    @GET("getAllHotelConfirm")
    Call<HotelReponse> getAllListHotel();

    @POST("hotelNearBy")
    Call<HotelReponseNearBy> getListHotelNearBy(@Body LocationNearByRequest locationNearByRequest);

    @GET("getHotelById/{id}")
    Call<HotelById> getHotelById(@Path("id") String id);

    @GET("getPhongById/{id}")
    Call<Room> getRoomById(@Path("id") String id);

    @GET("getHotelAndRoomByIdRoom/{id}/{idUser}")
    Call<HotelBillResponse> getHotelAndRoomByIdRoom(@Path("id") String id, @Path("idUser") String idUser);

    @POST("addorder")
    Call<BillResponse> createBooking(@Body BillRequest billRequest);

    @GET("getBillById/{id}")
    Call<StatusBillResponse> getStatusBill(@Path("id") String id);

    @GET("listBillByUserId/{id}")
    Call<List<ListBillResponse>> getListBillByUser(@Path("id") String id);

    @GET("getDataCancelBooking/{id}")
    Call<CancelBillResponse> getDataCancelBooking(@Path("id") String id);
}
