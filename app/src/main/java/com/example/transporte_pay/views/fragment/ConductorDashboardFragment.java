package com.example.transporte_pay.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.SessionManager;
import com.example.transporte_pay.views.activity.BookingActivity;
import com.example.transporte_pay.views.activity.ConductorMapsActivity;
import com.example.transporte_pay.views.activity.ContactTraceActivity;
import com.example.transporte_pay.views.activity.ProfileActivity;
import com.example.transporte_pay.views.activity.TravelLogsActivity;

public class ConductorDashboardFragment extends Fragment  implements View.OnClickListener{
    private String message;
    public TextView text;
    CardView card1, card2, card3 , card4;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();
        View view = inflater.inflate(R.layout.fragment_conductor_dashboard, container, false);


//        card1 = view.findViewById(R.id.driverBusLocation_card);
        card2 = view.findViewById(R.id.conductorBusLocation_card);
//        card3 = view.findViewById(R.id.account_card);
//        card4 = view.findViewById(R.id.covid_card);
        card2.setOnClickListener(this);
//        card2.setOnClickListener(this);
//        card3.setOnClickListener(this);
//        card4.setOnClickListener(this);
        Bundle data = getArguments();

        if (data != null){
            message = data.getString("message");
        }
//        text.setText("Basim");
        return view;
    }


    @Override
    public void onClick(View v) {
        Context context;
        Intent intent;
        switch (v.getId()){
            case R.id.conductorBusLocation_card:
                context = getActivity().getApplicationContext();

                intent = new Intent(context, ConductorMapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;



        }

    }
}