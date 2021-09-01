package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.adapter.ScheduleAdapter;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Schedule;
import com.example.transporte_pay.data.request.ScheduleRequest;
import com.example.transporte_pay.data.response.ScheduleResponse;
import com.example.transporte_pay.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends AppCompatActivity {
    int uToID, uFromID;
    String token, uDate, to , from;
    RecyclerView recyclerView;
    TextView from_tv, to_tv;
    EditText date;
    SessionManager sessionManager;
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        from_tv= findViewById(R.id.locationFrom_tv);
        to_tv= findViewById(R.id.locationTo_tv);
        date = findViewById(R.id.date_et);
        recyclerView = findViewById(R.id.ticketList_RV);
        scheduleAdapter = new ScheduleAdapter();

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            uToID =(int) b.get("toID");
            uFromID = (int) b.get("fromID");
            uDate = (String) b.get("date");
            to = (String) b.get("to");
            from = (String) b.get("from");

            to_tv.setText(to);
            from_tv.setText(from);
            date.setText(uDate);

            Log.e("DATA", "************* TEST: " + uToID + uFromID + uDate);
        }

        gotoSchedule();
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
                if (response.isSuccessful()){
                    ArrayList<Schedule> schedules = response.body().getSchedule();
                    scheduleAdapter.setData(schedules);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(scheduleAdapter);
                }
            }
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e("error", "busActivity:failed code=" +t.getMessage());
            }
        });
    }
}