package com.laith.babylontest.dagger;


import com.laith.babylontest.service.FeedService;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    private FeedService feedService;

    public NetworkModule(FeedService feedService) {
        this.feedService = feedService;
    }

    @Provides
    @PerApp
    FeedService provideFeedService() {
        return feedService;
    }

}
