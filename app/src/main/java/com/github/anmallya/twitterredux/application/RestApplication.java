package com.github.anmallya.twitterredux.application;

import android.app.Application;
import android.content.Context;

import com.github.anmallya.twitterredux.network.TwitterClient;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by anmallya on 10/27/2016.
 */

public class RestApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        RestApplication.context = this;
    }

    public static TwitterClient getRestClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, RestApplication.context);
    }
}