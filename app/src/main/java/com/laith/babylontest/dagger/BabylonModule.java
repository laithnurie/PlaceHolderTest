package com.laith.babylontest.dagger;

import android.app.Application;


import dagger.Module;
import dagger.Provides;

@Module
public class BabylonModule {

    private Application app;

    public BabylonModule(Application app) {
        this.app = app;
    }

    @Provides
    @PerApp
    Application providePlaygroundApp() {
        return app;
    }
}
