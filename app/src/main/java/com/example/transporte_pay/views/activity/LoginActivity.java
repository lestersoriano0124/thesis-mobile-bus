package com.example.transporte_pay.views.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.request.LoginRequest;
import com.example.transporte_pay.data.response.LoginResponse;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView reg_link;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText email,password;
    ApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiClient = new ApiClient();
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        reg_link = findViewById(R.id.register_link);
        signInButton = findViewById(R.id.signInButton);
        Toast.makeText(this, "ON CREATE", Toast.LENGTH_LONG).show();

        reg_link.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        configGSignIn();

        signInButton.setOnClickListener(v -> signIn());

        getActivityResultLauncher();

//        userLogin(email.getText().toString(), password.getText().toString());
    }

    private void userLogin(String email, String password) {

        apiClient.getApiService().login(new LoginRequest(email,password))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        assert loginResponse != null;
                        if(loginResponse.statusCode == 200) {
                            SessionManager.saveAuthToken(loginResponse.authToken);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                        Log.e("TAG", "=======onFailure: " + t.toString());
                        t.printStackTrace();
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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(this, "SIGN IN SUCCESSFULLY", Toast.LENGTH_LONG).show();
            }


        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());

        }


    }


}