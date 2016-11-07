package com.github.anmallya.twitterredux.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.activity.PhotoDetailActivity;
import com.github.anmallya.twitterredux.activity.ProfileActivity;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.fragments.ComposeFragment;
import com.github.anmallya.twitterredux.helper.PatternEditableBuilder;
import com.github.anmallya.twitterredux.models.DisplayType;
import com.github.anmallya.twitterredux.models.Extended;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.NetworkUtils;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Consts;
import com.github.anmallya.twitterredux.utils.Utils;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.parceler.Parcels;

import java.util.List;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.view.View.GONE;

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
    private TwitterClient client;

    public TweetsAdapter(Context context, List<Tweet> tweetList, TwitterClient client) {
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

    private void setText(TweetListViewHolder vh, Tweet tweet){
        vh.getTvProfileName().setText(tweet.getUser().getName());
        vh.getTvProfileHandler().setText("@"+tweet.getUser().getScreenName());
        vh.getTvTweet().setText(tweet.getText());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), getContext().getResources().getColor(R.color.linkBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {

                            }
                        }).into(vh.getTvTweet());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"), getContext().getResources().getColor(R.color.linkBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {

                            }
                        }).into(vh.getTvTweet());
        vh.getTvCreatedTime().setText(Utils.getTwitterDate(tweet.getCreatedAt()));
        vh.getTvLikeCount().setText(tweet.getFavouritesCount()+"");
        vh.getTvRetweetCount().setText(tweet.getRetweetCount()+"");
    }

    private void setToggleButton(TweetListViewHolder vh, Tweet tweet){
        vh.getIvLike().setChecked(tweet.isFavorited());
        vh.getIvRetweet().setChecked(tweet.isRetweeted());
    }

    private void setCountColor(TweetListViewHolder vh, Tweet tweet){
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

    }

    private void showComposeDialog(User user) {
          FragmentManager fm = ((FragmentActivity)mContext).getSupportFragmentManager();
          ComposeFragment composeDialog = ComposeFragment.newInstance(RestApplication.getUser().getProfileImageUrl(), user.getScreenName());
          composeDialog.show(fm, "fragment_alert");
    }

    private void setImages(TweetListViewHolder vh, final Tweet tweet){
        if(tweet.getEntities().getMedia()!=null){
            if(tweet.getEntities().getMedia().size() > 0){
                vh.getIvMedia().setVisibility(View.VISIBLE);
                vh.getVideoFrame().setVisibility(View.GONE);
                //vh.getPlayerController().setVisibility(View.GONE);
                //vh.getTextureView().setVisibility(View.GONE);
                vh.getIvMedia().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), PhotoDetailActivity.class);
                        intent.putExtra("media", tweet.getEntities().getMedia().get(0).getMediaUrl());
                        getContext().startActivity(intent);
                    }
                });
                System.out.println("Media url: "+tweet.getEntities().getMedia().get(0).getMediaUrl());
                Glide.with(getContext()).load(tweet.getEntities().getMedia().get(0).getMediaUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(), Consts.RL, Consts.RL)).placeholder(R.color.grey).into(vh.getIvMedia());
            } else{
                vh.getIvMedia().setVisibility(GONE);
            }
        } else if(tweet.getEntities().getExtendedList()!=null){
            if(tweet.getEntities().getExtendedList().size() > 0){
                Extended extended = tweet.getEntities().getExtendedList().get(0);
                if(extended.getType().equals("video")){
                    showVideo(extended, vh);
                    vh.getIvMedia().setVisibility(GONE);
                    vh.getVideoFrame().setVisibility(View.VISIBLE);
                    //vh.getPlayerController().setVisibility(View.VISIBLE);
                    //vh.getTextureView().setVisibility(View.VISIBLE);
                }
            }
        }
        else {
            vh.getIvMedia().setVisibility(GONE);
            vh.getVideoFrame().setVisibility(View.GONE);
            /// /vh.getPlayerController().setVisibility(View.GONE);
            //vh.getTextureView().setVisibility(View.GONE);
        }

        ImageButton ibReply = vh.getIvReply();
        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComposeDialog(tweet.getUser());
            }
        });

        ImageView ib = vh.getIvProfilePic();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(tweet.getUser()));
                getContext().startActivity(intent);
            }
        });
        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).bitmapTransform(new RoundedCornersTransformation(getContext(), 6, 6)).placeholder(R.color.grey).into(ib);
    }

    private void setToggleListners(final TweetListViewHolder vh,final  Tweet tweet){
        (vh.getIvLike()).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                tweet.setFavorited(!tweet.isFavorited());
                if(tweet.isFavorited()) {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$ LIKE");
                    NetworkUtils.favorited(client, tweet.getId());
                    vh.getTvLikeCount().setText(tweet.getFavouritesCount()+1+"");
                    vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.favRed));
                } else {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$ UN LIKE");
                    NetworkUtils.unFavorited(client, tweet.getId());
                    vh.getTvLikeCount().setText(tweet.getFavouritesCount()-1+"");
                    vh.getTvLikeCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
                }
            }
        });

        (vh.getIvRetweet()).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                tweet.setRetweeted(!tweet.isRetweeted());
                if(tweet.isRetweeted()) {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$ RETWEET");
                    NetworkUtils.retweet(client, tweet.getId());
                    vh.getTvRetweetCount().setText(tweet.getRetweetCount()+1+"");
                    vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.retweetGreen));
                } else {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$ UNRETWEET");
                    NetworkUtils.unRetweet(client, tweet.getId());
                    vh.getTvRetweetCount().setText(tweet.getRetweetCount()-1+"");
                    vh.getTvRetweetCount().setTextColor(getContext().getResources().getColor(R.color.darkGrey));
                }
            }
        });
    }

    void showVideo(Extended extended, TweetListViewHolder v){
        FensterVideoView textureView = v.getTextureView();
        MediaFensterPlayerController playerController = v.getPlayerController();
        textureView.setMediaController(playerController);

        textureView.setVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                SimpleMediaFensterPlayerController.DEFAULT_VIDEO_START);
        textureView.start();
    }

    private void configureViewHolder(final TweetListViewHolder vh, int position) {
        final Tweet tweet = tweetList.get(position);
        setButtonVisibility(tweet, vh);
        setText(vh, tweet);
        setToggleButton(vh, tweet);
        setCountColor(vh, tweet);
        setImages(vh, tweet);
        setToggleListners(vh, tweet);
    }

    private void setButtonVisibility(Tweet tweet, TweetListViewHolder vh){
        switch(tweet.getDisplayType()){
            case MESSAGE:
                vh.getIvRetweet().setVisibility(GONE);
                vh.getTvLikeCount().setVisibility(GONE);
                vh.getTvRetweetCount().setVisibility(GONE);
                vh.getIvDirectMsg().setVisibility(GONE);
                vh.getIvLike().setVisibility(GONE);
                vh.getIvReply().setVisibility(GONE);
                break;
            default:
                vh.getIvRetweet().setVisibility(View.VISIBLE);
                vh.getTvLikeCount().setVisibility(View.VISIBLE);
                vh.getTvRetweetCount().setVisibility(View.VISIBLE);
                vh.getIvDirectMsg().setVisibility(View.VISIBLE);
                vh.getIvLike().setVisibility(View.VISIBLE);
                vh.getIvReply().setVisibility(View.VISIBLE);
        }
    }
}