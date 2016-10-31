package com.github.anmallya.twitterclient.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by anmallya on 10/27/2016.
 */

@Database(name = TweetsDatabase.NAME, version = TweetsDatabase.VERSION)
public class TweetsDatabase {

    public static final String NAME = "TweetsDatabase";

    public static final int VERSION = 1;
}
