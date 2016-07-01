package com.laith.babylontest.viewmodel;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.activity.NetworkCall;
import com.laith.babylontest.activity.PostResponseCallback;
import com.laith.babylontest.activity.PostUpdateCallback;
import com.laith.babylontest.adapter.PostsListAdapter;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.service.FeedService;

import java.util.ArrayList;

public class PostListViewModel implements PostResponseCallback {

    private DBHelper dbHelper;
    private NetworkCall networkCall;
    private ArrayList<Post> posts;

    private final RecyclerView postsList;

    private PostUpdateCallback updateCallback;

    public PostListViewModel(View rootview, FeedService feedService, DBHelper dbHelper,
                             PostUpdateCallback updateCallback) {
        this.dbHelper = dbHelper;
        networkCall = new NetworkCall(this, feedService);
        this.updateCallback = updateCallback;
        postsList = (RecyclerView) rootview.findViewById(R.id.postList);
        postsList.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
    }

    public void setPosts(ArrayList<Post> posts) {
        postsList.setAdapter(new PostsListAdapter(posts));
    }

    public void fetchPosts() {
        networkCall.getPosts();
    }

    @Override
    public void onSuccess(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            this.posts = posts;
            dbHelper.updatePosts(posts);
            setPosts(posts);
            updateCallback.updatePosts(posts);
        } else {
            this.posts = dbHelper.getAllPosts();
            setPosts(this.posts);
        }

    }

    @Override
    public void onFail() {
        if (dbHelper.getAllPosts() != null && dbHelper.getAllPosts().size() > 0) {
            this.posts = dbHelper.getAllPosts();
            setPosts(this.posts);
            updateCallback.updatePosts(this.posts);
        }
    }
}
