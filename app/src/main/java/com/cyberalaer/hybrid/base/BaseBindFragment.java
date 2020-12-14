package com.cyberalaer.hybrid.base;

import android.view.View;

import com.cyberalaer.hybrid.util.NavigateUtil;
import com.meiyou.mvp.BaseFragment;

import java.lang.reflect.Method;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindFragment<T extends ViewDataBinding> extends BaseFragment implements BindClickListener {

    protected T bindRoot;

    @Override
    public void onViewCreated() {
        bindRoot = DataBindingUtil.bind(getView());

        setEventHandler();
    }

    protected void setTitleText(@StringRes int text) {
        if (getActivity() instanceof TitleControl) {
            ((TitleControl) getActivity()).setTitleText(text);
        }
    }

    protected void navigate(int actionId) {
        NavigateUtil.navigate(this, actionId);
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
