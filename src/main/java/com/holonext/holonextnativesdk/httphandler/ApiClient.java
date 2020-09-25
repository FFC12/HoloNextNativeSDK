package com.holonext.holonextnativesdk.httphandler;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private final static String ServiceURI = "https://holonext.azurewebsites.net/api/v1/scene/";
    private static Retrofit retrofitInstance;

    public static Retrofit getClient(){
        if(retrofitInstance == null){
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(ServiceURI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }

}
