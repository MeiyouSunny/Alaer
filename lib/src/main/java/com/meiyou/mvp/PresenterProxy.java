package com.meiyou.mvp;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class PresenterProxy {

    private final MVP mMVP;
    private final Presenter mPresenter;
    private final Presenter mProxy;

    public PresenterProxy(MVP MVP, Presenter presenter) {
        mMVP = MVP;
        mPresenter = presenter;

        mProxy = (Presenter) Proxy.newProxyInstance(
                presenter.getClass().getClassLoader(),
                presenter.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Log.e("METHOD:", method.getName());
                        if ("getView".equals(method.getName())) {
                            return mMVP.getView();
                        } else if ("getModel".equals(method.getName())) {
                            return mMVP.getModel();
                        } else {

                            return method.invoke(mPresenter, args);
                        }

                    }
                });
    }

    public Presenter getProxyPresenter() {
        return mProxy;
    }

}
