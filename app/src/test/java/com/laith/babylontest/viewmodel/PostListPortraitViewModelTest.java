package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.laith.babylontest.R;
import com.laith.babylontest.activity.PostActivity;
import com.laith.babylontest.network.PostNetworkCall;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PostListPortraitViewModelTest.class, PostActivity.class})
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

    private PostListPortraitViewModel sut;

    @Before
    public void setUp() {
        initMocks(this);
        when(rootView.findViewById(R.id.postList)).thenReturn(postsList);
    }

    @Test
    public void WhenPostsAvailableInSavedInstance() {
        ArrayList<Post> posts = createPosts();
        doReturn(posts).when(savedInstanceState).getParcelableArrayList("posts");
        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, savedInstanceState);
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    @Test
    public void WhenSavedInstanceDoesntContainPosts() {
        doReturn(null).when(savedInstanceState).getParcelableArrayList("posts");
        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, savedInstanceState);
        verify(postNetworkCall, times(1)).getPosts(sut);
    }

    @Test
    public void whenMultiplePostsReturned() {
        ArrayList<Post> posts = createPosts();
        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, null);
        verify(postNetworkCall, times(1)).getPosts(sut);
        sut.onPostsResponse(posts);
        verify(dbHelper, times(1)).updatePosts(posts);
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    @Test
    public void whenEmptyPostsReturnedUseOfflineData() {

        ArrayList<Post> emptyPost = new ArrayList<>();
        ArrayList<Post> offlinePosts = createPosts();
        when(dbHelper.getAllPosts()).thenReturn(offlinePosts);

        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, null);
        verify(postNetworkCall, times(1)).getPosts(sut);
        sut.onPostsResponse(emptyPost);
        verify(dbHelper, times(1)).getAllPosts();
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    @Test
    public void whenNullPostsReturnedUseOfflineData() {
        ArrayList<Post> offlinePosts = createPosts();
        when(dbHelper.getAllPosts()).thenReturn(offlinePosts);

        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, null);
        verify(postNetworkCall, times(1)).getPosts(sut);
        sut.onPostsResponse(null);
        verify(dbHelper, times(1)).getAllPosts();
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    @Test
    public void whenPostsErrorReturnedUseOfflineData() {
        ArrayList<Post> offlinePosts = createPosts();
        when(dbHelper.getAllPosts()).thenReturn(offlinePosts);

        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, null);
        verify(postNetworkCall, times(1)).getPosts(sut);
        sut.onPostsError();
        verify(dbHelper, times(1)).getAllPosts();
        verify(postsList, times(1)).setAdapter(isA(PostsListAdapter.class));
    }

    @Test
    public void whenOnSaveInstanceStateIsCalled() {
        ArrayList<Post> posts = createPosts();
        doReturn(posts).when(savedInstanceState).getParcelableArrayList("posts");
        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, savedInstanceState);
        sut.onSaveInstanceState(savedInstanceState);
        verify(savedInstanceState, times(1)).putParcelableArrayList("posts", posts);
    }

    @Test
    public void whenOnPostClicked() {
        mockStatic(PostActivity.class);

        Post postClicked = new Post();
        postClicked.setTitle("test");
        postClicked.setBody("body");
        postClicked.setUserId(1);
        postClicked.setId(1);

        Intent mockedIntent = mock(Intent.class);
        sut = new PostListPortraitViewModel(rootView, context,
                postNetworkCall, dbHelper, null);
        when(PostActivity.getIntent(postClicked, context)).thenReturn(mockedIntent);
        sut.onClick(postClicked);
        verify(context, times(1)).startActivity(mockedIntent);
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