# Android Reddit Client
This tutorial provides an introduction to Android basics. This take you
through the process of creating an application that displays posts from the front page of Reddit.

######See the live recording of the tutorial from the SEC@UF meeting on YouTube below:

Recording coming soon...

## Intro to Android basics

TODO: Michael put some of your stuff here if you want to.

## Prerequisites
You must have (preferably the latest version of ) Android Studio installed on your computer. You can download that here: https://developer.android.com/studio/index.html.

Some basic knowledge of Java or other OO languages is recommended.

##Create a new project:
Launch Android Studio and select "Start a new Android Studio Project".

You can name your application whatever you like. For this tutorial, we will be using the name "RedditClient".
The other default values (Company Domain , C++ Support, and Project location) can be left as they are. Click Next

Now we are required to choose the Minimum SDK for our app. Targeting lower SDKs will allow our app to be ran
on more devices, but we will not be able to utilize all of the features available in the newer SDKs. Generally,
we will want to use the lowest SDK that will still support all of the features we need in our app. For
the sake of this application, API 15 will do just fine. Click Next.

Click Next.

Select "Empty Activity" and click Next.

Name this activity `PostsActivity` and click Finish.

##What's in the new project:

TODO: Michael give a nice explanation of what all the files that were created are.

##Install Library Dependencies
We will be using some additional libraries in our application:

`android-async-http-client`, and `httpcore` for sending network requests, as well as `Picasso` for displaying images.

Edit the `app/build.gradle` file to include these dependencies:

```.gradle
repositories {
    jcenter()
}
dependencies {
    // ...
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.loopj.android:android-async-http:1.4.6'
    compile "org.apache.httpcomponents:httpcore:4.3.2"
}
```

We will then need to sync our project with Gradle via:

`Tools->Android->Sync Project with Gradle Files`

##Adding Necessary Permissions
Some features of the Android device will require explicit permission to be able to be used. Some examples are: Making a call, using the camera, using Bluetooth, using the internet, making the phone vibrate, etc.

We need to be able to use the internet to get our data. Add the internet permissions to `AndroidManifest.xml` within the `manifest` node:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Note: It is important to ensure that permissions are defined before
```xml
<application>
//...
</application>
```

##Downloading Assets
We will be using a placeholder image for the post thumbnails.
Download this image:
`https://raw.githubusercontent.com/UFSEC/android-reddit-client/master/Resources/reddit_alien.png`

Once you have this image downloaded, copy it into `res/drawable`. You can also drag and drop it using the `Project` pane in Android Studio.


##Setting Up a Basic Posts Layout

At this point, we should have all of the dependencies, permissions, and assets taken care of. Lets get started by creating a layout for our `PostsActivity`.

Open `res/layout/activity/activity_posts.xml`. We will create a ListView that will hold our posts.

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".PostsActivity" >

    <ListView
        android:id="@+id/lvPosts"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentRight="true"
        android:layout_alignParentTop="true" >

    </ListView>
</RelativeLayout>

```
You should see something that looks like this in the Preview pane:

![screenshot](https://raw.githubusercontent.com/UFSEC/android-reddit-client/master/Resources/lvPostsPreview.PNG =250px)


##Creating the API Client
We will now create  Java class that will act as our Reddit API client. This class will send out network requests to retrieve posts. Create a new class called `RedditClient` with the following code:

NOTE: To create a new class, Navigate to `app/java/your.package.name/` in the Package pane. Right click on the package name (not the test one) and select `New -> Java Class`.

```Java
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
}
```

Next, lets define a method for making a request using our client.

 ```Java
 public class RottenTomatoesClient {
     // ...

     public void getPosts(JsonHttpResponseHandler handler) {
         // Exercise: If you pass in the name of a subreddit to getApiUrl(), it will get posts from that subreddit. As an exercise, extend the functionality of this app to allow the user to choose a subreddit.
         String url = getApiUrl("");
         client.get(url, handler);
     }

     // ...
 }
 ```

##Define the Data Model

Lets take a look at what the data we will be getting from reddit will look like. You can actually take a look at the data by navigating to `http://www.reddit.com/.json` in your browser. I recommend quickly installing an extension to format json data in your browser nicely. If you are using chrome, JSONView is a great one.

As you can see, there is an object at the root that has an attribute `data`. Within this, there is an attribute `children`. This is where we have our array of posts. Each post has an attribute `data`, which contains a ton of information about that post. For the purposes of this demo, the information we will be using is the title, domain, num_comments, subreddit, thumbnail, url, and score.

We will need to define a model that that stores these attributes. Create a Java class called `RedditPost` with the relevant attributes discussed above:

```Java
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
}
```

Next, we will add a factory method for deserializing the JSON response:

```Java
public class RedditPost {
    // ...

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

    // ...
}
```

To make our life a little bit easier, lets add a method that will return an array of RedditPosts when given a JSON array.

```Java
public class RedditPost {
  // ...

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

    /// ...
}
```

##Construct an Array Adapter
We are now at a point where we have the API Client (`RedditClient`), and the data Model (`RedditPost`). What we need to do now is construct an adapter so that we can translate a `RedditPost` into a particular view. Create a new class called `RedditPostAdapter` which extends `ArrayAdapter<RedditPost>`:

```Java
public class RedditPostAdapter extends ArrayAdapter<RedditPost> {
    public RedditPostAdapter(Context context, ArrayList<RedditPost> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: Complete the definition of the view for each movie
        return null;

```

