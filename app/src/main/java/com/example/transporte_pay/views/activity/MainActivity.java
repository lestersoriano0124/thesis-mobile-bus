package com.example.transporte_pay.views.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.transporte_pay.views.fragment.ConductorDashboardFragment;
import com.example.transporte_pay.views.fragment.DriverDashboardFragment;
import com.example.transporte_pay.views.fragment.PassengerDashboardFragment;
import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    TextView email;
    ImageView profilePic;
    SessionManager sessionManager;
    String test, getEmail, token;
    Integer getRoleID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        profilePic = findViewById(R.id.prof_pic);

        sessionManager = new SessionManager(getApplicationContext());

        sessionManager.checkLogin();

        HashMap<String, String> userData = sessionManager.getUSerDetails();
        getEmail = userData.get(SessionManager.EMAIL);
        token = userData.get(SessionManager.PREF_USER_TOKEN);
//        email.setText(getEmail);
        email.setText("Welcome ! "+userData.get(SessionManager.NAME));
        test = userData.get(SessionManager.NAME);

        HashMap<String, Integer> ids = sessionManager.getID();
        getRoleID = ids.get(SessionManager.ROLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            String personEmail = account.getEmail();
            Uri personPhoto = account.getPhotoUrl();
            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(profilePic);
        }
        // showPassengerDash();

//        Roles
//        1 admin
//        2 Bus Driver
//        3 Conductor
//        4 Passenger

        switch (getRoleID){
            case 2: showDriverDash();
                break;
            case 3: showConductorDash();
                break;
            case 4: showPassengerDash();
                break;
            default:
                Log.e("", "no case");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            signOut();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showDriverDash() {
        DriverDashboardFragment driverDashboardFragment = new DriverDashboardFragment();
        fragmentTrans(driverDashboardFragment);
    }

    private void showConductorDash() {
        ConductorDashboardFragment conductorDashboardFragment = new ConductorDashboardFragment();
        fragmentTrans(conductorDashboardFragment);
    }

    private void fragmentTrans(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.dashboard,fragment).commit();
    }

    private void showPassengerDash() {
        PassengerDashboardFragment passengerDashboardFragment = new PassengerDashboardFragment();
        fragmentTrans(passengerDashboardFragment);
        Bundle data = new Bundle();
        data.putString("message", "Hey Buddy");

        passengerDashboardFragment.setArguments(data);
//        fragmentTransaction.replace(R.id.dashboard, passengerDashboardFragment).commit();
    }

    private void signOut() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.logoutUser();
        mGoogleSignInClient.signOut()
        .addOnCompleteListener(this, task -> {
            Toast.makeText(MainActivity.this, "USER SIGNED OUT SUCCESSFULLY", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        });
    }

    @Override
    public void onBackPressed() {
        dialogBox();
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to Log Out?");
        alertDialogBuilder.setPositiveButton("Ok",
                (arg0, arg1) -> signOut());

        alertDialogBuilder.setNegativeButton("cancel",
                (arg0, arg1) -> {

                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}