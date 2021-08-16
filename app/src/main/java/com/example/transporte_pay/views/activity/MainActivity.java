package com.example.transporte_pay.views.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.transporte_pay.views.fragment.PassengerDashboardFragment;
import com.example.transporte_pay.R;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    TextView email;
    ImageView profilePic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.email);
        profilePic = findViewById(R.id.prof_pic);

        String token1 = SessionManager.PREF_USER_TOKEN;

        Toast.makeText(this, "TOKEN" + token1 , Toast.LENGTH_LONG).show();

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
        sessionManager.setLogin(false);
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