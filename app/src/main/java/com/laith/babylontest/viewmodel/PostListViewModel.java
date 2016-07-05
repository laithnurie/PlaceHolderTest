package com.laith.babylontest.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.laith.babylontest.activity.PostNetworkCall;
import com.laith.babylontest.db.DBHelper;


public interface PostListViewModel {
    void onSaveInstanceState(Bundle bundle);
    void initialise(View rootview, Context context, PostNetworkCall networkCall,
                    DBHelper dbHelper, Bundle savedInstanceState);
}
