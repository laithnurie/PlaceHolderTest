package com.laith.babylontest.dagger;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.activity.PostListActivity;

import dagger.Component;

@PerApp
@Component(
        modules = {
                BabylonModule.class,
                NetworkModule.class,
                DatabaseModule.class
        }
)
public interface BabylonAppComponent {
    void inject(BabylonApp babylonApp);
    void inject(PostListActivity activity);
}