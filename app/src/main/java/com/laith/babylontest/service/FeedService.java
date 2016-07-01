package com.laith.babylontest.service;

import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedService {
    @GET("/posts")
    Call<ArrayList<Post>> getPosts();
    @GET ("/users")
    Call<ArrayList<User>> getUsers();
    @GET ("/comments")
    Call<ArrayList<Comment>> getComments();
}
