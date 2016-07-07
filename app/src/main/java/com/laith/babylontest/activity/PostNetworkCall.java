package com.laith.babylontest.activity;

import com.laith.babylontest.viewmodel.PostResponseCallback;

public interface PostNetworkCall {
    void getPosts(PostResponseCallback callback);
}
