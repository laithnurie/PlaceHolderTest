package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.adapter.CommentsListAdapter;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.ImageLoadUtil;

import java.util.ArrayList;

public class PostPortraitViewModel implements PostViewModel {
    private final ImageView userImage;
    private final TextView postTitle;
    private final TextView postBody;
    private final TextView name;
    private final RecyclerView postCommentsList;
    private final DBHelper blogDBHelper;
    private final Context context;

    public PostPortraitViewModel(View rootView, DBHelper blogDBHelper, Context context) {
        userImage = (ImageView) rootView.findViewById(R.id.img_user);
        postTitle = (TextView) rootView.findViewById(R.id.txt_post_title);
        postBody = (TextView) rootView.findViewById(R.id.txt_post_body);
        name = (TextView) rootView.findViewById(R.id.txt_name);
        postCommentsList = (RecyclerView) rootView.findViewById(R.id.rv_post_comments);
        postCommentsList.setLayoutManager(new LinearLayoutManager(context));
        this.blogDBHelper = blogDBHelper;
        this.context = context;
    }

    @Override
    public void setPost(Post post) {
        postTitle.setText(post.getTitle());
        postBody.setText(post.getBody());
        User user = blogDBHelper.getBriefUserInfo(post.getUserId());
        if (user != null) {
            name.setText(user.getName());
            ImageLoadUtil.loadUserImage(user.getEmail(), context, userImage);
        }
        ArrayList<Comment> postComments = blogDBHelper.getCommentsByPostId(post.getId());
        if (postComments.size() > 0) {
            postCommentsList.setAdapter(new CommentsListAdapter(context, postComments));
        }
    }
}
