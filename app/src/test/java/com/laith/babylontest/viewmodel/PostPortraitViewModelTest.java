package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;
import com.laith.babylontest.model.User;
import com.laith.babylontest.network.ImageLoadUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PostPortraitViewModelTest.class, ImageLoadUtil.class})
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
    TextView noOfComments;

    @Mock
    ImageView userImage;

    private PostPortraitViewModel sut;

    @Before
    public void setUp() {
        initMocks(this);
        mockStatic(ImageLoadUtil.class);

        when(rootview.findViewById(R.id.txt_post_title)).thenReturn(postTitle);
        when(rootview.findViewById(R.id.txt_post_body)).thenReturn(postBody);
        when(rootview.findViewById(R.id.txt_name)).thenReturn(name);
        when(rootview.findViewById(R.id.txt_no_of_comments)).thenReturn(noOfComments);
        when(rootview.findViewById(R.id.img_user)).thenReturn(userImage);
        sut = new PostPortraitViewModel(rootview, dbHelper, context);
    }

    @Test
    public void whenValidPostWithUser() {
        Post post = createPost();
        User briefUserInfo = new User();
        briefUserInfo.setEmail("test@example.com");
        briefUserInfo.setName("Test");

        when(dbHelper.getBriefUserInfo(post.getUserId())).thenReturn(briefUserInfo);
        sut.setPost(post);
        verify(postTitle, times(1)).setText(post.getTitle());
        verify(postBody, times(1)).setText(post.getBody());
        verify(name, times(1)).setText(briefUserInfo.getName());
        verify(noOfComments, times(1)).setText(Integer.toString(post.getId()));

        verifyStatic(times(1));
        ImageLoadUtil.loadUserImage(briefUserInfo.getEmail(), context, userImage);
    }

    @Test
    public void whenValidPostWithoutUser() {
        Post post = createPost();
        when(dbHelper.getBriefUserInfo(post.getUserId())).thenReturn(null);
        sut.setPost(post);
        verify(postTitle, times(1)).setText(post.getTitle());
        verify(postBody, times(1)).setText(post.getBody());
        verify(name, never()).setText(anyString());
        verify(noOfComments, times(1)).setText(Integer.toString(post.getId()));

        verifyStatic(times(0));
        ImageLoadUtil.loadUserImage(null, context, userImage);
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