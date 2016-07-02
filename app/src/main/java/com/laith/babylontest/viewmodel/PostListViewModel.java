package com.laith.babylontest.viewmodel;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.activity.PostNetworkCall;
import com.laith.babylontest.activity.PostResponseCallback;
import com.laith.babylontest.adapter.PostsListAdapter;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public class PostListViewModel implements PostViewModel, PostResponseCallback {

    private static final String POSTS_KEY = "posts";

    private final DBHelper dbHelper;
    private final RecyclerView postsList;
    private ArrayList<Post> posts;

    public PostListViewModel(View rootview, PostNetworkCall postNetworkCall, DBHelper dbHelper,
                             Bundle savedInstanceState) {
        this.dbHelper = dbHelper;
        postsList = (RecyclerView) rootview.findViewById(R.id.postList);
        postsList.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            posts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            updatePostList(posts);
        } else {
            postNetworkCall.getPosts(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, posts);
    }

    @Override
    public void onSuccess(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            this.posts = posts;
            dbHelper.updatePosts(posts);
            updatePostList(posts);
        } else {
            this.posts = dbHelper.getAllPosts();
            updatePostList(this.posts);
        }
    }

    @Override
    public void onFail() {
        if (dbHelper.getAllPosts() != null && dbHelper.getAllPosts().size() > 0) {
            this.posts = dbHelper.getAllPosts();
            updatePostList(this.posts);
        }
    }

    private void updatePostList(ArrayList<Post> posts) {
        postsList.setAdapter(new PostsListAdapter(posts));
    }
}
