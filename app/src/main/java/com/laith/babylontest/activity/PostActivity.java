package com.laith.babylontest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostPortraitViewModel;

import javax.inject.Inject;

public class PostActivity extends AppCompatActivity {

    @Inject
    private DBHelper blogDBHelper;

    private final static String POST_PARAM = "post";

    public static Intent getIntent(Post post, Context context) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(POST_PARAM, post);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        BabylonApp.getAppComponent(this).inject(this);

        Bundle bundle = getIntent().getExtras();
        Post post = bundle.getParcelable(POST_PARAM);

        PostPortraitViewModel postViewModel = new PostPortraitViewModel(findViewById(android.R.id.content)
                , blogDBHelper, this);
        if (post != null) {
            postViewModel.setPost(post);
        }
    }
}
