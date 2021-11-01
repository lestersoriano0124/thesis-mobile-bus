package com.example.transporte_pay.data.api;

import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.UpdateUserPass;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.Constants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PaymentClient {

//    @Multipart
//    @POST(Constants.PAYMENT_UPLOAD)
//    Call<PaymentRequest> uploadImage(@Part("proof_image") String encodedImage);
//    @Multipart
//    @POST(Constants.PAYMENT_UPLOAD)
//    Call<PaymentRequest> uploadImage(@Part MultipartBody.Part image,@Part("item_id") RequestBody item_id,@Part("references") RequestBody references,@Header("Authorization") String auth);

//    @Multipart
//    @POST(Constants.PAYMENT_UPLOAD)
//    Call<PaymentRequest> uploadImage(@Part MultipartBody.Part image,@Part("item_id") RequestBody item_id,@Part("references") RequestBody references,@Header("Authorization") String auth);
    @POST(Constants.PAYMENT_UPLOAD)
    Call<PaymentRequest> uploadImage(@Body PaymentRequest paymentRequest , @Header("Authorization") String auth);

}
