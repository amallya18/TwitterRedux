package com.github.anmallya.twitterredux.models;

import com.github.anmallya.twitterredux.data.TweetsDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Created by anmallya on 10/29/2016.
 */
@Parcel
@Table(database = TweetsDatabase.class)
public class Media extends BaseModel{

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    @Column
    private long tweetId;


    @Column
    @SerializedName("type")
    private String type;

    @Column
    @PrimaryKey
    @SerializedName("id")
    private String id;

    @Column
    @SerializedName("media_url")
    private String mediaUrl;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl){
        this.mediaUrl = mediaUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
