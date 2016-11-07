package com.github.anmallya.twitterredux.fragments;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by anmallya on 11/5/2016.
 */


public class UserFavoritesFragment extends TweetsFragment{

    private String screenName;
    protected static final String SCR_NAME = "SCR_NAME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            screenName = getArguments().getString(SCR_NAME);
        }
    }


    @Override
    protected void getTweets() {
        client.getUserLikesTimelineList(screenName, max, new JsonHttpResponseHandler() {
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

    public static UserFavoritesFragment newInstance(int page, String screenName) {
        UserFavoritesFragment fragment = new UserFavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(SCR_NAME, screenName);
        fragment.setArguments(args);
        return fragment;
    }

    public UserFavoritesFragment() {
    }
}