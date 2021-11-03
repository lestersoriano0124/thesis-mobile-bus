package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.request.ConductorRequest;
import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ConductorClient {
    @POST(Constants.CONDUCTOR_BUS)
    Call<ConductorRequest> getConductorBusDetail(@Body ConductorRequest conductorRequest , @Header("Authorization") String auth);
}
