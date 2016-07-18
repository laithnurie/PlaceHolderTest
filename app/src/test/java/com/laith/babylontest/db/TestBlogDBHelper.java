package com.laith.babylontest.db;


import com.laith.babylontest.model.Comment;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;

import java.util.ArrayList;

public class TestBlogDBHelper implements DBHelper {
    @Override
    public void updatePosts(ArrayList<Post> posts) {

    }

    @Override
    public void updateUsers(ArrayList<User> users) {

    }

    @Override
    public void updateComments(ArrayList<Comment> users) {

    }

    @Override
    public ArrayList<Post> getAllPosts() {
        return null;
    }

    @Override
    public ArrayList<Comment> getCommentsByPostId(int postId) {
        return null;
    }

    @Override
    public User getBriefUserInfo(int userId) {
        return null;
    }

    @Override
    public User getFullUserInfo(int userID) {
        return null;
    }
}