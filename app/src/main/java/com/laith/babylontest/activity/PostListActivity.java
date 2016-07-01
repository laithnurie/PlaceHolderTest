package com.laith.babylontest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.BlogDBHelper;
import com.laith.babylontest.service.FeedService;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostListActivity extends AppCompatActivity implements PostResponseCallback {

    private static final String POSTS_KEY = "posts";
    @Inject
    FeedService feedService;
    @Inject
    BlogDBHelper blogDBHelper;

    private PostListViewModel postListViewModel;
    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postListViewModel = new PostListViewModel(findViewById(android.R.id.content));

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            posts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            postListViewModel.initialise(posts);
        } else {
            NetworkCall networkCall = new NetworkCall(this, feedService);
            networkCall.getPosts();
        }
    }

    @Override
    public void onSuccess(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            this.posts = posts;
            blogDBHelper.updatePosts(posts);
            postListViewModel.initialise(posts);
        } else {
            this.posts = blogDBHelper.getAllPosts();
            postListViewModel.initialise(posts);
        }
    }

    @Override
    public void onFail() {
        if (blogDBHelper.getAllPosts() != null && blogDBHelper.getAllPosts().size() > 0) {
            posts = blogDBHelper.getAllPosts();
            postListViewModel.initialise(posts);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, posts);
        super.onSaveInstanceState(outState);
    }
}
