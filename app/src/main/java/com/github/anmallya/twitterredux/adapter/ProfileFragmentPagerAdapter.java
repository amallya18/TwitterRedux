package com.github.anmallya.twitterredux.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.anmallya.twitterredux.fragments.HomeFragment;
import com.github.anmallya.twitterredux.fragments.MentionsFragment;
import com.github.anmallya.twitterredux.fragments.MessagesFragment;
import com.github.anmallya.twitterredux.fragments.MomentsFragment;
import com.github.anmallya.twitterredux.fragments.UserFavoritesFragment;
import com.github.anmallya.twitterredux.fragments.UserTweetsFragment;

import static com.github.anmallya.twitterredux.fragments.MomentsFragment.newInstance;

/**
 * Created by anmallya on 11/5/2016.
 */


public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;

    private Context context;

    private String title[] = {"TWEETS", "MEDIA", "LIKES"};

    private String screenName;

    public ProfileFragmentPagerAdapter(String screenName, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.screenName = screenName;
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
                return UserTweetsFragment.newInstance(0, screenName);
            case 1:
                return MomentsFragment.newInstance(0);
            case 2:
                return UserFavoritesFragment.newInstance(0, screenName);
            default:
                return HomeFragment.newInstance(0);
        }
    }
}