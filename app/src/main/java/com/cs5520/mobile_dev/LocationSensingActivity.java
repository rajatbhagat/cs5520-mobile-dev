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
import android.view.View;
import android.widget.Button;
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

public class LocationSensingActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private TextView distanceTextView;
    private LocationRequest locationRequest;
    private Location firstLocation;
    private Location secondLocation;
    private float distance = 0;

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

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.high_acc_radio) {
                    if (!isFineLocationPermissionGranted()) {
                        ActivityCompat.requestPermissions(LocationSensingActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                99);
                    }
                    mFusedLocationClient = null;
                    locationRequest = createLocationRequest(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                    getInitialLocationData();

                }

                if(checkedId == R.id.coarse_Acc_Radio) {
                    mFusedLocationClient = null;
                    locationRequest = createLocationRequest(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                    getInitialLocationData();

                }
            }
        });

        Button resetDistanceButton = (Button) findViewById(R.id.reset_distance_button);
        resetDistanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance =0;
                distanceTextView.setText("0");
            }
        });

        if (isApproxLocationPermissionGranted()) {
            locationRequest = createLocationRequest(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            getLocationData();
        }


    }

    private boolean isApproxLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isFineLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (isFineLocationPermissionGranted() || isApproxLocationPermissionGranted()) {
            getLocationData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    private void getLocationData() {
//        if (isPermissionGranted()) {
            if (isGpsEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            getInitialLocationData();
                        } else {
                            System.out.println("Found location data");
                            firstLocation = location;
                            latitudeTextView.setText(String.valueOf(location.getLatitude()));
                            longitudeTextView.setText(String.valueOf(location.getLongitude()));
                            startLocationUpdates();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please turn on your location", Toast.LENGTH_LONG).show();
            }
//        }
//        else {
//            requestPermissionForLocation();
//        }
    }

    private LocationRequest createLocationRequest(int locationPriority) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(locationPriority);
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        return locationRequest;
    }

    @SuppressLint("MissingPermission")
    private void getInitialLocationData() {
        System.out.println("Inital location update");
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        System.out.println("Starting location update");
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                mLocationCallback,
                Looper.getMainLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            System.out.println(locationRequest.getPriority());
            System.out.println("High " + String.valueOf(LocationRequest.PRIORITY_HIGH_ACCURACY));
            System.out.println("Balance " + String.valueOf(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY));
            if (firstLocation != null) {
                float[] distanceArr = new float[1];
                Location.distanceBetween(firstLocation.getLatitude(), firstLocation.getLongitude(), mLastLocation.getLatitude(), mLastLocation.getLongitude(), distanceArr);
                distance += distanceArr[0];
                distanceTextView.setText(String.valueOf(distance));
            } else {
                firstLocation = mLastLocation;
            }
            latitudeTextView.setText(String.valueOf(mLastLocation.getLatitude()));
            longitudeTextView.setText(String.valueOf(mLastLocation.getLongitude()));
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
                    Toast.makeText(getApplicationContext(), "Fine Location Access Granted", Toast.LENGTH_LONG).show();
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Only approximate location access granted.
                    Toast.makeText(getApplicationContext(), "Coarse Location Access Granted", Toast.LENGTH_LONG).show();
                } else {
                    // No location access granted.
                    Toast.makeText(getApplicationContext(), "Location Access Not granted", Toast.LENGTH_LONG).show();
                }
            }
            );

            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });

    }
}