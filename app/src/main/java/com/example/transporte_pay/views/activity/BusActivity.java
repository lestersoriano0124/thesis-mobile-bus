package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.ScheduleAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.data.model.Routes;
import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.request.TransactionRequest;
import com.example.transporte_pay.data.response.ScheduleResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends AppCompatActivity {
    int uToID, uFromID, quantity = 1, uQuantity, uSchedID;
    String token, uDate, to, from;
    RecyclerView recyclerView;
    TextView from_tv, to_tv, quantityNo;
    EditText date;
    ImageView plus, minus;
    SessionManager sessionManager;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> schedules;
    AlertDialogManager alert;
    private ScheduleAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        from_tv = findViewById(R.id.locationFrom_tv);
        to_tv = findViewById(R.id.locationTo_tv);
        date = findViewById(R.id.date_et);
        plus = findViewById(R.id.plus_imgbtn);
        minus = findViewById(R.id.minus_imgbtn);
        quantityNo = findViewById(R.id.quantity_tv);
        recyclerView = findViewById(R.id.ticketList_RV);
        scheduleAdapter = new ScheduleAdapter();

        alert = new AlertDialogManager();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            uToID = (int) b.get("toID");
            uFromID = (int) b.get("fromID");
            uDate = (String) b.get("date");
            to = (String) b.get("to");
            from = (String) b.get("from");

            to_tv.setText(to);
            from_tv.setText(from);
            date.setText(uDate);

            Log.e("DATA", "************* TEST: " + uToID + uFromID + uDate);
        }

        plus.setOnClickListener(v -> {
            quantity++;
            displayQuantity();
        });

        minus.setOnClickListener(v -> {
            if (quantity == 0) {
                Toast.makeText(getApplicationContext(), "CANT DECREASE TO ZERO", Toast.LENGTH_SHORT).show();
            } else {
                quantity--;
                displayQuantity();
            }
        });

        gotoSchedule();
    }


    private void displayQuantity() {
        quantityNo.setText(String.valueOf(quantity));
        Log.e("NUMBER", "********** QNTY: " + quantity);
    }


    private void gotoSchedule() {
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStarting_point_id(uFromID);
        scheduleRequest.setDestination_id(uToID);
        scheduleRequest.setSchedule_date(uDate);

        Call<ScheduleResponse> callSchedule = ApiClient.getBusClient().getSchedule(scheduleRequest, "Bearer " + token);
        callSchedule.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful()) {
                    schedules = response.body().getSchedule();
                    setOnClickListener();
                    scheduleAdapter.setData(schedules, listener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(scheduleAdapter);
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" + t.getMessage());
            }
        });
    }

    private void setOnClickListener() {
        listener = new ScheduleAdapter.RecyclerViewClickListener() {
            @Override
            public void OnClick(View v, int position) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class)
//                        .putExtra("quantity", schedules.get(position).getId())
//                        .putExtra("number", quantity);
//                startActivity(intent);

                uQuantity = schedules.get(position).getId();
                uSchedID =  quantity;

                gotoConfirm();
            }
        };
    }

    private void gotoConfirm() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setScheduleID(uSchedID);
        transactionRequest.setQuantity(quantity);

        Call<Booking> transCall = ApiClient.getBusClient().getTransaction(transactionRequest, "Bearer " + token);
        transCall.enqueue(new Callback<Booking>() {
            @Override
            public void onResponse(Call<Booking> call, Response<Booking> response) {
                if (response.isSuccessful()){
                    String getResponse = new Gson().toJson(response.body());
                    List<Routes> routesList = new ArrayList<>();

                    Log.e("RESPONSE", "****************" + getResponse);

                    alert.showAlertDialog(BusActivity.this,
                        "TRANSACTION SAVED",
                        "Transaction Successfully Saved",
                        true);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(BusActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 700);
                }
            }

            @Override
            public void onFailure(Call<Booking> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" +t.getMessage());
            }
        });
    }
}