package com.cyberalaer.hybrid.base;

import android.os.Bundle;

import com.meiyou.mvp.BaseActivity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseViewBindActivity<T extends ViewDataBinding> extends BaseActivity {

    protected T bindRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindRoot = (T) DataBindingUtil.setContentView(this, layoutId());
    }

    protected abstract int layoutId();

}
