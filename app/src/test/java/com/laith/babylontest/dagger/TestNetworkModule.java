package com.laith.babylontest.dagger;

import com.laith.babylontest.network.NetworkCall;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestNetworkModule {
    private final NetworkCall networkCall;

    public TestNetworkModule(NetworkCall networkCall) {
        this.networkCall = networkCall;
    }

    @Provides
    @PerApp
    NetworkCall provideNetworkCall() {
        return Mockito.mock(NetworkCall.class);
    }
}