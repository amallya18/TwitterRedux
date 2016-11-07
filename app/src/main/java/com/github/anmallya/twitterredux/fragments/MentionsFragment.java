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

public class MentionsFragment extends TweetsFragment {
    @Override
    protected void getTweets() {
            client.getMentionsTimelineList(max, new JsonHttpResponseHandler() {
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

    public static MentionsFragment newInstance(int page) {
        MentionsFragment fragment = new MentionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public MentionsFragment() {
    }
}
