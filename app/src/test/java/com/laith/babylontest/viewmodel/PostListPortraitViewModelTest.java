package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.activity.PostNetworkCall;
import com.laith.babylontest.adapter.PostsListAdapter;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.model.Post;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PostListPortraitViewModelTest.class)
public class PostListPortraitViewModelTest {

    @Mock
    View rootView;

    @Mock
    Context context;

    @Mock
    PostNetworkCall postNetworkCall;

    @Mock
    DBHelper dbHelper;

    @Mock
    Bundle savedInstanceState;

    @Mock
    RecyclerView postsList;


    @Before
    public void setUp() {
        initMocks(this);
    }


    @Test
    public void initialTest() throws Exception {
        ArrayList<Post> posts = createPosts();
        doReturn(posts).when(savedInstanceState).getParcelableArrayList("posts");
        when(rootView.findViewById(R.id.postList)).thenReturn(postsList);
        PostListPortraitViewModel sut = new PostListPortraitViewModel();
        sut.initialise(rootView, context,
                postNetworkCall, dbHelper, savedInstanceState);
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    private ArrayList<Post> createPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Post post = new Post();
            post.setId(i);
            post.setUserId(i);
            post.setTitle("title - " + i);
            post.setBody("body - " + i);
            posts.add(post);
        }
        return posts;
    }

}