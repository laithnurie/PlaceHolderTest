package com.laith.babylontest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Post;

public class PostPortraitViewModel implements PostViewModel {
    private final TextView postTitle;
    private final TextView postBody;
    private final TextView userName;
    private final TextView noOfComments;
    private Post mPost;

    public PostPortraitViewModel(View rootView) {
        postTitle = (TextView) rootView.findViewById(R.id.txt_post_title);
        postBody = (TextView) rootView.findViewById(R.id.txt_post_body);
        userName = (TextView) rootView.findViewById(R.id.txt_username);
        noOfComments = (TextView) rootView.findViewById(R.id.txt_no_of_comments);
    }

    @Override
    public void setPost(Post post) {
        mPost = post;
        postTitle.setText(mPost.getTitle());
        postBody.setText(mPost.getBody());
        userName.setText(Integer.toString(mPost.getUserId()));
        noOfComments.setText(Integer.toString(mPost.getId()));
    }

    @Override
    public void onSavedInstance(Bundle outState) {

    }
}
