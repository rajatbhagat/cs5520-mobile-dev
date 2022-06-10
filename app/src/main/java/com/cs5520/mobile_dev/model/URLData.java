package com.cs5520.mobile_dev.model;

public class URLData {

    private String url;
    private String urlName;

    public URLData(String url, String urlName) {
        this.url = url;
        this.urlName = urlName;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlName() {
        return urlName;
    }
}
