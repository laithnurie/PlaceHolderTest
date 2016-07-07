package com.laith.babylontest.network;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoadUtil {

    private final static String PROFILE_IMAGE_PATH = "https://api.adorable.io/avatars/285/";
    public static void loadUserImage(String email, Context context, ImageView imageView) {
        Picasso.with(context).load(PROFILE_IMAGE_PATH + email).into(imageView);
    }
}