package com.example.transporte_pay.views.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.api.ApiClient;
import com.example.transporte_pay.data.model.User;
import com.example.transporte_pay.data.request.ConductorRequest;
import com.example.transporte_pay.data.request.LongLatRequest;
import com.example.transporte_pay.utils.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.transporte_pay.databinding.ActivityConductorMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConductorMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityConductorMapsBinding binding;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    SessionManager sessionManager;
    private String id,token;
    public String busId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        HashMap<String, Integer> hash = sessionManager.getID();
        id = String.valueOf(hash.get(SessionManager.ID));
        HashMap<String, String> user = sessionManager.getUSerDetails();
        token = user.get(SessionManager.PREF_USER_TOKEN);

        binding = ActivityConductorMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.conductormap);

        client = LocationServices.getFusedLocationProviderClient(this);
//        mapFragment.getMapAsync(this);

        ConductorRequest conductorRequest = new ConductorRequest();
        conductorRequest.setUserId(id);
        conductorRequest.setHealth("get");

        Call<ConductorRequest> conductorRequestCall = ApiClient.getConductorClient().getConductorBusDetail(conductorRequest,"Bearer :"+token);
        conductorRequestCall.enqueue(new Callback<ConductorRequest>() {
            @Override
            public void onResponse(Call<ConductorRequest> call, Response<ConductorRequest> response) {
                if(response.body().isStatus()){

                        ConductorRequest user = response.body();
                        busId                 = user.getBusId();
                    Toast.makeText(ConductorMapsActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ConductorMapsActivity.this,response.body().getRemarks(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ConductorRequest> call, Throwable t) {

            }
        });









        if (ActivityCompat.checkSelfPermission(ConductorMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission granted


            getCurrentLocation();


        } else {
            ActivityCompat.requestPermissions(ConductorMapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation() {
        @SuppressLint("MissingPermission") Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            float longitude = (float) location.getLongitude();
                            float latitude = (float) location.getLatitude();
                            Log.d("Long",String.valueOf( longitude)  );


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ConductorRequest cR = new ConductorRequest();
                                    cR.setBusId(busId);
                                    cR.setLongitude(String.valueOf(longitude));
                                    cR.setLatitude(String.valueOf(latitude));
                                    cR.setHealth("update");

                                    Call<ConductorRequest> crCall = ApiClient.getConductorClient().getConductorBusDetail(cR,"Bearer :" +token);
                                    crCall.enqueue(new Callback<ConductorRequest>() {
                                        @Override
                                        public void onResponse(Call<ConductorRequest> call, Response<ConductorRequest> response) {
                                            ConductorRequest res = response.body();
                                            if(res.isStatus()){

                                                Toast.makeText(ConductorMapsActivity.this,res.getRemarks(),Toast.LENGTH_SHORT).show();

                                            }else{
                                                Toast.makeText(ConductorMapsActivity.this,res.getRemarks(),Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ConductorRequest> call, Throwable t) {

                                        }
                                    });

                                }
                            },700);




                            MarkerOptions options = new MarkerOptions().position(latLng).title("Current Location");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                            googleMap.addMarker(options);




                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(), "Please Turn on Your location", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ConductorMapsActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }

        }
    }
}