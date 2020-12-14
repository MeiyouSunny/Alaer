package com.cyberalaer.hybrid.base;

import android.os.Bundle;
import android.view.View;

import com.meiyou.mvp.BaseActivity;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseViewBindActivity<T extends ViewDataBinding> extends BaseActivity implements BindClickListener {

    protected T bindRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindRoot = (T) DataBindingUtil.setContentView(this, layoutId());
        setEventHandler();
    }

    protected abstract int layoutId();

    private void setEventHandler() {
        try {
            Method method = bindRoot.getClass().getMethod("setHandler", BindClickListener.class);
            if (method != null) {
                method.invoke(bindRoot, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void click(View view) {
    }

}
