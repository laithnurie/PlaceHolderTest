package com.laith.babylontest.activity;

import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public interface PostUpdateCallback {
    void updatePosts(ArrayList<Post> posts);
}
