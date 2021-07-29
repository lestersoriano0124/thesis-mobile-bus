package com.example.transporte_pay.views.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


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
import com.example.transporte_pay.PassengerDashboardFragment;
import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.UserClient;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    Toast.makeText(MainActivity.this, "USER SIGNED OUT SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                });
    }
}