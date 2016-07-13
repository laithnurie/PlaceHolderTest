package com.laith.babylontest.network;

import com.laith.babylontest.viewmodel.PostResponseCallback;
import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall implements PostNetworkCall, UserNetworkCall, CommentNetworkCall {

    final private FeedService feedService;

    public NetworkCall(FeedService feedService) {
        this.feedService = feedService;
    }

    @Override
    public void getPosts(final PostResponseCallback callback) {
        Call<ArrayList<Post>> postsCall = feedService.getPosts();
        postsCall.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    callback.onPostsResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                callback.onPostsError();
            }
        });
    }

    @Override
    public void getUsers(final UserResponseCallback callback) {
        Call<ArrayList<User>> usersCall = feedService.getUsers();
        usersCall.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, final Response<ArrayList<User>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    callback.onUsersResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

    }

    @Override
    public void getComments(final CommentResponseCallback callback) {
        Call<ArrayList<Comment>> commentsCall = feedService.getComments();
        commentsCall.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, final Response<ArrayList<Comment>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    callback.onCommentsResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
            }
        });
    }
}