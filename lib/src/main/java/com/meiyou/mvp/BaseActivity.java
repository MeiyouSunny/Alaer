package com.meiyou.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity<P extends Presenter> extends AppCompatActivity implements ViewHandler<P> {

    private MVP mMVP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMVP = new MVP();
        mMVP.onCreate();
        mMVP.bind(this);
        onViewCreated();
    }

    @Override
    public P getPresenter() {
        return (P) mMVP.getPresenter();
    }

    @Override
    public int initLayoutResId() {
        return -1;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mMVP.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMVP.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMVP.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMVP.onDestroy();
    }

}
