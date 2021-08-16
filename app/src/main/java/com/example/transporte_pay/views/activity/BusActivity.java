package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.api.BusClient;
import com.example.transporte_pay.data.model.BusRoutes;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);




    }
}