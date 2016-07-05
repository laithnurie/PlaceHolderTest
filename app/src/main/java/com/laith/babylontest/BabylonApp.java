package com.laith.babylontest;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.laith.babylontest.network.NetworkCall;
import com.laith.babylontest.dagger.BabylonAppComponent;
import com.laith.babylontest.dagger.BabylonModule;
import com.laith.babylontest.dagger.DaggerBabylonAppComponent;
import com.laith.babylontest.dagger.DatabaseModule;
import com.laith.babylontest.dagger.NetworkModule;
import com.laith.babylontest.db.BlogDBHelper;
import com.laith.babylontest.network.FeedService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BabylonApp extends Application {

    private BabylonAppComponent babylonAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FeedService feedService = retrofit.create(FeedService.class);
        NetworkCall networkCall = new NetworkCall(feedService);

        babylonAppComponent = DaggerBabylonAppComponent.builder()
                .babylonModule(new BabylonModule(this))
                .networkModule(new NetworkModule(networkCall))
                .databaseModule(new DatabaseModule(new BlogDBHelper(this)))
                .build();
        babylonAppComponent.inject(this);
    }

    public static BabylonAppComponent getAppComponent(Context context) {
        return ((BabylonApp) context.getApplicationContext()).babylonAppComponent;
    }
}
