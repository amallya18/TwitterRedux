package com.github.anmallya.twitterredux.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.activity.MessageActivity;
import com.github.anmallya.twitterredux.activity.TweetDetailActivity;
import com.github.anmallya.twitterredux.activity.TweetListActivity;
import com.github.anmallya.twitterredux.adapter.TweetsAdapter;
import com.github.anmallya.twitterredux.application.RestApplication;
import com.github.anmallya.twitterredux.data.DbHelper;
import com.github.anmallya.twitterredux.helper.EndlessRecyclerViewScrollListener;
import com.github.anmallya.twitterredux.helper.ItemClickSupport;
import com.github.anmallya.twitterredux.models.Entity;
import com.github.anmallya.twitterredux.models.Media;
import com.github.anmallya.twitterredux.models.Tweet;
import com.github.anmallya.twitterredux.models.User;
import com.github.anmallya.twitterredux.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public abstract class TweetsFragment extends Fragment {

    protected static final String ARG_PAGE = "ARG_PAGE";
    protected ArrayList<Tweet> tweetList;
    protected TweetsAdapter tweetsAdapter;
    protected TwitterClient client;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout relativeLayout;

    protected long max = -1;

    protected int mPage;

    private OnFragmentInteractionListener mListener;
    protected AVLoadingIndicatorView avi;

    public TweetsFragment() {
        // Required empty public constructor
    }

    /*
    public static TweetsFragment newInstance(int page) {
        TweetsFragment fragment = new TweetsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
        tweetList = new ArrayList<Tweet>();
        client = RestApplication.getRestClient();
        tweetsAdapter = new TweetsAdapter(getActivity(), tweetList, client);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayout = (RelativeLayout) getActivity().findViewById(R.id.root1);
        setSwipeRefreshLayout();
        setRecyclerView();
        avi.show();
        getTweets();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setSwipeRefreshLayout(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
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

    protected void handleNetworkFailure(int statusCode, Throwable throwable ){
        Log.d("Failed: ", "" + statusCode);
        Log.d("Error : ", "" + throwable);
        if(throwable instanceof java.io.IOException){
            noInternet();
        }
        avi.hide();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /*
    protected void getTweets() {
        if(mPage == 0){
            client.getTweetTimelineList(max, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    processTweetJson(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                    Log.d("Failed: ", "" + statusCode);
                    Log.d("Error : ", "" + throwable);
                }
            });
        } else if(mPage == 2){
            client.getMentionsTimelineList(max, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                    processTweetJson(json);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                    Log.d("Failed: ", "" + statusCode);
                    Log.d("Error : ", "" + throwable);
                }
            });
        }
        else{
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }*/

    protected abstract void getTweets();


    protected void processTweetJson(JSONArray json){
        avi.hide();
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
        if (tweetListNew.size() > 0){
            max = tweetListNew.get(tweetListNew.size() - 1).getId();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    protected void noInternet(){
        System.out.println("No internet connection");
        if(relativeLayout == null){
            return;
        }
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "No Internet connection", Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.twitterBlue));
        snackbar.show();
        /*
        List<Tweet> tweetListDb = SQLite.select().
                from(Tweet.class).queryList();
        for(Tweet t:tweetListDb){
            t.setEntities(new Entity());
            List<Media> mediaList = DbHelper.getMediaForTweet(t.getId());
            t.getEntities().setMedia(mediaList);
        }
        tweetList.addAll(tweetListDb);
        tweetsAdapter.notifyDataSetChanged();*/
    }

    private void setRecyclerView(){
        avi = (AVLoadingIndicatorView)getView().findViewById(R.id.avi);
        RecyclerView rv = (RecyclerView)getView().findViewById(R.id.rvTweets);
        rv.setAdapter(tweetsAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
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
                        Intent intent;
                        switch(tweetList.get(position).getDisplayType()){
                            case MESSAGE:
                                intent = new Intent(getActivity(), MessageActivity.class);
                                intent.putExtra("user", Parcels.wrap(tweetList.get(position).getUser()));
                                startActivity(intent);
                                break;
                            default:
                                intent = new Intent(getActivity(), TweetDetailActivity.class);
                                intent.putExtra("tweet", Parcels.wrap(tweetList.get(position)));
                                startActivity(intent);
                        }
                    }
                }
        );
    }
}
