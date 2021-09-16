package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;

public class ContactTraceActivity extends AppCompatActivity {
    EditText name, address, dov, tov, temp,email;
    String uName, uEmail;
    CheckBox terms;
    Button saveTraceData;
    SessionManager sessionManager;
    AlertDialogManager alert;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact_trace);

            name = findViewById(R.id.trace_name_et);
            address = findViewById(R.id.trace_address_et);
            dov = findViewById(R.id.trace_date_et);
            tov = findViewById(R.id.trace_Time_et);
            temp = findViewById(R.id.trace_temp_et);
            email = findViewById(R.id.trace_email_et);
            terms = findViewById(R.id.terms_cBox);
            saveTraceData = findViewById(R.id.trace_save_btn);

            alert = new AlertDialogManager();
            sessionManager = new SessionManager(getApplicationContext());
            sessionManager.checkLogin();

            HashMap<String, String> map = sessionManager.getUSerDetails();
            uName =  map.get(SessionManager.NAME);
            uEmail = map.get(SessionManager.EMAIL);

            name.setText(uName);
            email.setText(uEmail);
            name.setEnabled(false);
            email.setEnabled(false);

            saveTraceData.setOnClickListener(v -> alert.showAlertDialog(
                    ContactTraceActivity.this,
                    "SUCCESS",
                    "This is under development mode.",
                    true));
        }
    }