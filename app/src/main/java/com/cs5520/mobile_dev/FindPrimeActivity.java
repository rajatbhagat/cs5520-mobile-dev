package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FindPrimeActivity extends AppCompatActivity {

    private static final String TAG = "FindPrimeActivity";

    private TextView currentNumTextView;
    private TextView latestPrimeTextView;
    private Handler textHandler = new Handler();
    private Button findPrimeButton;
    private Button terminateSearchButton;
    private boolean endSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);
        currentNumTextView = (TextView) findViewById(R.id.current_num_text);
        latestPrimeTextView = (TextView) findViewById(R.id.latest_prime_text);

        findPrimeButton = (Button) findViewById(R.id.start_search_button);
        terminateSearchButton = (Button) findViewById(R.id.terminate_search_button);

        findPrimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSearch = false;
                currentNumTextView.setText("2");
                latestPrimeTextView.setText("");
                ChildThread childThread = new ChildThread();
                new Thread(childThread).start();
            }
        });

        terminateSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endSearch = true;
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to terminate the search?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isPrime(int num) {
        if (num == 0 || num == 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int i = 3; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    protected class ChildThread implements Runnable {
        int originalNum = 2;
        @Override
        public void run() {
            while (!endSearch) {
                boolean ans = isPrime(originalNum);
                if (ans) {
                    textHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            latestPrimeTextView.setText(Integer.toString(originalNum));
                        }
                    });
                }
                originalNum++;
                try {
                    textHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            currentNumTextView.setText(Integer.toString(originalNum));
                        }
                    });
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}