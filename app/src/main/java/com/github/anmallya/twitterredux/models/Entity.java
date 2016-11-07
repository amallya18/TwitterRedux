package com.github.anmallya.twitterredux.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by anmallya on 10/29/2016.
 */
@Parcel
public class Entity {
    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    private List<Media> media;

    public List<Extended> getExtendedList() {
        return extendedList;
    }

    public void setExtendedList(List<Extended> extendedList) {
        this.extendedList = extendedList;
    }

    @SerializedName("extended_entities")
    private List<Extended> extendedList;

}
