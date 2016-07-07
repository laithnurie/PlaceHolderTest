package com.laith.babylontest.viewmodel;

import android.view.View;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Post;

public class PostPortraitViewModel implements PostViewModel {
    private final TextView postTitle;
    private final TextView postBody;
    private final TextView userName;
    private final TextView noOfComments;

    public PostPortraitViewModel(View rootView) {
        postTitle = (TextView) rootView.findViewById(R.id.txt_post_title);
        postBody = (TextView) rootView.findViewById(R.id.txt_post_body);
        userName = (TextView) rootView.findViewById(R.id.txt_username);
        noOfComments = (TextView) rootView.findViewById(R.id.txt_no_of_comments);
    }

    @Override
    public void setPost(Post post) {
        postTitle.setText(post.getTitle());
        postBody.setText(post.getBody());
        userName.setText(Integer.toString(post.getUserId()));
        noOfComments.setText(Integer.toString(post.getId()));
    }
}
