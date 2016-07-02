package com.laith.babylontest.dagger;


import com.laith.babylontest.activity.PostNetworkCall;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    private final PostNetworkCall networkCall;

    public NetworkModule(PostNetworkCall networkCall) {
        this.networkCall = networkCall;
    }

    @Provides
    @PerApp
    PostNetworkCall provideNetworkCall() {
        return networkCall;
    }

}
