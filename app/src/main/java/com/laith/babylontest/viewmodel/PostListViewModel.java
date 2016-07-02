package com.laith.babylontest.viewmodel;


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

    private final DBHelper dbHelper;
    private final PostNetworkCall postNetworkCall;
    private final RecyclerView postsList;
    private ArrayList<Post> posts;

    public PostListViewModel(View rootview, PostNetworkCall postNetworkCall, DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.postNetworkCall = postNetworkCall;
        postsList = (RecyclerView) rootview.findViewById(R.id.postList);
        postsList.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
    }

    @Override
    public void updatePostList(ArrayList<Post> posts) {
        postsList.setAdapter(new PostsListAdapter(posts));
    }

    @Override
    public void fetchPosts() {
        postNetworkCall.getPosts(this);
    }

    @Override
    public ArrayList<Post> getPosts() {
        return this.posts;
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
}
