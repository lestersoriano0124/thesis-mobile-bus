package com.example.transporte_pay.views.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;

import com.example.transporte_pay.data.request.GoogleSignInRequest;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView reg_link;
    SignInButton signInButton;
    Button loginButton;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText email,password;
    String personEmail,personName;
    String personId ;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        reg_link = findViewById(R.id.register_link);
        signInButton = findViewById(R.id.signInButton);
        loginButton = findViewById(R.id.loginButton);
        loading = findViewById(R.id.progressBar);


        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.isLoggedIn();


        reg_link.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this,
                    "YEY",
                    Toast.LENGTH_LONG).show();
        });

        loginButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(LoginActivity.this, "Email/Password is Required", Toast.LENGTH_LONG).show();
            }else {
                gotoLogin();
            }
        });

        configGSignIn();

        signInButton.setOnClickListener(v -> signIn());

        getActivityResultLauncher();



//        userLogin(email.getText().toString(), password.getText().toString());
    }

    private void gotoLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        loading.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        Call<User> loginResponseCall = ApiClient.getUserClient().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"LOGIN SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    User user = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.fetchAuthToken(user.getToken());
                            sessionManager.createSession(user.getName(), user.getEmail(), user.getRole_id(), user.getGoogle_id());

                            startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("id", user.getId()));

                            finish();
                        }
                    }, 300);
                } else {
                    Toast.makeText(LoginActivity.this,"LOGIN FAILED", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Toast.makeText(this, "Yeah", Toast.LENGTH_LONG).show();
        }
    }

    private void getActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInResult(task);
            }
        });
    }

    private void configGSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account != null){
                personEmail = account.getEmail();
                personName = account.getDisplayName();
                personId = account.getId();
                goToGoogleSignIn();
                //goToDash();
            }
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void goToGoogleSignIn() {
        GoogleSignInRequest googleSignInRequest = new GoogleSignInRequest();
        googleSignInRequest.setEmail(personEmail);
        googleSignInRequest.setName(personName);
        googleSignInRequest.setId(personId);

        Call<User> gooResponseCall = ApiClient.getUserClient().createGoggleAccount(googleSignInRequest);
        gooResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                    .putExtra("token", user.getToken()));
                        }
                    }, 700);

                }else {
                    Toast.makeText(LoginActivity.this,"GOOGLE LOGIN FAILED", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.w("error", "signInResult:failed code=" +t.getMessage());
            }
        });
    }
}