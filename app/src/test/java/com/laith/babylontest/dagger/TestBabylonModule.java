package com.laith.babylontest.dagger;

import com.laith.babylontest.TestBabylonApp;

import dagger.Module;
import dagger.Provides;

@Module
public class TestBabylonModule {

    private final TestBabylonApp app;

    public TestBabylonModule(TestBabylonApp app) {
        this.app = app;
    }

    @Provides
    @PerApp
    TestBabylonApp provideApp() {
        return app;
    }
}