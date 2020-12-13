package com.meiyou.mvp;

import androidx.annotation.LayoutRes;

public interface View<P extends Presenter> {

    P getPresenter();

    @LayoutRes
    int initLayoutResId();
}
