package com.laith.babylontest.viewmodel;

import com.laith.babylontest.model.Post;

import java.util.ArrayList;

public interface PostViewModel {
    void updatePostList(ArrayList<Post> posts);
    void fetchPosts();
    ArrayList<Post> getPosts();
}
