package com.example.transporte_pay.views.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.SessionManager;
import com.example.transporte_pay.views.activity.PassengerActivity;
import com.example.transporte_pay.views.activity.ProfileActivity;
import com.example.transporte_pay.views.activity.TravelLogsActivity;

public class DriverDashboardFragment extends Fragment implements View.OnClickListener{
    CardView card1, card2, card3 , card4;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_dashboard, container, false);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        card1 = view.findViewById(R.id.driverViewPassenger_card);
        card2 = view.findViewById(R.id.driverDestinationLog_card);
        card3 = view.findViewById(R.id.driverAccountDetails_card);
        card4 = view.findViewById(R.id.driverBusLocation_card);
        card1.setOnClickListener(this);
//        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
//        card4.setOnClickListener(this);
        return  view;


//        mitchell.makenzie@example.net
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Context context;
        Intent intent;
        switch (v.getId()) {
            case R.id.driverViewPassenger_card:
                context = getActivity().getApplicationContext();
                intent = new Intent(context, TravelLogsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
//            case R.id.log_card:
//                context = getActivity().getApplicationContext();
//                intent = new Intent(context, TravelLogsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                break;
//            case R.id.driverAccountDetails_card:
//                context = getActivity().getApplicationContext();
//                intent = new Intent(context, ProfileActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                break;
//            case R.id.covid_card:
//                context = getActivity().getApplicationContext();
//                intent = new Intent(context, ContactTraceActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                break;

        }
    }
}