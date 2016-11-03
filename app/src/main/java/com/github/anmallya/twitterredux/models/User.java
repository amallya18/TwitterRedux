package com.github.anmallya.twitterredux.models;

import com.github.anmallya.twitterredux.data.TweetsDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Created by anmallya on 10/28/2016.
 */

@Parcel
@Table(database = TweetsDatabase.class)
public class User extends BaseModel {

    @Column
    @SerializedName("screen_name")
    @PrimaryKey
    private String screenName; // screen_name

    @Column
    @SerializedName("name")
    private String name; // name

    @Column
    @SerializedName("description")
    private String description; // description

    @Column
    @SerializedName("url")
    private String url; // url

    @Column
    @SerializedName("followers_count")
    private long followersCount; //followers_count

    @Column
    @SerializedName("friends_count")
    private long friendsCount; // friends_count

    @Column
    @SerializedName("listed_count")
    private long listedCount; //listed_count

    @Column
    @SerializedName("favourites_count")
    private long favoritesCount; // favourites_count

    @Column
    @SerializedName("verified")
    private boolean verified; // verified

    @Column
    @SerializedName("statuses_count")
    private long statusesCount; // statuses_count

    @Column
    @SerializedName("profile_background_image_url")
    private String profileBackgroundImageUrl;  //profile_background_image_url

    @Column
    @SerializedName("profile_background_color")
    private String profileBackgroundColor; // profile_background_color

    @Column
    @SerializedName("profile_image_url_https")
    private String profileImageUrl; // profile_image_url_https

    @Column
    @SerializedName("profile_banner_url")
    private String bannerUrl; // banner_url

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(long friendsCount) {
        this.friendsCount = friendsCount;
    }

    public long getListedCount() {
        return listedCount;
    }

    public void setListedCount(long listedCount) {
        this.listedCount = listedCount;
    }

    public long getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(long favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public long getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(long statusesCount) {
        this.statusesCount = statusesCount;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
