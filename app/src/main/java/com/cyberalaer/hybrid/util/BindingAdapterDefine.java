package com.cyberalaer.hybrid.util;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class BindingAdapterDefine {

    @BindingAdapter({"app:drawableRes"})
    public static void ygetTransImageView(View view, int res) {
        view.setBackgroundResource(res);
    }

}
