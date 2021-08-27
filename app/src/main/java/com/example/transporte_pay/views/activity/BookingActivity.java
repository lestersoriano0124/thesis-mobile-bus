package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.BusRoutes;
import com.example.transporte_pay.data.response.RoutesJSONResponse;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingActivity extends AppCompatActivity{
    SessionManager sessionManager;
    String token;
    TextView title;
    Spinner destinationTo, destinationFrom;
    List<BusRoutes> routesList;
    AlertDialogManager alert;
//    RecyclerView recyclerView;
    private ArrayList<String> destinationList = new ArrayList<String>();
    private ArrayList<String> StartingPointList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        title = findViewById(R.id.title);
        destinationTo = findViewById(R.id.spinnerTo);
        destinationFrom = findViewById(R.id.spinnerFrom);
//        recyclerView = findViewById(R.id.bus_list);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);



        alert = new AlertDialogManager();
        routesList = new ArrayList<>();

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

//        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<String>(getApplicationContext(),
//                simple_spinner_dropdown_item,
//                routesList);


        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

      // title.setText(token);

        Log.e("TOKEN", "************ " + token);


        getBusRoutes();

    }

    private void getBusRoutes() {
        Call<RoutesJSONResponse> call = ApiClient.getBusClient().getRoutes("Bearer " + token);
        call.enqueue(new Callback<RoutesJSONResponse>() {
            @Override
            public void onResponse(Call<RoutesJSONResponse> call, Response<RoutesJSONResponse> response) {
                if(response.isSuccessful()){
//                    RoutesJSONResponse routesJSONResponse = response.body();
//                    Log.e("RESULT", "-----------" +routesJSONResponse.getBus_routes());
//                    routesList = new ArrayList<>(Arrays.asList(routesJSONResponse.getBus_routes()));
//                    Log.e("RESULT", "-----------" +routesList);
                    try {
                        String getResponse = new Gson().toJson(response.body().getBus_routes());
                        List<BusRoutes> getBusRoutes = new ArrayList<BusRoutes>();
                        JSONArray jsonArray = new JSONArray(getResponse);
                        for (int i=0;i<jsonArray.length();i++) {
                            BusRoutes busRoutes = new BusRoutes();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            busRoutes.setStarting_point(jsonObject.getString("starting_point"));
                            busRoutes.setDestination(jsonObject.getString("destination"));
                            busRoutes.setFare(jsonObject.getInt("fare"));
                            busRoutes.setId(jsonObject.getInt("id"));

                            getBusRoutes.add(busRoutes);
                            Log.e("RESULT", "-----------" + getResponse);
                            Log.e("Kineme", "-----------" + getBusRoutes);
                            Log.e("fare", "-----------" + getBusRoutes.get(i).getStarting_point());
                        }
                        for (int i=0;i<getBusRoutes.size();i++) {
                            destinationList.add(getBusRoutes.get(i).getStarting_point().toString());
//                            destinationList.add(getBusRoutes.get(i).getDestination().toString());
//                            destinationList.add(getBusRoutes.get(i).getFare());
//                            destinationList.add(getBusRoutes.get(i).getId().toString());

//                            StartingPointList.add(getBusRoutes.get(i).getDestination().toString());
                            String fare = getBusRoutes.get(i).getDestination().toString();
                            Log.e("fare", "-----------" + getBusRoutes);


                        }
                            ArrayAdapter<String> spinRoutesAdapter = new ArrayAdapter<String>(BookingActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    destinationList);
                            spinRoutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            destinationFrom.setAdapter(spinRoutesAdapter);


                            destinationFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String mID = getBusRoutes.get(position).getId().toString();
                                    String mDestination = getBusRoutes.get(position).getDestination().toString();

                                    Log.e("mSelected", "-----------" + mID + mDestination);

                                    ArrayAdapter<String> spinRoutesAdapter1 = new ArrayAdapter<String>(BookingActivity.this,
                                            android.R.layout.simple_spinner_item,
                                            StartingPointList);
                                    spinRoutesAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    destinationTo.setAdapter(spinRoutesAdapter1);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    Log.i("Message", "Nothing is selected");
                                }
                            });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                            .putExtra("data", fare));

                }
            }

            @Override
            public void onFailure(Call<RoutesJSONResponse> call, Throwable t) {
                Log.e("error", "signInResult:failed code=" +t.getMessage());
            }
        });

    }

    private void PutDataSomewhere(List<BusRoutes> routesList) throws JSONException {
//        destinationAdapter = new DestinationAdapter((ArrayList<BusRoutes>) routesList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(destinationAdapter);

//        Log.e("ROUTE", "***" + routesList);
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, simple_spinner_item, routesList);
//        arrayAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
//        destinationFrom.setAdapter((SpinnerAdapter) arrayAdapter);

    }

}