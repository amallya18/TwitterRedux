package com.github.anmallya.twitterredux.fragments;

import android.os.Bundle;
import android.util.Log;

import com.github.anmallya.twitterredux.models.Entity;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.utils.Consts;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by anmallya on 11/5/2016.
 */

public class HomeFragment extends TweetsFragment{

    @Override
    protected void getTweets() {
            client.getTweetTimelineList(max, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    processTweetJson(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                    handleNetworkFailure(statusCode, throwable);
                }
            });
    }

    public static HomeFragment newInstance(int page) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
    }

    public void postTweet(String newTweet, User loggedInUser){
        Tweet t = new Tweet();
        t.setText(newTweet);
        t.setUser(loggedInUser);
        t.setEntities(new Entity());
        t.setCreatedAt(Consts.JUST_NOW);
        tweetList.add(0,t);
        tweetsAdapter.notifyDataSetChanged();
    }
}


