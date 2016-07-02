package com.laith.babylontest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.viewmodel.PostListViewModel;
import com.laith.babylontest.viewmodel.PostViewModel;

import javax.inject.Inject;

public class PostListActivity extends AppCompatActivity {

    @Inject
    PostNetworkCall networkCall;
    @Inject
    DBHelper blogDBHelper;

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postViewModel = new PostListViewModel(findViewById(android.R.id.content), networkCall,
                blogDBHelper, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        postViewModel.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
