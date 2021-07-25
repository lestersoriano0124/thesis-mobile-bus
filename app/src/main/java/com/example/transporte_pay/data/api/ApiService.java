package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.response.LoginResponse;
import com.example.transporte_pay.utils.Constants;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST(Constants.LOGIN)
    @FormUrlEncoded
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

//    @FormUrlEncoded
//    @POST("auth/user/logout")
//    Call<LoginRequest> signOut(@Field("user_Id") Integer user_id,
//                              @Header("Authorization") String authorization);


}
