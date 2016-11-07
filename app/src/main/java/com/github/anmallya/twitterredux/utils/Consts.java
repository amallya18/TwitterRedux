package com.github.anmallya.twitterredux.utils;

import com.github.anmallya.twitterclient.R;

/**
 * Created by anmallya on 10/29/2016.
 */

public class Consts {
    public static final String JUST_NOW = "Now";

    public static final String FOLLOWERS = "followers";
    public static final String FOLLOWING = "following";

    public static final String SCREEN_NAME = "screenName";
    public static final String TYPE = "type";

    public static final int RL = 20; // Radius Large
    public static final int RS = 3; // Radius Small

    public static final int[]  imageResId = {
            R.drawable.ic_tab_home,
            R.drawable.ic_tab_moments,
            R.drawable.ic_tab_mentions,
            R.drawable.ic_tab_msg
    };

    public static final int[] imageResIdSel = {
            R.drawable.ic_tab_home_blue,
            R.drawable.ic_tab_moments_blue,
            R.drawable.ic_tab_mentions_blue,
            R.drawable.ic_tab_msg_blue
    };

    public static final String tabTitles[] = new String[] { "Home", "Moments", "Mentions" ,"Messages"};
}
