# android-reddit-client
A tutorial that provides an introduction to Android basics. This tutorial will take you
through the process of creating an application that displays posts from the front page of reddit.

######See the live recording of the tutorial from the SEC@UF meeting on YouTube below:
TODO: Put the correct link

<a href="http://www.youtube.com/watch?feature=player_embedded&v=PKu0G6vdkiA
" target="_blank"><img src="http://img.youtube.com/vi/PKu0G6vdkiA/0.jpg"
alt="IMAGE ALT TEXT HERE" width="240" height="180" border="10" /></a>

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

Keep the default values for this activity and click Finish.

##What's in the new project:

TODO: Michael give a nice explanation of what all the files that were created are.

##Install Library Dependencies
We will be using some additional libraries in our application:

android-async-http-client for sending network requests
Picasso for displaying images

Edit the app/build.gradle file to add these dependencies.

```.gradle
repositories {
    jcenter()
}
dependencies {
    // ...
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.loopj.android:android-async-http:1.4.6'
}
```

We will then need to sync with gradle via:

Tools->Android->Sync Project with Gradle Files

##Adding Necessary Permissions
Some features of the Android device require explicit permission to be able to be used. Some examples are: Making a call, using the camera, using Bluetooth, using the internet, making the phone vibrate, etc.

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




Now we are going to create a **work experience** section for Albert. Copy and paste the same container code that you have for your **about** section, but discarding your "about" text, changing the `<h1>` and giving this div an `id="work"`. It should look like this:
```html
<div id="work" class="container">
  <div class="page-header">
    <h1>Work experience</h1>
  </div>
</div>
```

Now, we're going to use a bootstrap [list group](http://getbootstrap.com/components/#list-group) to list our work experience. Copy & paste the code from the bootstrap's website (link above) right below your "pade-header" div to create a list of jobs.

This section should look like this now:
![screenshot](https://gyazo.com/8c18b6e6223ca57cef6a246f14a9b8a7.png)

Lets change the text and add some jobs now. I'm going to have a total of 3 jobs. I am also gonna add some [http://getbootstrap.com/components/#badges](bootstrap badge's) to denote the time spent at each job.

Finally, the code for this section looks like this:

```html
    <div id="work" class="container">
      <div class="page-header">
        <h1>Work experience</h1>
      </div>
      <ul class="list-group">
        <li class="list-group-item">Lead developer at UF Software Engineering Club<span class="badge">Present</span></li>
        <li class="list-group-item">UF Bookstore<span class="badge">2 years</span></li>
        <li class="list-group-item">McDonald's Crew Member<span class="badge">7 years</span></li>
      </ul>
    </div>
```

####Projects

Since Albert is a programmer, he definitely needs a **projects** section to showcase his work and impress technical recruiters who come across his site.

Again, copy and paste the same container code that you have for your **about** and **work experience** sections, but discarding the contents other than the `page-header`, changing the `<h1>` and giving this div an `id="projects"`. It should look like this:

```html
    <div id="projects" class="container">
      <div class="page-header">
        <h1>Projects</h1>
      </div>
    </div>
```

We are going to list out 3 projects that Albert has developed. I want to have all 3 projects in one row. Each having an image and a title + description below its respective image. On a desktop view it will look like this:

