package com.laith.babylontest.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.laith.babylontest.BabylonApp;
import com.laith.babylontest.R;
import com.laith.babylontest.db.DBHelper;
import com.laith.babylontest.viewmodel.UserPortraitViewModel;

import javax.inject.Inject;


public class UserActivity extends AppCompatActivity {

    @Inject
    DBHelper blogDBHelper;

    private static String USER_ID_PARAM = "userID";
    private UserPortraitViewModel userPortraitViewModel;

    public static Intent getIntent(int userId, Context context) {
        Intent userIntent = new Intent(context, UserActivity.class);
        userIntent.putExtra(USER_ID_PARAM, userId);
        return userIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        BabylonApp.getAppComponent(this).inject(this);

        Bundle bundle = getIntent().getExtras();
        int userID = bundle.getInt(USER_ID_PARAM);
        userPortraitViewModel = new UserPortraitViewModel(
                findViewById(android.R.id.content), blogDBHelper, this, savedInstanceState);
        userPortraitViewModel.onCreate();
        userPortraitViewModel.setUserID(userID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPortraitViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userPortraitViewModel.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        userPortraitViewModel.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPortraitViewModel.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        userPortraitViewModel.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
