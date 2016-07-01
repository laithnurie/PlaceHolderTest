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

public class PostListActivity extends AppCompatActivity implements PostUpdateCallback {

    private static final String POSTS_KEY = "posts";
    @Inject
    FeedService feedService;
    @Inject
    BlogDBHelper blogDBHelper;

    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        PostListViewModel postListViewModel = new PostListViewModel(findViewById(android.R.id.content), feedService,
                blogDBHelper, this);

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            posts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            postListViewModel.setPosts(posts);
        } else {
            postListViewModel.fetchPosts();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, posts);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void updatePosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
