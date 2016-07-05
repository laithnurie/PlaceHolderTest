package com.laith.babylontest.activity;

import com.laith.babylontest.model.Comment;

import java.util.ArrayList;

public interface CommentResponseCallback {
    void onCommentsResponse(ArrayList<Comment> comments);
    void onCommentsError();
}
