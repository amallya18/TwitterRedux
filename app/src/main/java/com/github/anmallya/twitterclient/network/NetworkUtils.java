package com.github.anmallya.twitterclient.network;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by anmallya on 10/30/2016.
 */

public class NetworkUtils {

    public static  void favorited(RestClient client, long tweetId){
        client.postFavorite(tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                System.out.println("Like success "+statusCode);
                System.out.println("success j "+json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                System.out.println("Like failure "+statusCode);
                System.out.println("failure j "+throwable);
            }
        });
    }

    public static void unFavorited(RestClient client, long tweetId){
        client.postUnFavorite(tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                System.out.println("unlike success "+statusCode);
                System.out.println("success j "+json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                System.out.println("unlike failure "+statusCode);
                System.out.println("failure j "+throwable);
            }
        });
    }

    public static void retweet(RestClient client, long tweetId){
        client.postRetweet(tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                System.out.println("retweet success "+statusCode);
                System.out.println("success j "+json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                System.out.println("retweet failure "+statusCode);
                System.out.println("failure j "+throwable);
            }
        });
    }

    public static void unRetweet(RestClient client, long tweetId){
        client.postUnRetweet(tweetId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                System.out.println("unretweet success "+statusCode);
                System.out.println("success j "+json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                System.out.println("unretweet failure "+statusCode);
                System.out.println("failure j "+throwable);
            }
        });
    }
}
