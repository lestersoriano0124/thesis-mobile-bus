package com.example.transporte_pay.views.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.transporte_pay.data.model.User;
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
    String test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        profilePic = findViewById(R.id.prof_pic);

//        Intent intent = getIntent();
//        if(intent.getExtras() != null){
//            user = (User) intent.getSerializableExtra("token");
//            Log.e("TOKEN", "************" + user);
//        }

        sessionManager = new SessionManager(getApplicationContext());

        sessionManager.checkLogin();

        HashMap<String, String> userData = sessionManager.getUSerDetails();
        String getToken = userData.get(SessionManager.PREF_USER_TOKEN);
        String getEmail = userData.get(SessionManager.EMAIL);
        email.setText(getEmail);
        test = userData.get(SessionManager.NAME);

        Log.e("TOKEN MAIN ACTIVITY", "************ " + getToken);
        //WELCOME MESSAGE
        Toast.makeText(this, "WELCOME "+test, Toast.LENGTH_SHORT ).show();

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
        showPassengerDash();
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

    private void showPassengerDash() {
        PassengerDashboardFragment passengerDashboardFragment = new PassengerDashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        Bundle data = new Bundle();
        data.putString("message", "Hey Buddy");

        passengerDashboardFragment.setArguments(data);
        fragmentTransaction.replace(R.id.dashboard, passengerDashboardFragment).commit();
    }

    private void signOut() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
//        sessionManager.isLoggedIn(false);
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
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        signOut();
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}