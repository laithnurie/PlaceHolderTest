package com.laith.babylontest.activity;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostListViewModel;
import com.laith.babylontest.viewmodel.PostViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostListActivity extends AppCompatActivity {

    private static final String POSTS_KEY = "posts";
    @Inject
    PostNetworkCall networkCall;
    @Inject
    DBHelper blogDBHelper;

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postViewModel = new PostListViewModel(findViewById(android.R.id.content), networkCall,
                blogDBHelper);

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            ArrayList<Post> posts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            postViewModel.updatePostList(posts);
        } else {
            postViewModel.fetchPosts();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, postViewModel.getPosts());
        super.onSaveInstanceState(outState);
    }
}
