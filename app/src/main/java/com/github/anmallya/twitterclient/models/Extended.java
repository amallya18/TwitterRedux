package com.github.anmallya.twitterclient.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by anmallya on 10/30/2016.
 */

@Parcel
public class Extended {
    @SerializedName("type")
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    @SerializedName("expanded_url")
    private String expandedUrl;
}
