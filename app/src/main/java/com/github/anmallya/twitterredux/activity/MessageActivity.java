package com.github.anmallya.twitterredux.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MessageActivity extends AppCompatActivity {

    EditText etScreenName, etDirectMsg;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        client = RestApplication.getRestClient();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10);
        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        etScreenName = (EditText) findViewById(R.id.et_screenName);
        etScreenName.setText("@"+user.getScreenName());
        etDirectMsg = (EditText) findViewById(R.id.et_directMessage);
        etScreenName.setSelection(etScreenName.getText().length());
        getSupportActionBar().setTitle(user.getName());
    }


    public void sendMessageOnClick(View view){
        String directMsg = etDirectMsg.getText().toString();
        String screenName = etScreenName.getText().toString();
        client.postDirectMessages(directMsg, screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }
}
