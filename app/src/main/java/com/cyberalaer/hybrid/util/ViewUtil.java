package com.cyberalaer.hybrid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ViewUtil {

    public static void gotoActivity(Context context, Class<? extends Activity> activityDes) {
        context.startActivity(new Intent(context, activityDes));
    }

    public static String getText(TextView textView) {
        if (textView == null)
            return "";
        return textView.getText().toString().trim();
    }

    public static void setText(TextView textView, String text) {
        if (textView == null || TextUtils.isEmpty(text))
            return;
        textView.setText(text);
    }

    public static void showImage(Context context, ImageView imageView, String imageUrl) {
        if (imageView == null || TextUtils.isEmpty(imageUrl))
            return;
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .into(imageView);
    }

}
