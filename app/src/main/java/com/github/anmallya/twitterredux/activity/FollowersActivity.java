package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.adapter.FollowersAdapter;
import com.github.anmallya.twitterredux.adapter.TweetsAdapter;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.helper.EndlessRecyclerViewScrollListener;
import com.github.anmallya.twitterredux.helper.ItemClickSupport;
import com.github.anmallya.twitterredux.models.Entity;
import com.github.anmallya.twitterredux.models.Media;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Consts;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FollowersActivity extends AppCompatActivity {

    private ArrayList<User> followerList;
    private FollowersAdapter followersAdapter;
    private TwitterClient client;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String screenName;
    private String type;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        client = RestApplication.getRestClient();
        followerList = new ArrayList<User>();
        followersAdapter = new FollowersAdapter(this, followerList, client);
        type = getIntent().getStringExtra(Consts.TYPE);
        screenName = getIntent().getStringExtra(Consts.SCREEN_NAME);
        setToolbar();
        setSwipeRefreshLayout();
        setRecyclerView();
        getFollowers();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = type.equals(Consts.FOLLOWING)?"Following":"Followers";
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setElevation(10);
    }

    private void setSwipeRefreshLayout(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followerList.clear();
                followersAdapter.notifyDataSetChanged();
                getFollowers();
            }
        });
    }

    private void processUserJson(JSONObject j){
        JSONArray json = null;
        try {
            json = j.getJSONArray("users");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> tweetListNew = User.getUserList(json.toString());
        followerList.addAll(tweetListNew);
        followersAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getFollowers(){
        if(type.equals(Consts.FOLLOWERS)){
            client.getUserFollowerList(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    System.out.println("List: "+json);
                    processUserJson(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                    Log.d("Failed: ", ""+statusCode);
                    Log.d("Error : ", "" + throwable);
                }
            });
        } else if(type.equals(Consts.FOLLOWING)){
            client.getUserFollowingList(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                    System.out.println("List: "+json);
                    processUserJson(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                    System.out.println("Error: "+j);
                    Log.d("Failed: ", ""+statusCode);
                    Log.d("Error : ", "" + throwable);
                }
            });
        }

    }

    private void setRecyclerView(){
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvTweets);
        rv.setAdapter(followersAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
    }
}
