package com.meiyou.mvp;

import android.content.Context;

public interface ViewHandler<P extends Presenter> extends View<P> {

    Context getContext();

    void onViewCreated();
}
