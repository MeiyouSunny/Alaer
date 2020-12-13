package com.cyberalaer.hybrid.base;

import com.cyberalaer.hybrid.util.NavigateUtil;
import com.meiyou.mvp.BaseFragment;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindFragment<T extends ViewDataBinding> extends BaseFragment {

    protected T bindRoot;

    @Override
    public void onViewCreated() {
        bindRoot = DataBindingUtil.bind(getView());
    }

    protected void setTitleText(@StringRes int text) {
        if (getActivity() instanceof TitleControl) {
            ((TitleControl) getActivity()).setTitleText(text);
        }
    }

    protected void navigate(int actionId) {
        NavigateUtil.navigate(this, actionId);
    }

}
