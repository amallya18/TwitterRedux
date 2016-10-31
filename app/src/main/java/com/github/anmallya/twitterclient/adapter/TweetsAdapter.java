package com.github.anmallya.twitterclient.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by anmallya on 10/20/2016.
 */
public class TweetsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Tweet> tweetList = null;
    private Context getContext() {
        return mContext;
    }
    private Context mContext;
    private RestClient client;

    public TweetsAdapter(Context context, List<Tweet> tweetList, RestClient client) {
        this.tweetList = tweetList;
        this.mContext = context;
        this.client = client;
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.layout_tweet_listview_holder, parent, false);
        viewHolder = new TweetListViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TweetListViewHolder vh1 = (TweetListViewHolder) viewHolder;
                configureViewHolder(vh1, position);
    }

    private void configureViewHolder(final TweetListViewHolder vh, int position) {
        final Tweet tweet = tweetList.get(position);

        vh.getTvProfileName().setText(tweet.getUser().getName());
        vh.getTvProfileHandler().setText("@"+tweet.getUser().getScreenName());


        vh.getIvLike().setChecked(tweet.isFavorited());
        vh.getIvRetweet().setChecked(tweet.isRetweeted());


        if (tweet.isFavorited()) {
            vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.favRed));
        } else{
            vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
        }

        if (tweet.isRetweeted()) {
            vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.retweetGreen));
        } else{
            vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
        }

        vh.getIvLike().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NetworkUtils.favorited(client, tweet.getId());
                    vh.getTvLikeCount().setText(tweet.getFavouritesCount()+1+"");
                    vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.favRed));
                } else {
                    NetworkUtils.unFavorited(client, tweet.getId());
                    vh.getTvLikeCount().setText(tweet.getFavouritesCount()-1+"");
                    vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
                }
            }
        });

        vh.getIvRetweet().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NetworkUtils.retweet(client, tweet.getId());
                    vh.getTvRetweetCount().setText(tweet.getRetweetCount()+1+"");
                    vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.retweetGreen));
                } else {
                    NetworkUtils.unRetweet(client, tweet.getId());
                    vh.getTvRetweetCount().setText(tweet.getRetweetCount()-1+"");
                    vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
                }
            }
        });

        vh.getTvTweet().setText(tweet.getText());
        vh.getTvCreatedTime().setText(Utils.getTwitterDate(tweet.getCreatedAt()));

        vh.getTvLikeCount().setText(tweet.getFavouritesCount()+"");
        vh.getTvRetweetCount().setText(tweet.getRetweetCount()+"");

        if(tweet.getEntities().getMedia()!=null){
            if(tweet.getEntities().getMedia().size() > 0){
                vh.getIvMedia().setVisibility(View.VISIBLE);
                System.out.println("Media url: "+tweet.getEntities().getMedia().get(0).getMediaUrl());
                Glide.with(getContext()).load(tweet.getEntities().getMedia().get(0).getMediaUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(), Constants.RL, Constants.RL)).placeholder(R.color.grey).into(vh.getIvMedia());
            } else{
                vh.getIvMedia().setVisibility(View.GONE);
            }
        } else {
            vh.getIvMedia().setVisibility(View.GONE);
        }

        ImageButton ib = vh.getIvProfilePic();
        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(), Constants.RS, Constants.RS)).placeholder(R.color.grey).into(ib);
    }
}