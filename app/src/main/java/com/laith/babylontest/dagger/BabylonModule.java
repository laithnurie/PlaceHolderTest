package com.laith.babylontest.dagger;

import android.app.Application;


import dagger.Module;
import dagger.Provides;

@Module
public class BabylonModule {

    private final Application app;

    public BabylonModule(Application app) {
        this.app = app;
    }

    @Provides
    @PerApp
    Application provideApp() {
        return app;
    }
}
