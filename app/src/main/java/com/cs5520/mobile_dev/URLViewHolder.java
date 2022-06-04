package com.cs5520.mobile_dev;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.URLData;

public class URLViewHolder extends RecyclerView.ViewHolder {

    private TextView urlTextView;

    public URLViewHolder(@NonNull View itemView) {
        super(itemView);
        urlTextView = (TextView) itemView.findViewById(R.id.url_view_text_view);
    }

    public void bindData(URLData singleData) {
        urlTextView.setText(singleData.getUrl());
    }
}
