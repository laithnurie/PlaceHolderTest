package com.laith.babylontest.viewmodel;

import android.view.View;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PostPortraitViewModelTest {

    @Mock
    View rootview;

    @Mock
    TextView postTitle;

    @Mock
    TextView postBody;

    @Mock
    TextView userName;

    @Mock
    TextView noOfComments;

    PostPortraitViewModel sut;

    @Before
    public void setUp() {
        initMocks(this);
        when(rootview.findViewById(R.id.txt_post_title)).thenReturn(postTitle);
        when(rootview.findViewById(R.id.txt_post_body)).thenReturn(postBody);
        when(rootview.findViewById(R.id.txt_username)).thenReturn(userName);
        when(rootview.findViewById(R.id.txt_no_of_comments)).thenReturn(noOfComments);
        sut = new PostPortraitViewModel(rootview);
    }

    @Test
    public void whenValidPostIsSet() {
        Post post = createPost();
        sut.setPost(post);
        verify(postTitle, times(1)).setText(post.getTitle());
        verify(postBody, times(1)).setText(post.getBody());
        verify(userName, times(1)).setText(Integer.toString(post.getUserId()));
        verify(noOfComments, times(1)).setText(Integer.toString(post.getId()));
    }

    private Post createPost() {
        Post post = new Post();
        post.setId(1);
        post.setUserId(1);
        post.setTitle("post title");
        post.setBody("post body");

        return post;
    }

}