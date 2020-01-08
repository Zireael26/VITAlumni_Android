package com.dscvit.vitalumni.web;

import com.dscvit.vitalumni.model.api.FormSaver;
import com.dscvit.vitalumni.model.api.LoginApiModel;
import com.dscvit.vitalumni.model.api.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebService {

    @GET("testOne?regno=1")
    Call<String> test();

    @POST("apiProcessLogin")
    Call<LoginResponse> login(@Body LoginApiModel loginApiModel);

    @POST("doRegistration")
    Call<String> register(@Header("token") String token, @Body FormSaver form);

}
