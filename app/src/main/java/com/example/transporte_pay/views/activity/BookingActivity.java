package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.transporte_pay.R;

public class BookingActivity extends AppCompatActivity {
    Spinner spinFrom, spinTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        spinFrom = findViewById(R.id.spinnerFrom);




    }
}