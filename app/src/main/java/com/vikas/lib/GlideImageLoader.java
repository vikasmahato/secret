package com.vikas.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader{

    private RequestManager requestManager;

    public GlideImageLoader(Context context) {
        requestManager = Glide.with(context);
    }

    @Override
    public void load(ImageView imgAvatar, String url) {
        if(url != null)
            requestManager.load(url).into(imgAvatar);
    }
}