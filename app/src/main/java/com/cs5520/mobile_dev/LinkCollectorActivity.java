package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs5520.mobile_dev.model.URLData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LinkCollectorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<URLData> urlDataList = new ArrayList<>();
    private URLAdapter urlAdapter;
    private FloatingActionButton fab;
    private Button submitButton;
    private EditText urlEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        urlDataList.add(new URLData("https://www.google.com"));

        urlAdapter = new URLAdapter(getApplicationContext(), urlDataList);
        recyclerView = (RecyclerView) findViewById(R.id.link_collector_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(urlAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickHelper(getApplicationContext(), new RecyclerViewItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlDataList.get(position).getUrl()));
                startActivity(intent);
            }
        }));
        View parentLayout = findViewById(android.R.id.content);
        fab = (FloatingActionButton) findViewById(R.id.link_collector_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LinkCollectorActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.url_adder_dialog_box, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                Button submitButton = dialogView.findViewById(R.id.dialog_submit_button);
                EditText editText = dialogView.findViewById(R.id.url_edit_text);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = editText.getText().toString();
                        String snackbarMessage = "";
                        String regexToValidateURL = "((http|https)://)(www.)?"
                                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                                + "{2,256}\\.[a-z]"
                                + "{2,6}\\b([-a-zA-Z0-9@:%"
                                + "._\\+~#?&//=]*)";
                        Pattern pattern = Pattern.compile(regexToValidateURL);
                        if (pattern.matcher(url).matches()) {
                            snackbarMessage = "URL Created Successfully.";
                            urlDataList.add(new URLData(editText.getText().toString()));
                        } else {
                            snackbarMessage = "Error while creating URL. Please enter URL like : https://www.example.com";
                        }
                        alertDialog.dismiss();
                        Snackbar.make(parentLayout, snackbarMessage, Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                });
                alertDialog.show();
            }
        });

//        urlEditText = (EditText) findViewById(R.id.url_edit_text);
//        submitButton = (Button) findViewById(R.id.dialog_submit_button);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(LinkCollectorActivity.this, urlEditText.getText(), Toast.LENGTH_LONG).show();
//            }
//        });


    }
}