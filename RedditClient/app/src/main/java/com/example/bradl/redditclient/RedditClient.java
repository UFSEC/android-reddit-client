package com.example.bradl.redditclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by bradl on 10/8/2016.
 */

public class RedditClient {
    private final String API_BASE_URL = "http://www.reddit.com/";
    private AsyncHttpClient client;

    public RedditClient() {
        this.client = new AsyncHttpClient();
    }

    // If an empty string is given, we will get data from the front page
    private String getApiUrl(String subreddit) {
        String url = API_BASE_URL;

        if(!subreddit.equals("")){
            url += "r/" + subreddit;
        }

        return url + ".json";
    }

    public void getPosts(JsonHttpResponseHandler handler) {
        // Exercise: If you pass in the name of a subreddit to getApiUrl(), it will get posts
        // from that subreddit. As an exercise, extend the functionality of this app to allow
        // the user to choose a subreddit.
        String url = getApiUrl("");
        client.get(url, handler);
    }
}