package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.request.RegRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.SessionManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView loginLink;
    Button registerButton;
    EditText name, email, password, c_pass;
    ProgressBar loading;
    AlertDialogManager alert;
    String getName, getEmail, getGooId;
    Integer getRole, id;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink = findViewById(R.id.changePass_link);
        registerButton = findViewById(R.id.reg_btn);
        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        password = findViewById(R.id.reg_password);
        c_pass = findViewById(R.id.reg_confirm_password);
        loading = findViewById(R.id.progressBar_update);

        sessionManager = new SessionManager(this);
        alert = new AlertDialogManager();

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(name.getText().toString()) ||
                 TextUtils.isEmpty(email.getText().toString()) ||
                 TextUtils.isEmpty(password.getText().toString()) ||
                 TextUtils.isEmpty(c_pass.getText().toString()))
            {
                Toast.makeText(RegisterActivity.this, "Email/Password is Required", Toast.LENGTH_LONG).show();
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Email/Password is Required",
                        false);
            }else if (!TextUtils.equals(password.getText().toString(), c_pass.getText().toString())){
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Password did not match, Please Try Again",
                        false);
            }else if(name.length()<10){
                alert.showAlertDialog(RegisterActivity.this,
                        "FAILED",
                        "Please Provide your Full Name",
                        false);
            }
            else
            {
                gotoRegister();

            }

        });
    }

    private void gotoRegister() {
        RegRequest regRequest = new RegRequest();
        regRequest.setName(capitalize(name.getText().toString()));
        regRequest.setEmail(email.getText().toString());
        regRequest.setPassword(password.getText().toString());
        regRequest.setPassword_confirmation(c_pass.getText().toString());

        loading.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);

        Call<User> registerResponseCall = ApiClient.getUserClient().userRegister(regRequest);
        registerResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    loading.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,"Account Registered", Toast.LENGTH_LONG).show();
                    alert.showAlertDialog(RegisterActivity.this,
                            "SUCCESS",
                            "Account Registered",
                            true);
                    User user = response.body();
                    String token = user.getToken();
                    Log.e("TOKEN","******************** " + token);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getName = user.getName();
                            getEmail = user.getEmail();
                            getRole = user.getRole_id();
                            getGooId = user.getGoogle_id();
                            id = user.getId();
                            sessionManager.saveAuthToken(token);
                            sessionManager.createSession(getName, getEmail,getRole,getGooId,id);
                            startActivity(new Intent(RegisterActivity.this,
                                    MainActivity.class).
                                    putExtra("data", user.getToken()));
                        }
                    }, 300);
                    loading.setVisibility(View.GONE);
                    registerButton.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"LOGIN FAILED", Toast.LENGTH_LONG).show();
                    failedAlert();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
                failedAlert();
                Log.e("error", "signInResult:failed code=" +t.getMessage());
                loading.setVisibility(View.GONE);
            }
        });
    }

    private void failedAlert() {
        alert.showAlertDialog(RegisterActivity.this,
                "FAILED",
                "LOGIN FAILED",
                false);
    }

    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }
}