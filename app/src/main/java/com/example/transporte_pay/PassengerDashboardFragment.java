package com.example.transporte_pay;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PassengerDashboardFragment extends Fragment {
    private String message;
    public TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_dashboard, container, false);

        text = view.findViewById(R.id.test);

        Bundle data = getArguments();

        if (data != null){
            message = data.getString("message");
        }
        text.setText(message);
        return view;


    }
}