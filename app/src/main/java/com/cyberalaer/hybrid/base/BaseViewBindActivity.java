package com.cyberalaer.hybrid.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meiyou.mvp.BaseActivity;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseViewBindActivity<T extends ViewDataBinding> extends BaseActivity implements BindClickListener {

    public T bindRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        bindRoot = (T) DataBindingUtil.setContentView(this, layoutId());
        setEventHandler();

        onViewCreated();
    }

    @Override
    public void onViewCreated() {

    }

    protected abstract int layoutId();

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

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
