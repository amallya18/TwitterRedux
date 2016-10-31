package com.github.anmallya.twitterclient.models;

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
}
