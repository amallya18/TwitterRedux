package com.github.anmallya.twitterredux.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.anmallya.twitterredux.fragments.HomeFragment;
import com.github.anmallya.twitterredux.fragments.SearchTopFragment;
import com.github.anmallya.twitterredux.helper.SmartFragmentStatePagerAdapter;

/**
 * Created by anmallya on 11/5/2016.
 */

    public class SearchFragmentAdapter extends SmartFragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;

        private Context context;
        private String query;

        private String title[] = {"TOP", "LIVE"};

        public SearchFragmentAdapter(String query, FragmentManager fm, Context context) {
            super(fm);
            this.query = query;
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return SearchTopFragment.newInstance(0, "popular", query);
                case 1:
                    return SearchTopFragment.newInstance(0, "recent", query);
                default:
                    return SearchTopFragment.newInstance(0, "recent", query);
            }
        }
    }