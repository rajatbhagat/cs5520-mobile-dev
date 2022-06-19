package com.cs5520.mobile_dev;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

public class LocationSensingActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sensing);
        requestPermissionForLocation();
    }

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