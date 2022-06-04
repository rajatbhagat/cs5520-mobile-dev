package com.cs5520.mobile_dev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs5520.mobile_dev.model.URLData;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<URLData> urlDataList = new ArrayList<>();
    private URLAdapter urlAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        urlDataList.add(new URLData("https://www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));
        urlDataList.add(new URLData("www.google.com"));

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
    }
}