package com.github.anmallya.twitterredux.data;

import com.github.anmallya.twitterredux.models.Media;
import com.github.anmallya.twitterclient.models.Media_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by anmallya on 10/29/2016.
 */

public class DbHelper {
    public static List<Media> getMediaForTweet(long id){
        List<Media> mediaList = SQLite.select().from(Media.class).where(Media_Table.tweetId.is(id)).queryList();
        return mediaList;
    }
}
