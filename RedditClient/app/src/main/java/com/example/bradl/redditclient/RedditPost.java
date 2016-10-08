package com.example.bradl.redditclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bradl on 10/8/2016.
 */

public class RedditPost {
    private String title;
    private String domain;
    private int numComments;
    private String subreddit;
    private String thumbnail;
    private String url;
    private int upvotes;

    public String getTitle() {
        return title;
    }

    public String getDomain() {
        return domain;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl(){
        return url;
    }

    public int getUpvotes(){
        return upvotes;
    }

    // Returns a RedditPost from the given JSON data.
    public static RedditPost fromJson(JSONObject jsonObject){
        RedditPost p = new RedditPost();
        try{
            p.title = jsonObject.getString("title");
            p.domain = jsonObject.getString("domain");
            p.numComments = jsonObject.getInt("num_comments");
            p.subreddit = jsonObject.getString("subreddit");
            p.url = jsonObject.getString("url");
            p.upvotes = jsonObject.getInt("score");


            // Try to get the thumbnailUrl (not all posts have thumbnails)
            try{
                p.thumbnail = jsonObject.getString("thumbnail");
            } catch (JSONException e){
                // There is no thumbnail
                p.thumbnail = "";
            }
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return p;
    }

    public static ArrayList<RedditPost> fromJson(JSONArray jsonArray){
        ArrayList<RedditPost> posts = new ArrayList<RedditPost>(jsonArray.length());

        // Convert each element in the json array to a json object, then to a Post
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject postJson = null;
            try{
                postJson = jsonArray.getJSONObject(i).getJSONObject("data");
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }

            RedditPost post = RedditPost.fromJson(postJson);
            if(post != null){
                posts.add(post);
            }
        }

        return posts;
    }
}
