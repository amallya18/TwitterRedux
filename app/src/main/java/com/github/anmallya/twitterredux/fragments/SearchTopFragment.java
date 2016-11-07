package com.github.anmallya.twitterredux.fragments;

import android.os.Bundle;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/**
 * Created by anmallya on 11/5/2016.
 */

public class SearchTopFragment extends TweetsFragment{

    private String searchType;
    protected static final String SCR_TYPE = "SCR_TYPE";
    protected static final String QUERY = "QUERY";
    private String searchTerm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchType = getArguments().getString(SCR_TYPE);
            searchTerm = getArguments().getString(QUERY);
        }
    }

    @Override
    protected void getTweets() {
        client.getsearchList(searchTerm, searchType, max, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("statuses");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                processTweetJson(jsonArray, false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                handleNetworkFailure(statusCode, throwable);
            }
        });
    }

    public static SearchTopFragment newInstance(int page, String searchType, String query) {
        SearchTopFragment fragment = new SearchTopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(SCR_TYPE, searchType);
        args.putString(QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    public void getSearchTweets(String searchTerm){
        getTweets();

    }

    public SearchTopFragment() {
    }
}
