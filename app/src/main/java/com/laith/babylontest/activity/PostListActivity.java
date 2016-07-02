package com.laith.babylontest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.viewmodel.PostListPortraitViewModel;
import com.laith.babylontest.viewmodel.PostListViewModel;

import javax.inject.Inject;

public class PostListActivity extends AppCompatActivity {

    @Inject
    PostNetworkCall networkCall;
    @Inject
    DBHelper blogDBHelper;

    private PostListViewModel postListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postListViewModel = new PostListPortraitViewModel(findViewById(android.R.id.content), this, networkCall,
                blogDBHelper, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        postListViewModel.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
