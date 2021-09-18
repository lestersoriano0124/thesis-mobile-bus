package com.example.transporte_pay.views.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.transporte_pay.R;

public class ConductorDashboardFragment extends Fragment {
    CardView card1, card2, card3 , card4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conductor_dashboard, container, false);
    }
}