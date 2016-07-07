package com.laith.babylontest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.viewmodel.PostPortraitViewModel;

public class PostActivity extends AppCompatActivity {
    private static String POST_PARAM = "post";

    public static Intent getIntent(Post post, Context context) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(POST_PARAM, post);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Bundle bundle = getIntent().getExtras();
        Post post = bundle.getParcelable(POST_PARAM);

        PostPortraitViewModel postViewModel = new PostPortraitViewModel(findViewById(android.R.id.content));
        if (post != null) {
            postViewModel.setPost(post);
        }
    }
}
