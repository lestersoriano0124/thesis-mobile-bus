package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    TextView name, email, role;
    String uName, uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        role = findViewById(R.id.prof_role);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> hash = sessionManager.getUSerDetails();
        uName = hash.get(SessionManager.NAME);
        uEmail = hash.get(SessionManager.EMAIL);


        name.setText(uName);
        email.setText(uEmail);


    }
}