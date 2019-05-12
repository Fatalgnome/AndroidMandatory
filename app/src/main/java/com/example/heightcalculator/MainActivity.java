package com.example.heightcalculator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Set;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng curPos;
    private TextView text;
    private Context context;
    private GetJSON getJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.meterValue);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }
        context = this;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        FragmentManager fm = getSupportFragmentManager();
        DatabaseFragment datFrag = (DatabaseFragment)getSupportFragmentManager().findFragmentById(R.id.database);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_info:

                        break;
                    case R.id.action_location:
                        Toast.makeText(MainActivity.this, "Location", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_list:
                        Toast.makeText(MainActivity.this, "List", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getLocation(findViewById(R.id.updateBtn));
                }
                else
                {
                    return;
                }
                break;
        }
    }

    public void getLocation(View view)
    {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            if (location != null)
                            {
                                curPos = new LatLng(location.getLatitude(), location.getLongitude());
                            }

                            if(curPos != null)
                            {
                                mMap.addMarker(new MarkerOptions().position(curPos).title("Current location"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPos, 15));
                                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                                getJSON = new GetJSON((Activity) context, text, new LatLng(curPos.latitude, curPos.longitude));
                                getJSON.StartClient();
                            }
                        }
                    });
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1 );
        }
    }
}
