package com.example.transporte_pay.data.response;

import com.example.transporte_pay.data.model.User;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status_Code")
    public int statusCode;

    @SerializedName("auth_token")
    public String authToken;

    @SerializedName("user")
    public User user;
}
