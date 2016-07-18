package com.laith.babylontest.activity;

import com.laith.babylontest.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class PostListActivityTest {

    @Test
    public void onCreateTest() {
        PostListActivity postListactivity = Robolectric.setupActivity(PostListActivity.class);
        verify(postListactivity.networkCall).getComments(postListactivity);
        verify(postListactivity.networkCall).getUsers(postListactivity);
    }
}
