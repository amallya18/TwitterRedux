package com.github.anmallya.twitterredux.fragments;

import android.os.Bundle;

/**
 * Created by anmallya on 11/5/2016.
 */
public class MomentsFragment extends TweetsFragment {
    @Override
    protected void getTweets() {
        avi.hide();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static MomentsFragment newInstance(int page) {
        MomentsFragment fragment = new MomentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    public MomentsFragment() {
    }
}
