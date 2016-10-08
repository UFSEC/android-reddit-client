package com.example.bradl.redditclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {
    private ListView lvPosts;
    private RedditPostAdapter postsAdapter;
    RedditClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        lvPosts = (ListView) findViewById(R.id.lvPosts);
        ArrayList<RedditPost> aPosts = new ArrayList<RedditPost>();
        postsAdapter = new RedditPostAdapter(this, aPosts);
        lvPosts.setAdapter(postsAdapter);
        client = new RedditClient();
        fetchPosts();
    }

    public void fetchPosts(){
        client.getPosts(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode,  org.apache.http.Header[] headers, JSONObject responseBody){
                JSONArray items = null;
                try{
                    // Get the posts json array
                    JSONObject data = responseBody.getJSONObject("data");
                    items = data.getJSONArray("children");
                    // Parse the json array into array of model objects
                    ArrayList<RedditPost> posts = RedditPost.fromJson(items);
                    // Load the model objects into the adapter
                    for(RedditPost post: posts){
                        postsAdapter.add(post);
                    }
                    postsAdapter.notifyDataSetChanged();

                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
