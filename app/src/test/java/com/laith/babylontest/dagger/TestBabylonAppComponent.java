package com.laith.babylontest.dagger;

import com.laith.babylontest.TestBabylonApp;
import com.laith.babylontest.activity.PostActivity;
import com.laith.babylontest.activity.PostListActivity;
import com.laith.babylontest.activity.UserActivity;

import dagger.Component;

@PerApp
@Component(
        modules = {
                TestNetworkModule.class,
                TestBabylonModule.class,
                TestDatabaseModule.class
        }
)
public interface TestBabylonAppComponent extends BabylonAppComponent {
    void inject(TestBabylonApp babylonApp);
    void inject(PostListActivity activity);
    void inject(PostActivity postActivity);
    void inject(UserActivity userActivity);
}