As you can see, the `RedditPostAdapter` will need to return a view representing a particular `RedditPost`. We'll define that view by creating a layout file called `item_reddit_post.xml` with a `RelativeLayout` root layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="5dp">

    <TextView
        android:id="@+id/tvUpvotes"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_alignParentLeft="true"
        android:hint="1233"
        android:layout_marginLeft="6dp"/>
    <RelativeLayout
        android:id="@+id/layout_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_toRightOf="@+id/tvUpvotes"
        android:layout_centerVertical="true"
        android:gravity="center_vertical"
        android:layout_marginLeft="12dp"
        android:layout_marginRight="6dp"
        android:layout_toLeftOf="@+id/ivThumbnail">

        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentTop="true"
            android:textStyle="bold"
            android:textSize="12sp"
            android:hint="Here is the title of a post."/>
        <TextView
            android:id="@+id/tvDomain"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignLeft="@+id/tvTitle"
            android:layout_below="@+id/tvTitle"
            android:textSize="12sp"
            android:hint="(google.com)"/>

        <TextView
            android:id="@+id/tvComments"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignLeft="@+id/tvDomain"
            android:layout_below="@+id/tvDomain"
            android:textSize="12sp"
            android:layout_marginTop="8dp"
            android:hint="217 comments"/>

        <TextView
            android:id="@+id/tvSubreddit"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignTop="@+id/tvComments"
            android:layout_toRightOf="@+id/tvComments"
            android:textSize="12sp"
            android:layout_marginLeft="12dp"
            android:hint="AskReddit"/>

    </RelativeLayout>

    <ImageView
        android:id="@+id/ivThumbnail"
        android:layout_width="65dp"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_alignParentRight="true"
        android:maxWidth="65dp"
        android:scaleType="fitXY"
        android:adjustViewBounds="true"
        android:layout_marginRight="2dp"
        android:src="@drawable/reddit_alien"/>
</RelativeLayout>
```

Now we can fill in the `getView()` method to finish off the `RedditPostAdapter`:

```Java
public class RedditPostAdapter extends ArrayAdapter<RedditPost>{
  // ...
  @Override
 public View getView(int position, View convertView, ViewGroup parent){
     // Get the data item for this position
     RedditPost post = getItem(position);

     // Check if the existing view is being reused, otherwise infalte the view
     if(convertView == null){
         LayoutInflater inflater = LayoutInflater.from(getContext());
         convertView = inflater.inflate(R.layout.item_reddit_post, parent, false);
     }

     // Lookup views within the layout
     TextView tvUpvotes = (TextView) convertView.findViewById(R.id.tvUpvotes);
     TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
     TextView tvDomain = (TextView) convertView.findViewById(R.id.tvDomain);
     TextView tvComments = (TextView) convertView.findViewById(R.id.tvComments);
     TextView tvSubreddit = (TextView) convertView.findViewById(R.id.tvSubreddit);
     ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.ivThumbnail);


     // Populate the data
     tvUpvotes.setText("" + post.getUpvotes());
     tvTitle.setText(post.getTitle());
     tvDomain.setText("(" + post.getDomain() + ")");
     tvComments.setText(post.getNumComments() + " comments");
     tvSubreddit.setText(post.getSubreddit());

     // Display the thumbnail if there is one
     String thumbnailUrl = post.getThumbnail();
     if(thumbnailUrl.length() < 1 || thumbnailUrl.equals("self")){
         ivThumbnail.setImageResource(R.drawable.reddit_alien);
     }else{
         Picasso.with(getContext()).load(post.getThumbnail()).into(ivThumbnail);
     }

     return convertView;
 }

 // ...
}
```

##Bringing it all together
We now have all the necessary wiring. All we have to do now is plug it in. We need to make it so that the `PostsActivity` will send out the API call, deserialize the response into an array of `RedditPosts`, and then render those into its View using the `RedditPostAdapter`.

Lets start by initializing the ArrayList, adapter, and the ListView:

```Java
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
}
```

Now lets create the `fetchPosts()` method, which will make the call to the API and populate our ListView.

```Java
public class PostsActivity extends AppCompatActivity {
// ...
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
  //...
}

```

Now run the app. We should see the ListView populated with posts from the frontpage of Reddit!

![screenshot](https://raw.githubusercontent.com/UFSEC/android-reddit-client/master/Resources/final_app.PNG =250px)


##Notes
Woo, we've done with our first app! Well... kinda. This app doesn't really do much does it? That's kind of the point. My goal here is really to provide you with a starting point. You can only really learn so much by following along with a tutorial and just copy-pasting code snippets. I strongly encourage you to continue with this app and implement additional features on your own. Some obvious features that this app is lacking include:

* We haven't yet implemented the functionality of viewing a post (thats kind of important isn't it???). Look into setting up an `OnItemClickListener` for the Posts ListView. You can use this to execute code when a post is clicked (we are already storing the posts URL. Lets do something with that!)

* We are only loading the first 20 posts. We should be able to continue scrolling right? You can set up event listeners for scrolling, and make another call to the API when we are getting towards the bottom. Hint: The JSON data contains an `after` property. This has something to do with the current page we are requesting.

* Provide a way to view the comments.

* Provide a way to view different subreddits.

* If you are feeling ambitious, allow users to sign in with their reddit account. You can then implement upvoting, commenting, making posts, etc.

There are so many things you can do with this, these are just some ideas. I hope you enjoyed this tutorial and continue learning and developing your skills!


[Contact me](http://bmtreuherz.github.io/) if you have any questions.
