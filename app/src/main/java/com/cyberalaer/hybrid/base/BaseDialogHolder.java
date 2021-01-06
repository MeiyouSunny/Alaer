package com.cyberalaer.hybrid.base;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Method;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import likly.dialogger.ViewHolder;

public class BaseDialogHolder<T extends ViewDataBinding> extends ViewHolder  implements BindClickListener {

    private Context mContext;
    public T bindRoot;

    public BaseDialogHolder(int layoutRes) {
        super(layoutRes);
    }

    public BaseDialogHolder(View view) {
        super(view);
    }

    protected Context getContext() {
        return mContext;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        mContext = view.getContext();
        bindRoot = DataBindingUtil.bind(view);
        setEventHandler();
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
