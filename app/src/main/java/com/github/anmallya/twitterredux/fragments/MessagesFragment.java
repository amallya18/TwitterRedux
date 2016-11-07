package com.github.anmallya.twitterredux.fragments;

import android.os.Bundle;
import android.util.Log;

import com.github.anmallya.twitterredux.models.Entity;
import com.github.anmallya.twitterredux.models.Media;
import com.github.anmallya.twitterredux.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by anmallya on 11/5/2016.
 */

public class MessagesFragment extends TweetsFragment {

    @Override
    protected void getTweets() {
        client.getDirectMessages(max, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                processTweetJson(json, false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }

    @Override
    protected void processTweetJson(JSONArray json, boolean save){
        ArrayList<Tweet> tweetListNew = Tweet.getDirectMsgList(json.toString());
        tweetList.addAll(tweetListNew);
        tweetsAdapter.notifyDataSetChanged();
        if (tweetListNew.size() > 0){
            max = tweetListNew.get(tweetListNew.size() - 1).getId();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        avi.hide();
    }

    public static MessagesFragment newInstance(int page) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public MessagesFragment() {
    }
}
