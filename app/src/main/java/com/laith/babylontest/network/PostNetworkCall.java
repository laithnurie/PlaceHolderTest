package com.laith.babylontest.network;

import com.laith.babylontest.viewmodel.PostResponseCallback;

public interface PostNetworkCall {
    void getPosts(PostResponseCallback callback);
}
