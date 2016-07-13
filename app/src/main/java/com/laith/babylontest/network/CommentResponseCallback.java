package com.laith.babylontest.network;

import com.laith.babylontest.model.Comment;

import java.util.ArrayList;

public interface CommentResponseCallback {
    void onCommentsResponse(ArrayList<Comment> comments);
}
