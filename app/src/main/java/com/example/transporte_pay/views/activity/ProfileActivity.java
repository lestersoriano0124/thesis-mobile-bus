package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.utils.ChangePassDialog;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity implements ChangePassDialog.DialogListener {
    TextView name, email, changePassLink;
    String uName, uEmail, uCurrent, uConfirm, uNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        changePassLink = findViewById(R.id.changePass_link);


        SessionManager sessionManager = new SessionManager(getApplicationContext());

        HashMap<String, String> hash = sessionManager.getUSerDetails();
        uName = hash.get(SessionManager.NAME);
        uEmail = hash.get(SessionManager.EMAIL);

        name.setText(uName);
        email.setText(uEmail);

        changePassLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        ChangePassDialog passDialog = new ChangePassDialog();
        passDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String currentPass, String newPassword, String confirmPass) {
        Toast.makeText(this, currentPass + newPassword +confirmPass, Toast.LENGTH_SHORT ).show();
        Log.e("CHANGE PASSWORD", "DETAILS: " + currentPass + newPassword +confirmPass);
    }
}