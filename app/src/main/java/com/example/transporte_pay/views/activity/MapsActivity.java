package com.example.transporte_pay.views.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.transporte_pay.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.transporte_pay.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    String fullname,longitude,latitude,platenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        if (extras != null){

            fullname = (String) extras.getString("fullname");
            longitude = (String) extras.getString("longitude");
            latitude = (String) extras.getString("latitude");
            platenumber = (String) extras.getString("platenumber");
        }

        if (longitude == null || longitude.isEmpty() || longitude.equals("null")){
            Toast.makeText(getApplicationContext(), "Location is not updated by the Conductor", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),TravelLogsActivity.class);
            startActivity(intent);

        }
        if (latitude == null || latitude.isEmpty() || latitude.equals("null")){
            Toast.makeText(getApplicationContext(), "Location is not updated by the Conductor", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),TravelLogsActivity.class);
            startActivity(intent);
        }

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        String title = "Bus :"+platenumber+ " , Passenger :"+fullname;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng( Float.parseFloat(latitude), Float.parseFloat(longitude));

        mMap.addMarker(new MarkerOptions().position(location).title(title));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney),20.0f);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20.0f));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(),TravelLogsActivity.class);
        startActivity(backIntent);
    }
}