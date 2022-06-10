package com.cs5520.mobile_dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.URLData;

import java.util.List;

public class URLAdapter extends RecyclerView.Adapter<URLViewHolder> {

    private final List<URLData> urlDataList;
    private final Context context;

    public URLAdapter(Context context, List<URLData> urlDataList) {
        this.urlDataList = urlDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public URLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new URLViewHolder(LayoutInflater.from(context).inflate(R.layout.url_view_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull URLViewHolder holder, int position) {
        holder.bindData(this.urlDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.urlDataList.size();
    }
}
