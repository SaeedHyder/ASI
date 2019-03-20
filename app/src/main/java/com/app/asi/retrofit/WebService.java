package com.app.asi.retrofit;

import com.app.asi.entities.CMSEnt;
import com.app.asi.entities.GameEnt;
import com.app.asi.entities.ResponseWrapper;
import com.app.asi.entities.WishListEnt;
import com.google.android.gms.games.Game;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {


    @POST("Account/SignIn")
    Call<ResponseWrapper> loginUser(@Body RequestBody parm);

    @POST("Account/SignUp")
    Call<ResponseWrapper> registerUser(@Body RequestBody parm);


    @GET("Account/SignOut/{deviceType}/{deviceToken}")
    Call<ResponseWrapper> logout(@Path("deviceType") String deviceType,
                                 @Path("deviceToken") String deviceToken);

    @GET("Account/ResendCode/{email}")
    Call<ResponseWrapper> resendCode(@Path("email") String email);

    @GET("Account/ForgotPassword/{email}")
    Call<ResponseWrapper> forgotPassword(@Path("email") String email);


    @POST("Account/VerifyCode")
    Call<ResponseWrapper> verifyCode(@Body RequestBody parm);

    @POST("Account/UpdatePassword")
    Call<ResponseWrapper> changePassword(@Body RequestBody parm);


    @POST("Account/ChangePassword")
    Call<ResponseWrapper> updatePassword(@Body RequestBody parm);

    @Multipart
    @POST("Account/UpdateProfile")
    Call<ResponseWrapper> updateProfile(
            @Part("FullName") RequestBody FullName,
            @Part("PhoneCode") RequestBody PhoneCode,
            @Part("PhoneNo") RequestBody PhoneNo,
            @Part("Designation") RequestBody Designation,
            @Part("Company") RequestBody Company,
            @Part MultipartBody.Part Image
    );

    @GET("Account/Update/{isNotification}/Notification")
    Call<ResponseWrapper> isNotification(@Path("isNotification") boolean isNotification);

    @GET("api/Section/{code}/Game")
    Call<ResponseWrapper<ArrayList<GameEnt>>> gameSection(@Path("code") String code);

    @GET("api/Game/{id}/{isFavourite}")
    Call<ResponseWrapper<ArrayList<GameEnt>>> addWishListMap(@Path("id") String id,
                                                             @Path("isFavourite") boolean isFavourite);

    @GET("api/Wishlist")
    Call<ResponseWrapper<ArrayList<WishListEnt>>> getWishList(@Query("pageNumber") int pageNumber,
                                                              @Query("rowsPerPage") int rowsPerPage);

    @GET("api/Game/{id}/Played")
    Call<ResponseWrapper> gameSectionPlayed(@Path("id") String id );

    @GET("api/PlayedList")
    Call<ResponseWrapper<ArrayList<GameEnt>>> getPlayedList(@Query("pageNumber") int pageNumber,
                                                              @Query("rowsPerPage") int rowsPerPage);

    @GET("GeneralInfo")
    Call<ResponseWrapper<CMSEnt>> getCms();



}