package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.adapter.TweetsAdapter;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.data.DbHelper;
import com.github.anmallya.twitterredux.fragments.ComposeFragment;
import com.github.anmallya.twitterredux.helper.EndlessRecyclerViewScrollListener;
import com.github.anmallya.twitterredux.helper.ItemClickSupport;
import com.github.anmallya.twitterredux.models.Entity;
import com.github.anmallya.twitterredux.models.Media;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.NetworkUtils;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.github.anmallya.twitterclient.models.User_Table.url;

public class TweetListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Tweet> tweetList;
    private TweetsAdapter tweetsAdapter;
    private TwitterClient client;

    private TextView tvNavHeader1, tvNavHeader2;
    private ImageView ivNavHeader; LinearLayout lvNavHeader;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout relativeLayout;

    private Toolbar toolbar;
    private User loggedInUser;
    private ComposeFragment composeDialog;
    private long max = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
        setViews();
        tweetList = new ArrayList<Tweet>();
        client = RestApplication.getRestClient();
        tweetsAdapter = new TweetsAdapter(this, tweetList, client);
        setDrawerViews();
        setSwipeRefreshLayout();
        setRecyclerView();
        getTweets();
        getUserCred();
    }


    private void setViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.root);
        getSupportActionBar().setTitle("Home");
    }

    private void setSwipeRefreshLayout(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                max = -1;
                tweetList.clear();
                tweetsAdapter.notifyDataSetChanged();
                getTweets();
            }
        });
    }

    private void setRecyclerView(){
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvTweets);
        rv.setAdapter(tweetsAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getTweets();
            }
        });
        ItemClickSupport.addTo(rv).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(TweetListActivity.this, TweetDetailActivity.class);
                        intent.putExtra("tweet", Parcels.wrap(tweetList.get(position)));
                        startActivity(intent);
                    }
                }
        );
    }

    private void setDrawerViews(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComposeDialog();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        lvNavHeader = (LinearLayout) hView.findViewById(R.id.lv_nav_header);
        tvNavHeader1 = (TextView) hView.findViewById(R.id.tv_nav_header1);
        tvNavHeader2 = (TextView) hView.findViewById(R.id.tv_nav_header2);
        ivNavHeader = (ImageView) hView.findViewById(R.id.iv_nav_header);
    }


    private void getTweets(){
        client.getTweetTimelineList(max, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
               processTweetJson(json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }

    private void processTweetJson(JSONArray json){
        ArrayList<Tweet> tweetListNew = Tweet.getTweetList(json.toString());
        tweetList.addAll(tweetListNew);
        tweetsAdapter.notifyDataSetChanged();
        for(Tweet tweet:tweetListNew){
            System.out.println(tweet.toString());
            Entity entities = tweet.getEntities();
            if(entities.getMedia() != null){
                if(entities.getMedia().size()>0){
                    for(Media media:entities.getMedia()){
                        media.setTweetId(tweet.getId());
                        media.save();
                    }
                }
            }
            tweet.getUser().save();
            tweet.save();
        }
        max = tweetListNew.get(tweetListNew.size()-1).getId();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    private void getUserCred(){
        client.getCurrentUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
               processUserCred(json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
                if(throwable instanceof  java.io.IOException){
                   noInternet();
                }
            }
        });

    }

    private void processUserCred(JSONObject json){
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement tweetElement = parser.parse(json.toString());
        JsonObject jObject = tweetElement.getAsJsonObject();
        User user = gson.fromJson(jObject, User.class);
        loggedInUser = user;
        tvNavHeader1.setText(user.getName());
        tvNavHeader2.setText("@"+user.getScreenName());
        Picasso.with(this)
                .load(user.getProfileImageUrl())
                .into(ivNavHeader);
        /*
        Glide.with(TweetListActivity.this)
                .load(user.getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(TweetListActivity.this, 5, 5))
                .into(ivNavHeader);*/
        Glide.with(TweetListActivity.this).load(user.getBannerUrl()).asBitmap().into(new SimpleTarget<Bitmap>(500, 500) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lvNavHeader.setBackground(drawable);
                }
            }
        });
    }

    private void noInternet(){
        System.out.println("No internet connection");
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "No Internet connection", Snackbar.LENGTH_LONG);
        List<Tweet> tweetListDb = SQLite.select().
                from(Tweet.class).queryList();
        for(Tweet t:tweetListDb){
            t.setEntities(new Entity());
            List<Media> mediaList = DbHelper.getMediaForTweet(t.getId());
            t.getEntities().setMedia(mediaList);
        }
        tweetList.addAll(tweetListDb);
        tweetsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("result recieved "+requestCode+" "+resultCode);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            String draft = data.getStringExtra("drafts");
            System.out.println("draft recieved "+draft);
            composeDialog.setDraft(draft);
        }
        System.out.println("result recieved");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sign_out) {
            client.clearAccessToken();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        composeDialog = ComposeFragment.newInstance(loggedInUser.getProfileImageUrl());
        composeDialog.show(fm, "fragment_alert");
    }

    public void postTweet(String newTweet){
        NetworkUtils.postTweets(client, newTweet, relativeLayout);
        Tweet t = new Tweet();
        t.setText(newTweet);
        t.setUser(loggedInUser);
        t.setEntities(new Entity());
        t.setCreatedAt(Constants.JUST_NOW);
        tweetList.add(0,t);
        tweetsAdapter.notifyDataSetChanged();
    }
}
