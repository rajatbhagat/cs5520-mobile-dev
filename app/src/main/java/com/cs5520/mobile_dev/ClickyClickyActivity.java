package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs5520.mobile_dev.model.DisplayData;

public class ClickyClickyActivity extends AppCompatActivity {

    private static final String TAG = "ClickyActivity";
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;
    private Button buttonE;
    private Button buttonF;
    private TextView displayTextView;
    private DisplayData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        data = new DisplayData(null);
        Log.e(TAG, "Creating the ClickyActivity");

        displayTextView = (TextView) findViewById(R.id.textview_second);
        displayTextView.setText(data.getPressedButtonData());

        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonD = (Button) findViewById(R.id.buttonD);
        buttonE = (Button) findViewById(R.id.buttonE);
        buttonF = (Button) findViewById(R.id.buttonF);

        buttonA.setOnClickListener(this::onClick);
        buttonB.setOnClickListener(this::onClick);
        buttonC.setOnClickListener(this::onClick);
        buttonD.setOnClickListener(this::onClick);
        buttonE.setOnClickListener(this::onClick);
        buttonF.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.buttonA) {
            Log.e(TAG, "Button A Pressed");
            data.setPressedButtonData(buttonA.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        } else if (view.getId() == R.id.buttonB) {
            Log.e(TAG, "Button B Pressed");
            data.setPressedButtonData(buttonB.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        } else if (view.getId() == R.id.buttonC) {
            Log.e(TAG, "Button C Pressed");
            data.setPressedButtonData(buttonC.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        } else if (view.getId() == R.id.buttonD) {
            Log.e(TAG, "Button D Pressed");
            data.setPressedButtonData(buttonD.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        } else if (view.getId() == R.id.buttonE) {
            Log.e(TAG, "Button E Pressed");
            data.setPressedButtonData(buttonE.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        } else if (view.getId() == R.id.buttonF) {
            Log.e(TAG, "Button F Pressed");
            data.setPressedButtonData(buttonF.getText().toString());
            displayTextView.setText(data.getPressedButtonData());
        }
    }

}