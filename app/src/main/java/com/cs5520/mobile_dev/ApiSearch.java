package com.cs5520.mobile_dev;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs5520.mobile_dev.model.AnimeData;
import com.cs5520.mobile_dev.model.URLData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiSearch extends AppCompatActivity {


    private Button searchButton;
    private TextView displayTextView;
    private RecyclerView recyclerView;
    List<AnimeData> animeDataList = new ArrayList<>();
    private Handler listHandler = new Handler();
    private AnimeSearchAdapter animeSearchAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_search);
        searchButton = (Button) findViewById(R.id.anime_search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog = ProgressDialog.show(ApiSearch.this, "",
                        "Fetching Data...", true);
                dialog.show();
                callWebserviceButtonHandler(v);
            }
        });

        animeSearchAdapter = new AnimeSearchAdapter(animeDataList, getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.api_seaerch_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(animeSearchAdapter);


//        mURLEditText = (EditText)findViewById(R.id.URL_editText);
//        mTitleTextView = (TextView)findViewById(R.id.result_textview);

    }

    public void callWebserviceButtonHandler(View view){
//        PingWebServiceTask task = new PingWebServiceTask();
////         Insert search params in the execute
//        task.execute(); // This is a security risk.  Don't let your user enter the URL in a real app.

        ApiSearch.ChildThread childThread = new ApiSearch.ChildThread();
        new Thread(childThread).start();

    }

    protected class ChildThread implements Runnable {
        int originalNum = 2;

        @Override
        public void run() {
            /**
             * Sample URL to get data: https://api.jikan.moe/v4/anime?q=naruto&type=movie&start_date=2015
             *
             * Search params:
             * name,
             * type: tv, movie, special
             * status: airing, complete, upcoming
             * start_date: (only year)
             */



            URL url = null;
            try {
//                url = new URL("https://api.jikan.moe/v4/anime?q=naruto&type=movie&start_date=2015");
                url = new URL("https://api.jikan.moe/v4/anime?q=naruto");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();

                // Read response.
                InputStream inputStream = conn.getInputStream();
                final String resp = convertStreamToString(inputStream);
                JSONObject jObject = new JSONObject(resp);
                JSONArray jsonArray = jObject.getJSONArray("data");    // Use this if your web service returns an array of objects.  Arrays are in [ ] brackets.
                for (int i = 0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AnimeData data = new AnimeData();
                    data.setAnimeName(jsonObject.get("title").toString());
//                    data.setAnimeScore(Integer.parseInt(jsonObject.get("score").toString()));
                    data.setAnimeStatus(jsonObject.get("status").toString());
                    data.setAnimeType(jsonObject.get("type").toString());
                    data.setAnimeSynopsis(jsonObject.get("synopsis").toString());

                    JSONObject imageObject = jsonObject.getJSONObject("images").getJSONObject("webp");
                    data.setAnimeImageURL(imageObject.get("image_url").toString());
//                    data.setYoutubeTrailerURL(jsonObject.getJSONObject("trailer"));
//
                    animeDataList.add(data);
                }



                listHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                       animeSearchAdapter.updateList(animeDataList);
                       dialog.dismiss();
                    }
                });

            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }

//            return animeDataList;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
        }
    }


    /**
     * Helper function
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

}