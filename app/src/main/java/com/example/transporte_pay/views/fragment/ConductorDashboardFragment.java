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
import com.example.transporte_pay.utils.SessionManager;
import com.example.transporte_pay.views.activity.BookingActivity;
import com.example.transporte_pay.views.activity.ConductorBookingActivity;
import com.example.transporte_pay.views.activity.ConductorMapsActivity;
import com.example.transporte_pay.views.activity.ContactTraceActivity;
import com.example.transporte_pay.views.activity.ProfileActivity;
import com.example.transporte_pay.views.activity.TravelLogsActivity;

import java.util.HashMap;

public class ConductorDashboardFragment extends Fragment  implements View.OnClickListener{
    private String message,uName,status;
    private Integer id;
    public TextView text;
    CardView card1, card2, card3 , card4;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        HashMap<String, String> hashUser = sessionManager.getUSerDetails();
        status = hashUser.get(SessionManager.STATUS);
        HashMap<String, Integer> hash = sessionManager.getID();
        id = hash.get(SessionManager.ID);

        View view = inflater.inflate(R.layout.fragment_conductor_dashboard, container, false);

        card1 = view.findViewById(R.id.conductorBookingDetails_card);
        card2 = view.findViewById(R.id.conductorBusLocation_card);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);


        Bundle data = getArguments();

        if (data != null){
            message = data.getString("message");
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


        }

    }
}