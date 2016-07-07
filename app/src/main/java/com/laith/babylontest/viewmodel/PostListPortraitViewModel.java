package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.activity.PostActivity;
import com.laith.babylontest.activity.PostNetworkCall;
import com.laith.babylontest.activity.PostResponseCallback;
import com.laith.babylontest.adapter.PostsListAdapter;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public class PostListPortraitViewModel implements PostListViewModel, PostResponseCallback, PostClickListener {

    private static final String POSTS_KEY = "posts";

    private Context mContext;
    private DBHelper mDbHelper;
    private RecyclerView postsList;
    private ArrayList<Post> mPosts;

    @Override
    public void initialise(View rootview, Context context, PostNetworkCall postNetworkCall, DBHelper dbHelper, Bundle savedInstanceState) {
        mContext = context;
        mDbHelper = dbHelper;
        postsList = (RecyclerView) rootview.findViewById(R.id.postList);
        postsList.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            mPosts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            updatePostList(mPosts);
        } else {
            postNetworkCall.getPosts(this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, mPosts);
    }

    @Override
    public void onPostsResponse(ArrayList<Post> posts) {
        if (posts != null && posts.size() > 0) {
            mPosts = posts;
            mDbHelper.updatePosts(posts);
            updatePostList(posts);
        } else {
            mPosts = mDbHelper.getAllPosts();
            updatePostList(mPosts);
        }
    }

    @Override
    public void onPostsError() {
        if (mDbHelper.getAllPosts() != null && mDbHelper.getAllPosts().size() > 0) {
            mPosts = mDbHelper.getAllPosts();
            updatePostList(mPosts);
        }
    }

    private void updatePostList(ArrayList<Post> posts) {
        postsList.setAdapter(new PostsListAdapter(posts, this));
    }

    @Override
    public void onClick(Post post) {
        mContext.startActivity(PostActivity.getIntent(post, mContext));
    }
}
