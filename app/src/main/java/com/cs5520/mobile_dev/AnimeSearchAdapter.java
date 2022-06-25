package com.cs5520.mobile_dev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.AnimeData;
import com.cs5520.mobile_dev.model.URLData;

import java.util.List;

public class AnimeSearchAdapter extends RecyclerView.Adapter<AnimeSearchViewHolder>{

    private final List<AnimeData> animeDataList;
    private final Context context;

    public AnimeSearchAdapter(List<AnimeData> animeDataList, Context context) {
        this.animeDataList = animeDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public AnimeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimeSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.api_search_output_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeSearchViewHolder holder, int position) {
        holder.bindData(this.animeDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.animeDataList.size();
    }
}
