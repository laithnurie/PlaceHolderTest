package com.laith.babylontest.viewmodel;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.adapter.PostsListAdapter;
import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public class PostListViewModel {

    private final RecyclerView postsList;

    public PostListViewModel(View rootview) {
        postsList = (RecyclerView) rootview.findViewById(R.id.postList);
        postsList.setLayoutManager(new LinearLayoutManager(rootview.getContext()));
    }

    public void initialise(ArrayList<Post> posts) {
        postsList.setAdapter(new PostsListAdapter(posts));
    }
}
