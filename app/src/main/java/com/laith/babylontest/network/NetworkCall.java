package com.laith.babylontest.network;

import com.laith.babylontest.activity.PostNetworkCall;
import com.laith.babylontest.activity.PostResponseCallback;
import com.laith.babylontest.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall implements PostNetworkCall {

    private FeedService feedService;

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
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                callback.onFail();
            }
        });
    }
}