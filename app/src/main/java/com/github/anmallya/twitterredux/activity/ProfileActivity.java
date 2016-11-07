package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.adapter.CustomFragmentPagerAdapter;
import com.github.anmallya.twitterredux.adapter.ProfileFragmentPagerAdapter;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.fragments.ComposeFragment;
import com.github.anmallya.twitterredux.fragments.HomeFragment;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.NetworkUtils;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.github.anmallya.twitterredux.utils.Consts;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.github.anmallya.twitterredux.utils.Consts.imageResId;
import static com.github.anmallya.twitterredux.utils.Consts.imageResIdSel;
import static com.github.anmallya.twitterredux.utils.Consts.tabTitles;

public class ProfileActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener {

    TextView tvName, tvScreenName, tvDesc, tvFollowers, tvFollowing, tvFollowersText, tvFollowingText;
    ImageView ivBackground, ivProfile;
    private TwitterClient client;
    private LinearLayout root;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        client = RestApplication.getRestClient();
        root = (LinearLayout) findViewById(R.id.root);

        //CollapsingToolbarLayout ctbLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        //ctbLayout.setBackgroundColor(getResources().getColor(R.color.black));

        tvName = (TextView)findViewById(R.id.tv_profile_name); tvName.setText(user.getName());
        tvScreenName = (TextView)findViewById(R.id.tv_profile_handle); tvScreenName.setText("@"+user.getScreenName());
        tvDesc = (TextView)findViewById(R.id.tv_desc); tvDesc.setText(user.getDescription());
        tvFollowers = (TextView)findViewById(R.id.tv_followers_count); tvFollowers.setText(""+user.getFollowersCount());
        tvFollowing = (TextView)findViewById(R.id.tv_following_count); tvFollowing.setText(""+user.getFriendsCount());

        tvFollowersText = (TextView)findViewById(R.id.tv_followers_text);
        tvFollowingText = (TextView)findViewById(R.id.tv_following_text);


        tvFollowersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FollowersActivity.class);
                intent.putExtra(Consts.TYPE,Consts.FOLLOWERS);
                intent.putExtra(Consts.SCREEN_NAME, user.getScreenName());
                startActivity(intent);
            }
        });

        tvFollowingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, FollowersActivity.class);
                intent.putExtra(Consts.TYPE,Consts.FOLLOWING);
                intent.putExtra(Consts.SCREEN_NAME, user.getScreenName());
                startActivity(intent);
            }
        });


        ivBackground = (ImageView)findViewById(R.id.iv_backdrop);
        ivProfile = (ImageView)findViewById(R.id.iv_profile);

        System.out.println("############# "+user.getProfileBackgroundImageUrl());
        System.out.println("############# "+user.getProfileImageUrl());

        Glide.with(this).load(user.getProfileBackgroundImageUrl()).placeholder(R.color.grey).into(ivBackground);
        Glide.with(this).load(user.getProfileImageUrl()).placeholder(R.color.grey).into(ivProfile);
        setupTabs();
    }

    private void setupTabs(){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ProfileFragmentPagerAdapter(user.getScreenName(), getSupportFragmentManager(),
                this));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onTweetPosted(String newTweet){
        NetworkUtils.postTweets(client, newTweet, root);
    }
}
