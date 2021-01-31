package com.cyberalaer.hybrid.base;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.ui.user.LoginActivity;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.jaeger.library.StatusBarUtil;
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
        initView();
        onViewCreated();
    }

    protected void initView() {
        setStatusBar();
        bindRoot = (T) DataBindingUtil.setContentView(this, layoutId());

        setEventHandler();
    }

    @Override
    public void onViewCreated() {

    }

    protected abstract int layoutId();

    private void setStatusBar() {
//        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        getWindow().getDecorView().setSystemUiVisibility(option);
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setNavigationBarColor(Color.TRANSPARENT);

//        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setLightMode(this);
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

    protected boolean judgeLogined() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null) {
            ViewUtil.gotoActivity(this, LoginActivity.class);
            return false;
        }
        return true;
    }

}
