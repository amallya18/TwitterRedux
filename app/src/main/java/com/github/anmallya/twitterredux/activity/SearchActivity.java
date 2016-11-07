package com.github.anmallya.twitterredux.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.adapter.ProfileFragmentPagerAdapter;
import com.github.anmallya.twitterredux.adapter.SearchFragmentAdapter;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.fragments.ComposeFragment;
import com.github.anmallya.twitterredux.network.NetworkUtils;
import com.github.anmallya.twitterredux.network.TwitterClient;

import org.parceler.Parcels;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements ComposeFragment.OnPostTweetListener {

    private TwitterClient client;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        client = RestApplication.getRestClient();
        root = (LinearLayout) findViewById(R.id.root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(5);
        //setupTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        setSearchView(searchItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupTabs(String query){
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.invalidate();
        viewPager.setAdapter(new SearchFragmentAdapter(query, getSupportFragmentManager(),
                this));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.invalidate();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void setSearchView(MenuItem searchItem){
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                setupTabs(query);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void onTweetPosted(String newTweet){
        NetworkUtils.postTweets(client, newTweet, root);
    }
}