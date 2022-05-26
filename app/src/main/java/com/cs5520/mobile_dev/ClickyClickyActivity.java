package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cs5520.mobile_dev.model.DisplayData;

public class ClickyClickyActivity extends AppCompatActivity {

    private static final String TAG = "ClickyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        DisplayData data = new DisplayData(null);
        Log.e(TAG, "Creating the ClickyActivity");
    }
}