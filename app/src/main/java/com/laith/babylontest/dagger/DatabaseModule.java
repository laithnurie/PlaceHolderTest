package com.laith.babylontest.dagger;

import com.laith.babylontest.db.BlogDBHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private BlogDBHelper blogDBHelper;

    public DatabaseModule(BlogDBHelper blogDBHelper) {
        this.blogDBHelper = blogDBHelper;
    }

    @Provides
    @PerApp
    BlogDBHelper provideBlogDBHelper() {
        return blogDBHelper;
    }
}
