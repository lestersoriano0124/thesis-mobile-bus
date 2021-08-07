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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView loginLink;
    Button registerButton;
    EditText name, email, password, c_pass;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginLink = findViewById(R.id.login_link);
        registerButton = findViewById(R.id.reg_btn);
        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        password = findViewById(R.id.reg_password);
        c_pass = findViewById(R.id.reg_confirm_password);
        loading = findViewById(R.id.progressBar_Reg);

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
            }
            else
            {
                gotoRegister();
            }

        });
    }

    private void gotoRegister() {
        RegRequest regRequest = new RegRequest();
        regRequest.setName(name.getText().toString());
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
                    Toast.makeText(RegisterActivity.this,"Account Registered", Toast.LENGTH_LONG).show();
                    User user = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class).putExtra("data", user.getToken()));
                        }
                    }, 300);
                }
                else {
                    Toast.makeText(RegisterActivity.this,"LOGIN FAILED", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
            }
        });
    }
}