package com.github.anmallya.twitterredux.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.network.NetworkUtils;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Consts;
import com.github.anmallya.twitterredux.utils.Utils;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailActivity extends AppCompatActivity {

    private ImageButton ivProfilePic, ivReply, ivDirectMsg;
    private ToggleButton ivRetweet, ivLike;
    private TextView tvProfileName, tvProfileHandler, tvCreatedTime, tvTweet, tvRetweetCount, tvLikeCount;
    private ImageView ivMedia;
    private Toolbar toolbar;
    private EditText etReply;
    private TwitterClient client;
    private RelativeLayout relativeLayout;
    private Tweet tweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        client = RestApplication.getRestClient();
        intitializeViews();
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        setupViews(tweet);
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
        ivRetweet.setChecked(tweet.isRetweeted());
        ivDirectMsg = ((ImageButton)findViewById(R.id.iv_direct_msg));
        ivProfilePic = ((ImageButton)findViewById(R.id.iv_profile_pic));
        ivReply = ((ImageButton)findViewById(R.id.iv_reply));
        ivMedia = ((ImageView)findViewById(R.id.iv_media));
        setToggleButtons();
        setImages();
    }


    private void setImages(){
        if(tweet.getEntities().getMedia()!=null){
            if(tweet.getEntities().getMedia().size() > 0){
                ivMedia.setVisibility(View.VISIBLE);
                System.out.println("Media url: "+tweet.getEntities().getMedia().get(0).getMediaUrl());
                Glide.with(this).load(tweet.getEntities().getMedia().get(0).getMediaUrl())
                        .bitmapTransform(new RoundedCornersTransformation(this, Consts.RL, Consts.RL))
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
                .bitmapTransform(new RoundedCornersTransformation(this, Consts.RS, Consts.RS))
                .placeholder(R.color.grey).into(ivProfilePic);
    }

    private void intitializeViews(){
        relativeLayout = (RelativeLayout) findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(5);
    }

    private void setToggleButtons(){
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
    }

    public void replyClicked(View view){
        String tweetResponse = etReply.getText().toString();
        etReply.setText("");
        NetworkUtils.reply(client, tweet, tweetResponse ,relativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
