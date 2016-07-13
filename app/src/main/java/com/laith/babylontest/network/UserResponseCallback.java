package com.laith.babylontest.network;

import com.laith.babylontest.model.User;
import java.util.ArrayList;

public interface UserResponseCallback {
    void onUsersResponse(ArrayList<User> users);
}

