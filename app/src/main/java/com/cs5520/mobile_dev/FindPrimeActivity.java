package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FindPrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);
    }

    private boolean isPrime(int num) {
        if (num == 0 || num == 1) {
            return false;
        } else {
            int div = 2;
            while ( div != num) {
                if (num % div == 0) {
                    return false;
                }
                div++;
            }
            return true;
        }
    }
}