package com.laith.babylontest.db;

import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;

import java.util.ArrayList;

public interface DBHelper {
    void updatePosts(ArrayList<Post> posts);
    void updateUsers(ArrayList<User> users);
    void updateComments(ArrayList<Comment> users);
    ArrayList<Post> getAllPosts();
    ArrayList<Comment> getCommentsByPostId(int postId);
    User getBriefUserInfo(int userId);
    User getFullUserInfo(int userID);
}
