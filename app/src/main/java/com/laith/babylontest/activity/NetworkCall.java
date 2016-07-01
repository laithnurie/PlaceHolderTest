package com.laith.babylontest.activity;

import com.laith.babylontest.model.Post;
import com.laith.babylontest.service.FeedService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {

    private PostResponseCallback callback;
    private FeedService feedService;

    public NetworkCall(PostResponseCallback callback, FeedService feedService) {
        this.callback = callback;
        this.feedService = feedService;
    }

    public void getPosts() {
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
