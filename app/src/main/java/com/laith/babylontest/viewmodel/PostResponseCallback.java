package com.laith.babylontest.viewmodel;

import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public interface PostResponseCallback {
    void onPostsResponse(ArrayList<Post> posts);
    void onPostsError();
}
