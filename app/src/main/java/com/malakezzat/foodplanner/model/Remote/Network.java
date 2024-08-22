package com.malakezzat.foodplanner.model.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static final String TAG = "RetrofitRequest";
    Retrofit retrofit;
    public static final String BASR_URL = "https://www.themealdb.com/api/json/v1/1/";
    static NetworkCallBack networkCallBack;

    public Network(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
