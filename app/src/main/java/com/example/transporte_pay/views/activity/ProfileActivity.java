package com.example.transporte_pay.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.data.request.UpdateUserPass;
import com.example.transporte_pay.data.request.UpdateUserRequest;
import com.example.transporte_pay.utils.AlertDialogManager;
import com.example.transporte_pay.utils.ChangePassDialog;
import com.example.transporte_pay.utils.SessionManager;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements ChangePassDialog.DialogListener {
    TextView name, email, changePassLink;
    EditText currentPass, newPass, confirmPass;
    String uName, uEmail,token, gID, uCurrent, uConfirm, uNew;
    int id, role_id;
    Button updateBtn;
    ProgressBar loading;
    AlertDialogManager alert;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.prof_name);
        email = findViewById(R.id.prof_email);
        changePassLink = findViewById(R.id.changePass_link);
        updateBtn = findViewById(R.id.profUpdate_btn);
        loading = findViewById(R.id.progressBar_update);
        currentPass = findViewById(R.id.currentPass_et);
        newPass = findViewById(R.id.newPass_et);
        confirmPass = findViewById(R.id.confirmPass_et);


        alert = new AlertDialogManager();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, String> hash = sessionManager.getUSerDetails();
        uName = hash.get(SessionManager.NAME);
        uEmail = hash.get(SessionManager.EMAIL);
        token = hash.get(SessionManager.PREF_USER_TOKEN);
        gID = hash.get(SessionManager.G_ID);

        name.setText(uName);
        email.setText(uEmail);

        HashMap<String, Integer> hash1 = sessionManager.getID();
        id = hash1.get(SessionManager.ID);
        role_id = hash1.get(SessionManager.ROLE);

        updateBtn.setOnClickListener(v -> {
            if(name.length()<10){
                alert.showAlertDialog(ProfileActivity.this,
                        "FAILED",
                        "Please Provide your Full Name",
                        false);
            }
            openUpdateData();
        });
        changePassLink.setOnClickListener(v -> {
            openDialog();
        });
    }

    private void openUpdateData() {
        UpdateUserRequest updateUserRequestRequest = new UpdateUserRequest();
        updateUserRequestRequest.setName(capitalize(name.getText().toString()));
        updateUserRequestRequest.setEmail(email.getText().toString());
        updateUserRequestRequest.setId(id);
        updateUserRequestRequest.setRole_id(role_id);
        loading.setVisibility(View.VISIBLE);

        Call<User> profileResponseCall = ApiClient.getUserClient().
                updateData(updateUserRequestRequest,"Bearer " + token);

        profileResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ProfileActivity.this,"Name/Email Updated", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    alert.showAlertDialog(ProfileActivity.this,
                            "SUCCESS",
                            "Name/Email Updated",
                            true);
                    User user = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String getName = user.getName();
                            String getEmail = user.getEmail();
                           sessionManager.updateData(getName,getEmail);
                        }
                    }, 300);
                }else {
                    Toast.makeText(ProfileActivity.this,"UPDATE FAILED", Toast.LENGTH_LONG).show();
                    alert.showAlertDialog(ProfileActivity.this,
                            "UPDATE FAILED",
                            "Please try again",
                            false);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", "signInResult:failed code=" +t.getMessage());
                gotoErrorMessage();
            }
        });
    }

    private void gotoErrorMessage() {
        alert.showAlertDialog(ProfileActivity.this,
                "ERROR",
                "Please try Again",
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

    private void openDialog() {
        ChangePassDialog passDialog = new ChangePassDialog();
        passDialog.show(getSupportFragmentManager(), "example dialog");
    }

    private void openUpdatePass() {
        UpdateUserPass updateUserPass = new UpdateUserPass();
        updateUserPass.setOld_password(uCurrent);
        updateUserPass.setNew_password(uNew);
        updateUserPass.setNew_password_confirmation(uConfirm);

        Call<User> userCall = ApiClient.getUserClient().updatePass(updateUserPass,"Bearer " + token );
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alert.showAlertDialog(ProfileActivity.this,
                                    "SUCCESS",
                                    "Password Updated",
                                    true);
                        }
                    }, 300);
                }else {
                    Toast.makeText(ProfileActivity.this,"UPDATE FAILED", Toast.LENGTH_LONG).show();
                    alert.showAlertDialog(ProfileActivity.this,
                            "CHANGE PASSWORD FAILED",
                            "Please try again",
                            false);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", "signInResult:failed code=" +t.getMessage());
                gotoErrorMessage();
            }
        });
    }

    @Override
    public void applyTexts(String currentPass, String newPassword, String confirmPass) {
        Log.e("CHANGE PASSWORD", "DETAILS: " + currentPass + newPassword +confirmPass);
        uCurrent = currentPass;
        uNew = newPassword;
        uConfirm = confirmPass;
        Log.e("VIEW IF NULL", "DETAILS: " + uCurrent + uNew + uConfirm);
        openUpdatePass();
    }
}