package com.cs5520.mobile_dev;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs5520.mobile_dev.model.AnimeData;
import com.cs5520.mobile_dev.model.URLData;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

//            URL url = new URL();
            Picasso.get().load(Uri.parse(anime.getAnimeImageURL())).into(animeImageView);
//            animeImageView.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()));

    }
}
