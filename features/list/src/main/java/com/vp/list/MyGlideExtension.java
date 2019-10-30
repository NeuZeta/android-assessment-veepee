package com.vp.list;

import android.content.Context;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension(){}

    @GlideOption
    public static void setLoader (RequestOptions options, Context context) {

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.start();

        options.placeholder(progressDrawable);
    }

}
