package com.github.anmallya.twitterclient.models;

import com.github.anmallya.twitterclient.data.TweetsDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anmallya on 10/28/2016.
 */

@Parcel
@Table(database = TweetsDatabase.class)
public class Tweet extends BaseModel {

    @Column
    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id_str")
    private String idStr;

    @Column
    @PrimaryKey
    @SerializedName("id")
    private long id;

    @Column
    @SerializedName("text")
    private String text;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    @SerializedName("user")
    private User user;

    @Column
    @SerializedName("retweet_count")
    private int retweetCount;

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    @Column
    @SerializedName("favorite_count")
    private int favouritesCount;

    @Column
    @SerializedName("favorited")
    private boolean favorited;

    @Column
    @SerializedName("retweeted")
    private boolean retweeted;

    @SerializedName("entities")
    private Entity entities;

    public Entity getEntities() {
        return entities;
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static ArrayList<Tweet> getTweetList(String jArrayString){
        JsonParser parser = new JsonParser();
        JsonElement tweetElement = parser.parse(jArrayString);
        JsonArray jArray = tweetElement.getAsJsonArray();
        ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        int length = jArray.size();
        for(int i = 0; i < length; i ++){
            Gson gson = new Gson();
            Tweet tweet = gson.fromJson(jArray.get(i), Tweet.class);
            tweetList.add(tweet);
        }
        return tweetList;
    }

    @Override
    public String toString(){
        if(getEntities().getMedia() != null){
            if(getEntities().getMedia().size() > 0){
                return getId()+" "+getUser().getScreenName()+" "+getEntities().getMedia().get(0).getMediaUrl();
            }
        }
        return getId()+" "+getUser().getScreenName()+" "+getText()+" "+getFavouritesCount()+" "+getRetweetCount();
    }

}
