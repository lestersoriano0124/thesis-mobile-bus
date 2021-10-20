package com.example.transporte_pay.views.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.api.PaymentClient;
import com.example.transporte_pay.data.model.Routes;
import com.example.transporte_pay.data.request.PaymentRequest;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView message;
    EditText busGcash,fullName,reference;
    ImageButton uploadImageBtn;
    ImageView showImage;
    Button save,back;
    int scheduleID, quantity;
    String token,transId,busGcashNumber,fullname;
    SessionManager sessionManager;
    AlertDialogManager alert;
    private Bitmap bitmap;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 21 && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                showImage.setImageBitmap(bitmap);
                showImage.setVisibility(View.VISIBLE);

            }catch (IOException e){
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(),TravelLogsActivity.class);
        startActivity(backIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        busGcash       = findViewById(R.id.gcashNumber);
        fullName       = findViewById(R.id.fullname);
        reference      = findViewById(R.id.reference);
        uploadImageBtn = findViewById(R.id.uploadBtn);
        showImage      = findViewById(R.id.proofImage);
        save           = findViewById(R.id.save);
        back           = findViewById(R.id.back);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            busGcashNumber = (String) extras.getString("busGcash");
            fullname = (String) extras.getString("fullname");
            transId = (String) extras.getString("transId");
        }
        busGcash.setEnabled(false);
        fullName.setEnabled(false);

        busGcash.setText(busGcashNumber);
        fullName.setText(fullname);


        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,21);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(getApplicationContext(),TravelLogsActivity.class);
                startActivity(backIntent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


//    private void gotoConfirm() {
//        TransactionRequest transactionRequest = new TransactionRequest();
//        transactionRequest.setScheduleID(scheduleID);
//        transactionRequest.setQuantity(quantity);
//
//        Call<TransactionResponse> transCall = ApiClient.getBusClient().getTransaction(transactionRequest, "Bearer " + token);
//        transCall.enqueue(new Callback<TransactionResponse>() {
//            @Override
//            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
//                if (response.isSuccessful()){
//                    String getResponse = new Gson().toJson(response.body());
//                    List<Routes> routesList = new ArrayList<>();
//
//                    Log.e("RESPONSE", "****************" + getResponse);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            alert.showAlertDialog(getApplicationContext(),
////                                    "TRANSACTION SAVED",
////                                    "Transaction Successfully Saved",
////                                    true);
//
//                            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }, 300);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TransactionResponse> call, Throwable t) {
//                Log.e("error", "busActivity:failed code=" +t.getMessage());
//            }
//        });
//    }
    }
    private void uploadImage(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
        byte[] imageInByte   = byteArrayOutputStream.toByteArray();

        String encodedImage =Base64.getEncoder().encodeToString(imageInByte);


        Call<PaymentRequest> paymentCall = ApiClient.getPaymentClient().uploadImage(encodedImage);
        paymentCall.enqueue(new Callback<PaymentRequest>() {
            @Override
            public void onResponse(Call<PaymentRequest> call, Response<PaymentRequest> response) {
                Toast.makeText(PaymentActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                if(response.body().isStatus()){
                    Intent intent = new Intent(PaymentActivity.this,TravelLogsActivity.class);
                    startActivity(intent);
                }else{

                }
            }

            @Override
            public void onFailure(Call<PaymentRequest> call, Throwable t) {

                Toast.makeText(PaymentActivity.this,"Network Failure",Toast.LENGTH_SHORT).show();
            }
        });



    }
}