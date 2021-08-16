package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.BusRoutes;
import com.example.transporte_pay.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BusClient {

    @GET(Constants.BUS_ROUTES)
    Call<List<BusRoutes>> getRoutes(@Header("Authorization") String auth);

}
