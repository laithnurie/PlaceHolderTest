package com.laith.babylontest.dagger;

import com.laith.babylontest.db.DBHelper;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.when;

@Module
public class TestDatabaseModule {
    private final DBHelper blogDBHelper;

    public TestDatabaseModule(DBHelper blogDBHelper) {
        this.blogDBHelper = blogDBHelper;
    }

    @Provides
    @PerApp
    DBHelper provideBlogDBHelper() {
        return Mockito.mock(DBHelper.class);
    }
}