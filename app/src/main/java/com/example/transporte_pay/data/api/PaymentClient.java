package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.utils.Constants;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PaymentClient {

    @FormUrlEncoded
    @POST(Constants.PAYMENT_UPLOAD)
    Call<PaymentRequest> uploadImage(@Field("proof_image") String encodedImage);
}
