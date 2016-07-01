package com.laith.babylontest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.BlogDBHelper;
import com.laith.babylontest.service.FeedService;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostListViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends AppCompatActivity {

    private static final String POSTS_KEY = "posts";
    @Inject
    FeedService feedService;
    @Inject
    BlogDBHelper blogDBHelper;

    private PostListViewModel postListViewModel;
    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabylonApp.getAppComponent(this).inject(this);
        postListViewModel = new PostListViewModel(findViewById(android.R.id.content));

        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList(POSTS_KEY) != null) {
            posts = savedInstanceState.getParcelableArrayList(POSTS_KEY);
            postListViewModel.initialise(posts);
        } else {
            Call<ArrayList<Post>> postsCall = feedService.getPosts();
            postsCall.enqueue(new Callback<ArrayList<Post>>() {
                @Override
                public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                    if (response.body() != null && response.body().size() > 0) {
                        posts = response.body();
                        blogDBHelper.updatePosts(posts);
                        postListViewModel.initialise(posts);
                    } else {
                        posts = blogDBHelper.getAllPosts();
                        postListViewModel.initialise(posts);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                    if (blogDBHelper.getAllPosts() != null && blogDBHelper.getAllPosts().size() > 0) {
                        posts = blogDBHelper.getAllPosts();
                        postListViewModel.initialise(posts);
                    }
                }
            });
        }
//        Call<User[]> usersCall = placeholderService.getUsers();
//        usersCall.enqueue(new Callback<User[]>() {
//            @Override
//            public void onResponse(Call<User[]> call, final Response<User[]> response) {
//                Log.v("lnln", "success usersCall");
//                if (response.body() != null && response.body().length > 0) {
//                    User[] users = response.body();
//                    usersCount.setText(Integer.toString(users.length));
//                    dbHelper.updateUsers(users);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User[]> call, Throwable t) {
//
//            }
//        });
//
//        Call<Comment[]> commentsCall = placeholderService.getComments();
//        commentsCall.enqueue(new Callback<Comment[]>() {
//            @Override
//            public void onResponse(Call<Comment[]> call, final Response<Comment[]> response) {
//                Log.v("lnln", "success postsCall");
//                if (response.body() != null) {
//                    commentsCount.setText(Integer.toString(response.body().length));
//
//                    if (response.body() != null && response.body().length > 0) {
//                        for (Comment comment : response.body()) {
//                            dbHelper.addComment(comment);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Comment[]> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(POSTS_KEY, posts);
        super.onSaveInstanceState(outState);
    }
}
