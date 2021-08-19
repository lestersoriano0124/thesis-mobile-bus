package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.response.RoutesJSONResponse;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BusClient {

    @GET(Constants.BUS_ROUTES)
    Call<RoutesJSONResponse> getRoutes(@Header("Authorization") String auth);

}
