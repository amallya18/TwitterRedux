package com.github.anmallya.twitterredux.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.github.anmallya.twitterclient.R;
import com.github.anmallya.twitterredux.fragments.HomeFragment;
import com.github.anmallya.twitterredux.fragments.MentionsFragment;
import com.github.anmallya.twitterredux.fragments.MessagesFragment;
import com.github.anmallya.twitterredux.fragments.MomentsFragment;
import com.github.anmallya.twitterredux.fragments.TweetsFragment;
import com.github.anmallya.twitterredux.helper.SmartFragmentStatePagerAdapter;

/**
 * Created by anmallya on 11/4/2016.
 */



public class CustomFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    final int PAGE_COUNT = 4;

    private Context context;

    public CustomFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return HomeFragment.newInstance(0);
            case 1:
                return MomentsFragment.newInstance(0);
            case 2:
                return MentionsFragment.newInstance(0);
            case 3:
                return MessagesFragment.newInstance(0);
            default:
                return HomeFragment.newInstance(0);
        }
    }
}

