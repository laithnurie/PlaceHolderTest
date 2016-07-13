package com.laith.babylontest.viewmodel;

import android.content.Context;
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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImageLoadUtil.class})
public class PostPortraitViewModelTest {

    @Mock
    View rootview;

    @Mock
    Context context;

    @Mock
    DBHelper dbHelper;

    @Mock
    TextView postTitle;

    @Mock
    TextView postBody;

    @Mock
    TextView name;

    @Mock
    RecyclerView postCommentsList;

    @Mock
    ImageView userImage;

    @Mock
    UserClickListener userClickListener;

    private PostPortraitViewModel sut;

    @Before
    public void setUp() {
        initMocks(this);
        mockStatic(ImageLoadUtil.class);

        when(rootview.findViewById(R.id.txt_post_title)).thenReturn(postTitle);
        when(rootview.findViewById(R.id.txt_post_body)).thenReturn(postBody);
        when(rootview.findViewById(R.id.txt_name)).thenReturn(name);
        when(rootview.findViewById(R.id.rv_post_comments)).thenReturn(postCommentsList);
        when(rootview.findViewById(R.id.img_user)).thenReturn(userImage);
        sut = new PostPortraitViewModel(rootview, dbHelper, context, userClickListener);
    }

    @Test
    public void whenPostWithUser() {
        Post post = createPost();
        User briefUserInfo = new User();
        briefUserInfo.setEmail("test@example.com");
        briefUserInfo.setName("Test");
        ArrayList<Comment> comments = createComments();

        when(dbHelper.getBriefUserInfo(post.getUserId())).thenReturn(briefUserInfo);
        when(dbHelper.getCommentsByPostId(post.getId())).thenReturn(comments);
        sut.setPost(post);

        verify(postTitle, times(1)).setText(post.getTitle());
        verify(postBody, times(1)).setText(post.getBody());
        verify(name, times(1)).setText(briefUserInfo.getName());
        verify(postCommentsList, times(1)).setAdapter(isA(CommentsListAdapter.class));

        verifyStatic(times(1));
        ImageLoadUtil.loadUserImage(briefUserInfo.getEmail(), context, userImage);
    }

    @Test
    public void whenPostWithoutUser() {
        Post post = createPost();
        when(dbHelper.getBriefUserInfo(post.getUserId())).thenReturn(null);
        sut.setPost(post);
        verify(postTitle, times(1)).setText(post.getTitle());
        verify(postBody, times(1)).setText(post.getBody());
        verify(name, never()).setText(anyString());

        verifyStatic(times(0));
        ImageLoadUtil.loadUserImage(null, context, userImage);
    }

    @Test
    public void whenPostWithoutComments() {
        Post post = createPost();
        when(dbHelper.getBriefUserInfo(post.getUserId())).thenReturn(null);
        when(dbHelper.getCommentsByPostId(post.getId())).thenReturn(new ArrayList<Comment>());

        sut.setPost(post);
        verify(postCommentsList, never()).setAdapter(isA(CommentsListAdapter.class));
    }

    private Post createPost() {
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        post.setTitle("post title");
        post.setBody("post body");
        return post;
    }

    private ArrayList<Comment> createComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Comment comment = new Comment();
            comment.setBody("body " + i);
            comment.setName("name " + i);
            comment.setEmail("email " + i + "@test.com");
            comments.add(comment);
        }
        return comments;
    }
}