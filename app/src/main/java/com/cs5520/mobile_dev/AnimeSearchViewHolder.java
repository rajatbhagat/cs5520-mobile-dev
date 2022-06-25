package com.cs5520.mobile_dev;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.AnimeData;
import com.cs5520.mobile_dev.model.URLData;

public class AnimeSearchViewHolder extends RecyclerView.ViewHolder {

    private TextView animeTitleTextView;
    private TextView animeTypeTextView;
    private ImageView animeImageView;

    public AnimeSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        animeImageView = (ImageView) itemView.findViewById(R.id.anime_card_view_recycler_image_view);
        animeTitleTextView = (TextView) itemView.findViewById(R.id.anime_card_view_recycler_text_view_title);
        animeTypeTextView = (TextView) itemView.findViewById(R.id.anime_card_view_recycler_type_text_view);
    }

    public void bindData(AnimeData anime) {
        animeTitleTextView.setText(anime.getAnimeName());
        animeTypeTextView.setText(anime.getAnimeType());
        animeImageView.setImageURI(Uri.parse(anime.getAnimeImageURL()));
    }
}
