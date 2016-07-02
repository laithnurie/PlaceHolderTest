package com.laith.babylontest.dagger;

import com.laith.babylontest.db.DBHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private final DBHelper blogDBHelper;

    public DatabaseModule(DBHelper blogDBHelper) {
        this.blogDBHelper = blogDBHelper;
    }

    @Provides
    @PerApp
    DBHelper provideBlogDBHelper() {
        return blogDBHelper;
    }
}
