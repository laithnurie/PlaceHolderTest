package com.laith.babylontest.viewmodel;

import android.os.Bundle;

import com.laith.babylontest.model.Post;

public interface PostViewModel {
    void setPost(Post post);
    void onSavedInstance(Bundle outState);
}
