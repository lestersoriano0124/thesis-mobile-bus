package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.request.GoogleSignInRequest;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.data.request.UpdateUserPass;
import com.example.transporte_pay.data.request.UpdateUserRequest;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST(Constants.G_LOGIN)
    Call<User> createGoggleAccount(@Body GoogleSignInRequest googleSignInRequest);

    @POST(Constants.LOGIN)
    Call<User> userLogin(@Body LoginRequest loginRequest);

    @POST(Constants.REGISTER)
    Call<User> userRegister(@Body RegRequest regRequest);

    @POST(Constants.UPDATE_DATA)
    Call<User> updateData(@Body UpdateUserRequest updateUserRequest, @Header("Authorization") String auth);

    @POST(Constants.UPDATE_PASS)
    Call<User> updatePass(@Body UpdateUserPass updateUserPass, @Header("Authorization") String auth);
}
