package com.alaer.lib.api;

import android.text.TextUtils;
import android.util.Log;

import com.alaer.lib.R;
import com.alaer.lib.util.NetworkUtil;

import likly.dollar.$;

public abstract class Callback<T> implements likly.reverse.Callback<T> {

    @Override
    public void onStart() {
        Log.e("Callback", "");
    }

    @Override
    public void onCancel() {
        Log.e("Callback", "");
    }

    @Override
    public void onFinish() {
        Log.e("Callback", "");
    }

    @Override
    public void onResponse(T response) {
        Log.e("Callback", "");
    }

    public void onError(int code, String msg) {

    }

    @Override
    public void onError(Throwable throwable) {
        handleError(throwable);
    }

    /**
     * 错误处理
     */
    private void handleError(Throwable error) {
        if (error instanceof ServiceError) {
            // 服务器已定义的错误
            final ServiceError exception = (ServiceError) error;
            onError(exception.code, exception.msg);
        } else {
            // 请求错误
            if (!TextUtils.isEmpty(error.getMessage()) && !(error instanceof IllegalStateException))
                onError(-1, error.getMessage());
        }
    }

    public boolean isNetworkAvailable() {
        // 网络判断
        boolean available = NetworkUtil.isNetworkAvailable();
        if (!available) {
            $.toast().text(R.string.network_not_available).show();
        }

        return available;
    }

}
