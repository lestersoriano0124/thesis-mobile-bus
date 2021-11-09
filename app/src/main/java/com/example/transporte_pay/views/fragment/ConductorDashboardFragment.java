package com.example.transporte_pay.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.request.ConductorRequest;
import com.example.transporte_pay.utils.SessionManager;
import com.example.transporte_pay.views.activity.BookingActivity;
import com.example.transporte_pay.views.activity.ConductorBookingActivity;
import com.example.transporte_pay.views.activity.ConductorMapsActivity;
import com.example.transporte_pay.views.activity.ContactTraceActivity;
import com.example.transporte_pay.views.activity.ProfileActivity;
import com.example.transporte_pay.views.activity.TravelLogsActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConductorDashboardFragment extends Fragment  implements View.OnClickListener{
    private String message,uName,status,token;
    private Integer id;
    public TextView text;

    CardView card1, card2, card3 , card4,card5;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> hashUser = sessionManager.getUSerDetails();
        status = hashUser.get(SessionManager.STATUS);
        token = hashUser.get(SessionManager.PREF_USER_TOKEN);
        HashMap<String, Integer> hash = sessionManager.getID();
        id = hash.get(SessionManager.ID);


        View view = inflater.inflate(R.layout.fragment_conductor_dashboard, container, false);

        card1 = view.findViewById(R.id.conductorBookingDetails_card);
        card2 = view.findViewById(R.id.conductorBusLocation_card);
        card5 = view.findViewById(R.id.end_travel_card);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card5.setOnClickListener(this);


        Bundle data = getArguments();

        if (data != null){
            message = data.getString("message");
        }

        if(status.equals("booked") ){
        card5.setVisibility(View.VISIBLE);

        }else{
            card5.setVisibility(View.INVISIBLE);
        }


        return view;
    }


    @Override
    public void onClick(View v) {
        Context context;
        Intent intent;
        switch (v.getId()){
            case R.id.conductorBusLocation_card:
//                getConductorBusDetail

                context = getActivity().getApplicationContext();
                if (status.equals("booked") ) {
                    intent = new Intent(context, ConductorMapsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }else{
                    card2.setEnabled(false);
                    Toast.makeText(context, "You are not hired for travel yet. Please wait for your turn"+status, Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.conductorBookingDetails_card:
                context = getActivity().getApplicationContext();
                intent = new Intent(context, ConductorBookingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case R.id.end_travel_card:
                ConductorRequest conductorRequest = new ConductorRequest();
                conductorRequest.setUserId(String.valueOf(id));
                conductorRequest.setHealth("end");

                Call<ConductorRequest> conductorRequestCall = ApiClient.getConductorClient().getConductorBusDetail(conductorRequest,"Bearer :"+token);
                conductorRequestCall.enqueue(new Callback<ConductorRequest>() {
                    @Override
                    public void onResponse(Call<ConductorRequest> call, Response<ConductorRequest> response) {
                        if(response.body().isStatus()){

                            Toast.makeText(getContext(),response.body().getRemarks(),Toast.LENGTH_SHORT).show();
                            card5.setVisibility(View.INVISIBLE);
                            sessionManager.updateStatus("open");
                            HashMap<String, String> hashUserNew = sessionManager.getUSerDetails();
                            status = hashUserNew.get(SessionManager.STATUS);


                        }else{
                            Toast.makeText(getContext(),response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ConductorRequest> call, Throwable t) {
                        Log.w("error", "API:failed code=" +t.getMessage());
                    }
                });
                break;


        }

    }
}