package com.github.anmallya.twitterclient.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterclient.application.RestApplication;
import com.github.anmallya.twitterclient.models.Tweet;
import com.github.anmallya.twitterclient.network.NetworkUtils;
import com.github.anmallya.twitterclient.network.RestClient;
import com.github.anmallya.twitterclient.utils.Constants;
import com.github.anmallya.twitterclient.utils.Utils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailActivity extends AppCompatActivity {

    private ImageButton ivProfilePic, ivReply, ivDirectMsg;
    private ToggleButton ivRetweet, ivLike;
    private TextView tvProfileName, tvProfileHandler, tvCreatedTime, tvTweet, tvRetweetCount, tvLikeCount;
    private ImageView ivMedia;
    private Toolbar toolbar;
    private EditText etReply;
    private RestClient client;
    private RelativeLayout relativeLayout;
    private Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        relativeLayout = (RelativeLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        setupViews(tweet);
        getSupportActionBar().setElevation(5);
        client = RestApplication.getRestClient();
    }

    private void setupViews(final Tweet tweet) {

        getSupportActionBar().setTitle("Tweet");
        tvCreatedTime = (TextView)findViewById(R.id.tv_created_time); tvCreatedTime.setText(Utils.getTwitterDateVerbose(tweet.getCreatedAt()));

        tvLikeCount = ((TextView)findViewById(R.id.tv_like_count)); tvLikeCount.setText(tweet.getFavouritesCount()+"");

        tvProfileHandler = ((TextView)findViewById(R.id.tv_profile_handle)); tvProfileHandler.setText("@"+tweet.getUser().getScreenName());

        tvProfileName = ((TextView)findViewById(R.id.tv_profile_name)); tvProfileName.setText(tweet.getUser().getName());

        tvRetweetCount = ((TextView)findViewById(R.id.tv_retweet_count)); tvRetweetCount.setText(tweet.getRetweetCount()+"");

        tvTweet = ((TextView)findViewById(R.id.tv_tweet)); tvTweet.setText(tweet.getText());

        etReply = ((EditText) findViewById(R.id.et_reply)); etReply.setHint("Reply to "+tweet.getUser().getName());

        ivLike = ((ToggleButton)findViewById(R.id.iv_like));
        ivRetweet = ((ToggleButton)findViewById(R.id.iv_retweet));
        ivLike.setChecked(tweet.isFavorited());
        if (tweet.isFavorited()) {
            tvLikeCount.setTextColor(getResources().getColor(R.color.favRed));
        } else{
            tvLikeCount.setTextColor(getResources().getColor(R.color.darkGrey));
        }

        ivRetweet.setChecked(tweet.isRetweeted());
        if (tweet.isRetweeted()) {
            tvRetweetCount.setTextColor(getResources().getColor(R.color.retweetGreen));
        } else{
            tvRetweetCount.setTextColor(getResources().getColor(R.color.darkGrey));
        }

        ivDirectMsg = ((ImageButton)findViewById(R.id.iv_direct_msg));
        ivProfilePic = ((ImageButton)findViewById(R.id.iv_profile_pic));
        ivReply = ((ImageButton)findViewById(R.id.iv_reply));
        ivMedia = ((ImageView)findViewById(R.id.iv_media));

        ivLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NetworkUtils.favorited(client, tweet.getId());
                    tvLikeCount.setText(tweet.getFavouritesCount()+1+"");
                    tvLikeCount.setTextColor(getResources().getColor(R.color.favRed));
                } else {
                    NetworkUtils.unFavorited(client, tweet.getId());
                    tvLikeCount.setText(tweet.getFavouritesCount()-1+"");
                    tvLikeCount.setTextColor(getResources().getColor(R.color.darkGrey));
                }
            }
        });


        ivRetweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NetworkUtils.retweet(client, tweet.getId());
                    tvRetweetCount.setText(tweet.getRetweetCount()+1+"");
                    tvRetweetCount.setTextColor(getResources().getColor(R.color.retweetGreen));
                } else {
                    NetworkUtils.unRetweet(client, tweet.getId());
                    tvRetweetCount.setText(tweet.getRetweetCount()-1+"");
                    tvRetweetCount.setTextColor(getResources().getColor(R.color.darkGrey));
                }
            }
        });


        if(tweet.getEntities().getMedia()!=null){
            if(tweet.getEntities().getMedia().size() > 0){
                ivMedia.setVisibility(View.VISIBLE);
                System.out.println("Media url: "+tweet.getEntities().getMedia().get(0).getMediaUrl());
                Glide.with(this).load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                        .bitmapTransform(new RoundedCornersTransformation(this, Constants.RL, Constants.RL))
                        .placeholder(R.color.grey).into(ivMedia);
            } else{
                ivMedia.setVisibility(View.GONE);
            }
        } else {
            ivMedia.setVisibility(View.GONE);
        }

        final String screenName = tweet.getUser().getScreenName();
        etReply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    etReply.setText("@"+screenName+" ");
                }
            }
        });

        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(this, Constants.RS, Constants.RS))
                .placeholder(R.color.grey).into(ivProfilePic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void replyClicked(View view){

       // String tweetResponse = "@"+tweet.getUser().getScreenName()+" "+etReply.getText().toString();
        String tweetResponse = etReply.getText().toString();
        etReply.setText("");
        client.postReply(tweetResponse, tweet.getId(),new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Reply Posted Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Reply posting failure", Snackbar.LENGTH_LONG);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
            }
        });
    }
}
