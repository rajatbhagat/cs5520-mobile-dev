package com.cs5520.mobile_dev.model;

import java.net.URL;

public class AnimeData {

    private String animeName;
    private String animeSynopsis;
    private int animeScore;
    private String myAnimeUrl;
    private String animeImageURL;
    private String animeType;
    private String animeStatus;

    public AnimeData() {
//        Default empty constructor
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getAnimeSynopsis() {
        return animeSynopsis;
    }

    public void setAnimeSynopsis(String animeSynopsis) {
        this.animeSynopsis = animeSynopsis;
    }

    public int getAnimeScore() {
        return animeScore;
    }

    public void setAnimeScore(int animeScore) {
        this.animeScore = animeScore;
    }

    public String getMyAnimeUrl() {
        return myAnimeUrl;
    }

    public void setMyAnimeUrl(String myAnimeUrl) {
        this.myAnimeUrl = myAnimeUrl;
    }

    public String getAnimeImageURL() {
        return animeImageURL;
    }

    public void setAnimeImageURL(String animeImageURL) {
        this.animeImageURL = animeImageURL;
    }

    public String getAnimeType() {
        return animeType;
    }

    public void setAnimeType(String animeType) {
        this.animeType = animeType;
    }

    public String getAnimeStatus() {
        return animeStatus;
    }

    public void setAnimeStatus(String animeStatus) {
        this.animeStatus = animeStatus;
    }

    @Override
    public String toString() {
        return "AnimeData{" +
                "animeName='" + animeName + '\'' +
                ", animeSynopsis='" + animeSynopsis + '\'' +
                ", animeScore=" + animeScore +
                ", myAnimeUrl=" + myAnimeUrl +
                ", animeImageURL=" + animeImageURL +
                ", animeType='" + animeType + '\'' +
                ", animeStatus='" + animeStatus + '\'' +
                '}';
    }
}
