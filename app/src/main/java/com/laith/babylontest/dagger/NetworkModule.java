package com.laith.babylontest.dagger;


import com.laith.babylontest.network.NetworkCall;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    private final NetworkCall networkCall;

    public NetworkModule(NetworkCall networkCall) {
        this.networkCall = networkCall;
    }

    @Provides
    @PerApp
    NetworkCall provideNetworkCall() {
        return networkCall;
    }

}
