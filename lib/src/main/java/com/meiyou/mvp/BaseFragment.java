package com.meiyou.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment<P extends Presenter> extends Fragment implements ViewHandler<P> {
    private MVP mMVP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mMVP.bind(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMVP.onBind(this);
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
    public void onViewCreated() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMVP = new MVP();
        mMVP.onCreate();
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
