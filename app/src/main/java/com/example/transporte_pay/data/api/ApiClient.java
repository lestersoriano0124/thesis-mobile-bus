package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.Constants;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    SessionManager sessionManager;

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    public static UserClient getUserClient(){
        return getRetrofit().create(UserClient.class);
    }

    public static BusClient getBusClient(){
        return  getRetrofit().create(BusClient.class);
    }



}

