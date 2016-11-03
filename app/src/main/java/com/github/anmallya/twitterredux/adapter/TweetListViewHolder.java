package com.github.anmallya.twitterredux.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.anmallya.twitterclient.R;

/**
 * Created by anmallya on 10/21/2016.
 */
public class TweetListViewHolder extends RecyclerView.ViewHolder {

    private ToggleButton ivRetweet;
    private TextView tvProfileName, tvProfileHandler, tvCreatedTime, tvTweet, tvRetweetCount, tvLikeCount;
    private ImageView ivMedia;

    public ImageButton getIvProfilePic() {
        return ivProfilePic;
    }

    public void setIvProfilePic(ImageButton ivProfilePic) {
        this.ivProfilePic = ivProfilePic;
    }

    public ImageButton getIvReply() {
        return ivReply;
    }

    public void setIvReply(ImageButton ivReply) {
        this.ivReply = ivReply;
    }

    public ImageButton getIvDirectMsg() {
        return ivDirectMsg;
    }

    public void setIvDirectMsg(ImageButton ivDirectMsg) {
        this.ivDirectMsg = ivDirectMsg;
    }

    public TextView getTvProfileName() {
        return tvProfileName;
    }

    public void setTvProfileName(TextView tvProfileName) {
        this.tvProfileName = tvProfileName;
    }

    public TextView getTvProfileHandler() {
        return tvProfileHandler;
    }

    public void setTvProfileHandler(TextView tvProfileHandler) {
        this.tvProfileHandler = tvProfileHandler;
    }

    public TextView getTvCreatedTime() {
        return tvCreatedTime;
    }

    public void setTvCreatedTime(TextView tvCreatedTime) {
        this.tvCreatedTime = tvCreatedTime;
    }

    public TextView getTvTweet() {
        return tvTweet;
    }

    public void setTvTweet(TextView tvTweet) {
        this.tvTweet = tvTweet;
    }

    public TextView getTvRetweetCount() {
        return tvRetweetCount;
    }

    public void setTvRetweetCount(TextView tvRetweetCount) {
        this.tvRetweetCount = tvRetweetCount;
    }

    public TextView getTvLikeCount() {
        return tvLikeCount;
    }

    public void setTvLikeCount(TextView tvLikeCount) {
        this.tvLikeCount = tvLikeCount;
    }

    public ImageView getIvMedia() {
        return ivMedia;
    }

    public void setIvMedia(ImageView ivMedia) {
        this.ivMedia = ivMedia;
    }

    public void setIvLike(ToggleButton ivLike) {
        this.ivLike = ivLike;
    }

    private ImageButton ivProfilePic, ivReply, ivDirectMsg;

    public ToggleButton getIvLike() {
        return ivLike;
    }

    private ToggleButton ivLike;

    public ToggleButton getIvRetweet() {
        return ivRetweet;
    }

    public void setIvRetweet(ToggleButton ivRetweet) {
        this.ivRetweet = ivRetweet;
    }


    public TweetListViewHolder(View v) {
        super(v);

        // set text views
        setTvCreatedTime((TextView)v.findViewById(R.id.tv_created_time));
        setTvLikeCount((TextView)v.findViewById(R.id.tv_like_count));
        setTvProfileHandler((TextView)v.findViewById(R.id.tv_profile_handle));
        setTvProfileName((TextView)v.findViewById(R.id.tv_profile_name));
        setTvRetweetCount((TextView)v.findViewById(R.id.tv_retweet_count));
        setTvTweet((TextView)v.findViewById(R.id.tv_tweet));

        // set image views
        setIvDirectMsg((ImageButton)v.findViewById(R.id.iv_direct_msg));
        setIvProfilePic((ImageButton)v.findViewById(R.id.iv_profile_pic));
        setIvReply((ImageButton)v.findViewById(R.id.iv_reply));

        // set toggle buttons
        setIvLike ((ToggleButton)v.findViewById(R.id.iv_like));
        setIvRetweet((ToggleButton)v.findViewById(R.id.iv_retweet));

        setIvMedia((ImageView)v.findViewById(R.id.iv_media));
    }

}
