package com.laith.babylontest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.NetworkCall;
import com.laith.babylontest.viewmodel.PostListPortraitViewModel;
import com.laith.babylontest.viewmodel.PostListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class PostListActivity extends AppCompatActivity implements UserResponseCallback, CommentResponseCallback{

    @Inject
    NetworkCall networkCall;
    @Inject
    DBHelper blogDBHelper;

    private PostListViewModel postListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postListViewModel = new PostListPortraitViewModel();
        postListViewModel.initialise(findViewById(android.R.id.content), this, networkCall,
                blogDBHelper, savedInstanceState);

        networkCall.getComments(this);
        networkCall.getUsers(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        postListViewModel.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onUsersResponse(ArrayList<User> users) {
        blogDBHelper.updateUsers(users);
    }

    @Override
    public void onUsersError() {

    }

    @Override
    public void onCommentsResponse(ArrayList<Comment> comments) {
        blogDBHelper.updateComments(comments);
    }

    @Override
    public void onCommentsError() {

    }
}