![projects](https://gyazo.com/5904de1ff8b709f611d32eabf39f9921.png)

On mobile, since there is normally not enough screen width to display all 3 projects side by side in a row. It would be desirable to collapse each project underneath the previous in one column. We can accomplish this easily with bootstrap's [grid layout](http://getbootstrap.com/css/#grid).

Here is the code for this section implementing the grid layout and with all of the data for the projects:
```html
    <div id="projects" class="container">
      <div class="page-header">
        <h1>Projects</h1>
      </div>
      <!--Bootstrap grid layout-->
      <div class="row">
        <div class="col-md-4">
          <img src="img/gatorway.webp" class="img-responsive">
          <h3>
            <a href="https://play.google.com/store/apps/details?id=com.guidebook.apps.GatorWay.android&hl=en" target="_blank">UF GatorWay App</a>
            </h3>
          <p>I developed the GatorWay app for both iOS and Android. "GatorWay is the official app of New Student and Family Programs in the Dean of Students Office at the University of Florida. GatorWay lets you download guides to various events including: Preview, Weeks of Welcome, and Family Weekend."</p>
        </div>

        <div class="col-md-4">
          <img src="img/isis.jpg" height="280" width="280" class="img-responsive img-rounded">
          <br/>
          <h3>
            <a href="https://www.isis.ufl.edu/" target="_blank">isis.ufl.edu website</a>
          </h3>
          <p> I developed the isis website for university of florida students. This site is the online hub for students. It allows students to register for classes, view grades, transcripts, and a whole bunch more.</p>
        </div>

        <div class="col-md-4">
          <img src="img/transloc.webp" class="img-responsive">
          <h3>
            <a href="https://play.google.com/store/apps/details?id=com.transloc.android.rider&hl=en" target="_blank">TransLoc Rider App</a>
          </h3>
          <p> I developed the TransLoc Rider app for Android. It allows students to track the bus system in Gainesville so they are able to get to class in time! It provides real time bus locations and ETAs.
          </p>
        </div>
      </div>
    </div>
```

As you see I have three `<div class="col-md-4">`'s inside of a `<div class="row">`.  Each row has 12 columns. The `<div class="col-md-4">` creates a column of length 4 for "medium" sized devices (Desktops (≥992px). 4 x 3 = a total of 12 columns. For non-medium sized devices such as phones/tables, these `<div class="col-md-4">`'s get wrapped onto a new line. For more info, checkout [bootstrap's docs](http://getbootstrap.com/css/#grid).

Too see how the **projects** section looks on a mobile device, open the chrome developer tools.

`ctrl+shift+j` on Windows or `cmd+option+j` on Mac.

Click on the little [phone icon](http://cdn.sixrevisions.com/500-03-smartphone-icon-highlighted.png) in the very top left hand corner of the developer tools window. This will bring up [device mode](https://developer.chrome.com/devtools/docs/device-mode). Where you can view your site on all different devices. Select an iPhone or something, then refresh the page to interact with your site.

####Blinking cursor animation
The site is practically done. But lets add a little "blinking cursor" animation. It's going to look like this:
![cursor](https://gyazo.com/5bcfff2123a0d48bc1a93cb98714cedc.gif)

First add the below `<span>` element right after the "Hello, World!" text in your `<h1>` tag (still inside this `<h1>` tag though).
```html
<span id="cursor">|</span>
```
Now, we are going to use some JavaScript (and [jQuery](https://en.wikipedia.org/wiki/JQuery)) to animate this "|" so it looks like its blinking. Open your `script.js` file and paste the following function right at the top of the file.

```javascript
function cursorAnimation() {
	$('#cursor').animate({
	    opacity: 0
	}, 'fast', 'swing').animate({
	    opacity: 1
	}, 'fast', 'swing');
}
```
This function first selects any HTML element(s) with an id of `cursor` (aka our cursor!) Then calls [jQuery's .animate()](http://api.jquery.com/animate/) function on it. Which just sets the opacity to 0 (invisible) and then brings it back to 1.

You don't really need to understand entirely whats going on here. Alot of software development is utilizing other people's code and resources to accomplish your goal. For example, I didn't write this from scratch. I got the idea from [here](http://codepen.io/stathisg/pen/Bkvhg).

Now, paste the below code above your `cursorAnimation()` function in your `script.js` file.

```javascript
$(document).ready(function() {
    setInterval ('cursorAnimation()', 800);
});
```

This function calls [setInterval()](https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers/setInterval), passing in our `cursorAnimation()` function and a value of 800ms. This essentially calls `cursorAnimation()` every 800ms, giving the cursor a blinking effect!

The `$( document ).ready()` is a jQuery function, which is kind of like a "main method". Basically, once the page is loaded and ready to be manipulated it executes whatever code is inside of it. More info [here](https://learn.jquery.com/using-jquery-core/document-ready/).

####Navbar

As you may have noticed, the links in the navigation bar are incorrect and not all of them work. Lets fix that real quick.

If you go to the code for the navigation bar, (the large `<nav>` tag at the top of the `<body>`) you will find an unordered list which houses each button on the nav bar. The code should look like this right now:
```html
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#contact">Contact</a></li>
      </ul>
```
If you take a closer look at the code, you will see that each `<li>` is just an `<a>` tag with a link and some text. For example `<a href="#about">` is telling that link element to link to **the** element in the page with an id of *about*. So there was a reason we gave each section an id! :sunglasses:

Let's fix/change the second `<li>` to correspond to "work" or "work experience" and add one more `<li>` for  "projects".

The code should now look like this:

```html
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#about">About</a></li>
        <li><a href="#work">Work</a></li>
        <li><a href="#projects">Projects</a></li>
      </ul>
    </div><!--/.nav-collapse -->
```

And the nav buttons should all work appropiately! :+1:

####Notes
Woo, we've built your first website! And it doesn't look half bad, eh? It also looks good on mobile! All of this with minimal effort. Thanks bootstrap.

Now, if you want to use this tutorial as a basis for your own personal website (which you probably should) you will probably want to:
* add a *contact* section with links to your email, github, linkedin, etc
* maybe host your resume on the website
* customize it to your liking. We don't want everyone with an identical website :stuck_out_tongue_winking_eye:
* Host it with [github pages](https://pages.github.com/)
-- GitHub offers free hosting for static websites and makes it really simple to get your site online. The link above has great step by step instructions.

[Here](https://github.com/HackathonHackers/personal-sites) is a list of a ton of students around the world's personal sites. This is so you can get inspiration for design and whatnot.

[Contact me](http://spuleri.com/) if you have any questions.
