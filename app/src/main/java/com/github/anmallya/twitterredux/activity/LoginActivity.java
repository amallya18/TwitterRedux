package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.network.TwitterClient;

/**
 * Created by anmallya on 10/27/2016.
 */

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {
    /*
        Twitter Redux

        part 1: home, mentions, direct message, search.
        http://sendvid.com/6ng53ae7.mp4

        part 2: friend profile
        http://sendvid.com/b84g1qaa.mp4

        part 3: current user profile
        http://sendvid.com/sybxss0f.mp4
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


    @Override
    public void onLoginSuccess() {
        Intent i = new Intent(this, TweetListActivity.class);
        startActivity(i);
    }


    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }

}