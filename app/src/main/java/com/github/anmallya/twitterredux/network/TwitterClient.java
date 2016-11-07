package com.github.anmallya.twitterredux.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * Created by anmallya on 10/27/2016.
 */

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";

    //public static final String REST_CONSUMER_KEY = "cVC9iLskldD0gGfBBy5A7IEEM";
    //public static final String REST_CONSUMER_SECRET = "YfDJb0dQqj1ft4JGuMMRUO6zQQU3Mlj3JuWfe4UxuG0UBMxVdO";
    public static final String REST_CALLBACK_URL = "oauth://codepathtweets";


    public static final String REST_CONSUMER_KEY = "ztpNiAun5Ne8h6KW7V7amIwDS";
    public static final String REST_CONSUMER_SECRET = "RCtQ5isUnd66tEwo0cxRSYhcxWCOMqUyiy4FuCzXyyOqD167i4";

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getTweetTimelineList(long max, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("since_id", 1);
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }


    public void getDirectMessages(long max, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("direct_messages.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 20);
        params.put("since_id", 1);
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }

    public void postDirectMessages(String directMsg, String screenName, AsyncHttpResponseHandler handler) {
        System.out.println("Message sent to "+screenName+" Message is "+directMsg);
        String apiUrl = getApiUrl("direct_messages/new.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("screen_name", screenName);
        params.put("text", directMsg);
        client.post(apiUrl, params, handler);
    }


    public void getUserTimelineList(String screenName, long max, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("since_id", 1);
        params.put("screen_name", screenName);
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }

    public void getsearchList(String searchTerm, String searchType, long max, AsyncHttpResponseHandler handler) {
        System.out.println("################################"+searchTerm+" "+searchType);
        String apiUrl = getApiUrl("search/tweets.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("since_id", 1);
        params.put("q", searchTerm);
        params.put("result_type", searchType); //recent // popylar
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }



    public void getUserFollowerList(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("followers/list.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
    }

    public void getUserFollowingList(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("friends/list.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
    }

    public void getUserLikesTimelineList(String screenName, long max, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/list.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("since_id", 1);
        params.put("screen_name", screenName);
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimelineList(long max, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("count", 25);
        params.put("since_id", 1);
        if(max != -1){
            params.put("max_id", max-1);
        }
        client.get(apiUrl, params, handler);
    }


    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        getClient().post(apiUrl, params, handler);
    }

    public void postFavorite(long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", tweetId);
        getClient().post(apiUrl, params, handler);
    }

    public void postUnFavorite(long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id", tweetId);
        getClient().post(apiUrl, params, handler);
    }

    public void postRetweet(long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/retweet/"+tweetId+".json");
        RequestParams params = new RequestParams();
        getClient().post(apiUrl, params, handler);
    }

    public void postUnRetweet(long tweetId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/unretweet/"+tweetId+".json");
        RequestParams params = new RequestParams();
        params.put("id", tweetId);
        getClient().post(apiUrl, params, handler);
    }

    public void postReply(String tweet, long tweetId, AsyncHttpResponseHandler handler) {
        System.out.println("reply posted "+tweet+" id "+tweetId);
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweet);
        params.put("in_reply_to_status_id", tweetId);
        getClient().post(apiUrl, params, handler);
    }

    public void getCurrentUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }

    // public static final String REST_CONSUMER_KEY = "Vm9jkr9TVS0yvfHfXrBWnKFEn";
    // public static final String REST_CONSUMER_SECRET = "JsP4aEgOuGxTg9ABfCec6dVJTaQUft84GwxuV3DeciMPKi4s00";
}