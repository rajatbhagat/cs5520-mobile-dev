package com.cs5520.mobile_dev;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.URLData;

public class URLViewHolder extends RecyclerView.ViewHolder {

    private TextView urlTextView;
    private TextView urlNameTextView;

    public URLViewHolder(@NonNull View itemView) {
        super(itemView);
        urlTextView = (TextView) itemView.findViewById(R.id.url_view_text_view);
        urlNameTextView = (TextView) itemView.findViewById(R.id.url_name);
    }

    public void bindData(URLData singleData) {
        urlTextView.setText(singleData.getUrl());
        urlNameTextView.setText(singleData.getUrlName());
    }
}
