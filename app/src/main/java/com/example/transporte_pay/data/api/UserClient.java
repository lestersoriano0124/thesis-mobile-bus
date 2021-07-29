package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.data.response.AuthResponse;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserClient {

    @FormUrlEncoded
    @POST("/google/")
    Call<User> createGoggleAccount(@Body User user);

    @POST(Constants.LOGIN)
    Call<AuthResponse> userLogin(@Body LoginRequest loginRequest);

    @POST(Constants.REGISTER)
    Call<AuthResponse> userRegister(@Body RegRequest regRequest);


}
