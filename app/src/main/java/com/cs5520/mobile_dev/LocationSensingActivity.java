package com.cs5520.mobile_dev;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LocationSensingActivity extends AppCompatActivity implements LocationListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView distanceTextView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sensing);
        requestPermissionForLocation();

        latitudeTextView = (TextView) findViewById(R.id.current_lat_val);
        longitudeTextView = (TextView) findViewById(R.id.current_long_text);
        distanceTextView = (TextView) findViewById(R.id.current_distance_text);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationData();

    }

    private boolean isPermissionGranted() {
        boolean isFinePermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isCoarsePermissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return isFinePermissionGranted && isCoarsePermissionGranted;
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitudeTextView.setText(location.getLatitude() + "");
        longitudeTextView.setText(location.getLongitude() + "");
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (isPermissionGranted()) {
            getLocationData();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    private void getLocationData() {
        if (isPermissionGranted()) {
            if (isGpsEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            getInitialLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitudeTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please turn on your location", Toast.LENGTH_LONG).show();
            }
        } else {
            requestPermissionForLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getInitialLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText(mLastLocation.getLatitude() + "");
            longitudeTextView.setText(mLastLocation.getLongitude() + "");
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void requestPermissionForLocation() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                    .RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,false);
                if (fineLocationGranted != null && fineLocationGranted) {
                    // Precise location access granted.
                    Toast.makeText(getApplicationContext(), "Fine Granted", Toast.LENGTH_LONG).show();
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Only approximate location access granted.
                    Toast.makeText(getApplicationContext(), "Coarse Granted", Toast.LENGTH_LONG).show();
                } else {
                    // No location access granted.
                    Toast.makeText(getApplicationContext(), "Not granted", Toast.LENGTH_LONG).show();
                }
            }
            );

        String fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarseLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        int locationPermissionTest = getApplicationContext().checkCallingOrSelfPermission(fineLocationPermission);
        int coarseLocationTest = getApplicationContext().checkCallingOrSelfPermission(coarseLocationPermission);

        if (locationPermissionTest != PackageManager.PERMISSION_GRANTED
                || coarseLocationTest !=  PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }
}