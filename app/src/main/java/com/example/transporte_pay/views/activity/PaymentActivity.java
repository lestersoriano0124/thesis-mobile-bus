package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Routes;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.data.response.TransactionResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    TextView message;
    int scheduleID, quantity;
    String token;
    SessionManager sessionManager;
    AlertDialogManager alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        message = findViewById(R.id.test_tv);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            quantity = (int) extras.getInt("number");
            scheduleID = (int) extras.getInt("quantity");
        }

        alert = new AlertDialogManager();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        gotoConfirm();

    }

    private void gotoConfirm() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setScheduleID(scheduleID);
        transactionRequest.setQuantity(quantity);

        Call<TransactionResponse> transCall = ApiClient.getBusClient().getTransaction(transactionRequest, "Bearer " + token);
        transCall.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()){
                    String getResponse = new Gson().toJson(response.body());
                    List<Routes> routesList = new ArrayList<>();

                    Log.e("RESPONSE", "****************" + getResponse);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            alert.showAlertDialog(getApplicationContext(),
//                                    "TRANSACTION SAVED",
//                                    "Transaction Successfully Saved",
//                                    true);

                            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 300);
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" +t.getMessage());
            }
        });
    }
}