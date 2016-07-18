package com.laith.babylontest;

import android.content.Context;

import com.laith.babylontest.dagger.BabylonAppComponent;
import com.laith.babylontest.dagger.DaggerTestBabylonAppComponent;
import com.laith.babylontest.dagger.TestBabylonAppComponent;
import com.laith.babylontest.dagger.TestBabylonModule;
import com.laith.babylontest.dagger.TestDatabaseModule;
import com.laith.babylontest.dagger.TestNetworkModule;
import com.laith.babylontest.db.TestBlogDBHelper;

public class TestBabylonApp extends BabylonApp {
    private TestBabylonAppComponent testBabylonAppComponent;

    @Override
    public void onCreate() {
        testBabylonAppComponent = DaggerTestBabylonAppComponent.builder()
                .testBabylonModule(new TestBabylonModule(this))
                .testDatabaseModule(new TestDatabaseModule(new TestBlogDBHelper()))
                .testNetworkModule(new TestNetworkModule(null))
                .build();

        testBabylonAppComponent.inject(this);
    }

    public BabylonAppComponent getAppComponent(Context context) {
        return testBabylonAppComponent;
    }
}