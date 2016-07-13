package com.laith.babylontest.viewmodel;

import android.os.Bundle;

public interface UserViewModel {
    void onCreate();
    void onResume();
    void onPause();
    void onLowMemory();
    void onDestroy();
    void onSaveInstanceState(Bundle outState);
}
