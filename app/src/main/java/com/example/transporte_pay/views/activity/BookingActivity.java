package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.Routes;
import com.example.transporte_pay.data.response.RoutesResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingActivity extends AppCompatActivity{
    SessionManager sessionManager;
    String token, uFrom, uTo, uDate, dayPadded, monthPadded;
    int uFromID, uToID;
    TextView title;
    Spinner destinationTo, destinationFrom;
    List<Routes> routesList;
    AlertDialogManager alert;
    DatePicker datePicker;
    Button booking;
    Button bookings;
    private final ArrayList<String> destinationList = new ArrayList<>();
    private final ArrayList<String> StartingPointList = new ArrayList<>();

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        title = findViewById(R.id.title);
        destinationTo = findViewById(R.id.spinnerTo);
        destinationFrom = findViewById(R.id.spinnerFrom);
        datePicker = findViewById(R.id.editTextDate);
        booking = findViewById(R.id.booking_btn);

        booking.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = (datePicker.getMonth() + 1);
            int year = datePicker.getYear();

            monthPadded = String.format("%02d",month );
            dayPadded = String.format("%02d",day );


            uDate = year + "-" + monthPadded + "-" + dayPadded;
            Log.e("DATE", "********** DATE: " + uDate);
            Log.e("LOCATIONS", "********** FROM: "+ uFrom + "  TO: " + uTo);
            Log.e("IDS", "********** FROM ID: "+ uFromID + "  TO ID: " + uToID);

            Intent intent = new Intent(BookingActivity.this, BusActivity.class)
                    .putExtra("fromID", uFromID)
                    .putExtra("toID", uToID)
                    .putExtra("from", uFrom)
                    .putExtra("to", uTo)
                    .putExtra("date", uDate);
            startActivity(intent);
        });

        alert = new AlertDialogManager();
        routesList = new ArrayList<>();

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        getBusRoutes();

    }

    private void getBusRoutes() {
        Call<RoutesResponse> call = ApiClient.getBusClient().getRoutes("Bearer " + token);
        call.enqueue(new Callback<RoutesResponse>() {
            @Override
            public void onResponse(@NotNull Call<RoutesResponse> call, @NotNull Response<RoutesResponse> response) {
                if (response.isSuccessful()){
                   try {
                       assert response.body() != null;
                       String getResponse = new Gson().toJson(response.body().getRoutes());
                      List<Routes> routesList = new ArrayList<>();
                      JSONArray jsonArray = new JSONArray(getResponse);
                      for (int i=0;i<jsonArray.length();i++) {
                          Routes routes = new Routes();
                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                          routes.setId(jsonObject.getInt("id"));
                          routes.setName(jsonObject.getString("name"));

                          routesList.add(routes);
                      }

                      for (int i=0;i<routesList.size();i++) {
                          StartingPointList.add(routesList.get(i).getName());
                          destinationList.add(routesList.get(i).getName());
                      }
                       ArrayAdapter<String> spinRoutesAdapter = new ArrayAdapter<>(
                               BookingActivity.this,
                               android.R.layout.simple_spinner_item,
                               StartingPointList);
                       spinRoutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       destinationFrom.setAdapter(spinRoutesAdapter);

                       destinationFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                               int mID = routesList.get(position).getId();
                               String mDestination = routesList.get(position).getName();
                               uFrom =  mDestination;
                               uFromID = mID;
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> parent) {
                               Log.i("Message", "Nothing is selected");
                           }
                       });

                       ArrayAdapter<String> spinRoutesAdapter1 = new ArrayAdapter<>(
                               BookingActivity.this,
                               android.R.layout.simple_spinner_item,
                               destinationList);
                       spinRoutesAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       destinationTo.setAdapter(spinRoutesAdapter1);


                       destinationTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                               int mID = routesList.get(position).getId();
                               String mDestination = routesList.get(position).getName();
                                uTo = mDestination;
                                uToID = mID;
                           }
                           @Override
                           public void onNothingSelected(AdapterView<?> parent) {
                               Log.i("Message", "Nothing is selected");
                           }
                       });
                   }
                   catch (Exception e) {
                       e.printStackTrace();
                   }
                }

            }

            @Override
            public void onFailure(@NotNull Call<RoutesResponse> call, @NotNull Throwable t) {
                Log.e("error", "signInResult:failed code=" +t.getMessage());
            }
        });

    }
}