package com.example.heightcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationListener;
import android.location.LocationManager;
import android.webkit.HttpAuthHandler;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng position = new LatLng(21, -30);
    private String elevationBaseUrl = "https://maps.googleapis.com/maps/api/elevation/json?";
    private String curLocation = "locations=" + new LatLng(position.latitude,position.longitude);
    private String key = "&key=" + R.string.google_maps_key;
    private String completeUrl = elevationBaseUrl + curLocation + key;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker on your current position and move the camera
        //TODO: Use current position
        LatLng mapsPos = new LatLng(-31, 30);
        mMap.addMarker(new MarkerOptions().position(mapsPos).title("Current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapsPos));


    }

}
