package com.app.asi.retrofit;


import com.app.asi.entities.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebService {

    @FormUrlEncoded
    @POST("change-device-token")
    Call<ResponseWrapper> updateToken(@Field("device_type") String device_type,
                                      @Field("device_token") String device_token);


}