package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class FindPrimeActivity extends AppCompatActivity {

    private static final String TAG = "FindPrimeActivity";

    private TextView currentNumTextView;
    private TextView latestPrimeTextView;
    private Handler textHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);
        currentNumTextView = (TextView) findViewById(R.id.current_num_text);
        currentNumTextView.setText("2");
        latestPrimeTextView = (TextView) findViewById(R.id.latest_prime_text);

        ChildThread childThread = new ChildThread();
        new Thread(childThread).start();
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
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
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

    protected class ChildThread implements Runnable {

        @Override
        public void run() {
            int originalNum = 2;
            while (true) {
                boolean ans = isPrime(originalNum);
                if (ans) {
                    int finalOriginalNum = originalNum;
                    textHandler.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            latestPrimeTextView.setText("" + finalOriginalNum);
                        }
                    });
                    Log.d(TAG, "Running on a different thread using Runnable Interface: " + finalOriginalNum);
                }
                originalNum++;
                try {
                    Thread.sleep(1000);
                    currentNumTextView.setText(originalNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}