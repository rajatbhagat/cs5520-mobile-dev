package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView aboutMe = (TextView) findViewById(R.id.aboutme_textView);
        aboutMe.setText("Name: Rajat Manish Bhagat \n Email: bhagat.r@northeastern.edu");
    }
}