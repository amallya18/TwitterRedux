package com.github.anmallya.twitterclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterclient.network.TwitterClient;

/**
 * Created by anmallya on 10/27/2016.
 */

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

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
        System.out.println("############# "+"success");
        //Toast.makeText(this,"Success", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, TweetListActivity.class);
        startActivity(i);
    }


    @Override
    public void onLoginFailure(Exception e) {
        System.out.println("############# "+"failure");
        Toast.makeText(this,"Failure", Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        //Toast.makeText(this,"Button clicked", Toast.LENGTH_LONG).show();
        getClient().connect();
    }

}