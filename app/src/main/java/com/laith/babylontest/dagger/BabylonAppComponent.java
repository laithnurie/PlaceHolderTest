package com.laith.babylontest.dagger;

import android.app.Application;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.activity.PostListActivity;
import com.laith.babylontest.service.FeedService;


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

    Application app();

    FeedService placeHolderService();

}