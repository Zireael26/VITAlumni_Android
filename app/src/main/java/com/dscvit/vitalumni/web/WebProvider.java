package com.dscvit.vitalumni.web;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebProvider {

    public static final String BASE_URL = "https://vtop9.vit.ac.in/VitaadayApp/";

    public static WebService provide() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService webService = retrofit.create(WebService.class);
        return webService;
    }

}
