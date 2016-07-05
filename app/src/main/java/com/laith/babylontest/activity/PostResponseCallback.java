package com.laith.babylontest.activity;

import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public interface PostResponseCallback {
    void onPostsResponse(ArrayList<Post> posts);
    void onPostsError();
}
