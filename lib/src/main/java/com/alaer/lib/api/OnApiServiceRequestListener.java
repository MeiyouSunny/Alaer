package com.alaer.lib.api;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import likly.reverse.HttpMethod;
import likly.reverse.OnServiceInvokeListener;
import likly.reverse.RequestHolder;

public class OnApiServiceRequestListener implements OnServiceInvokeListener {

    private String TOKEN_KEY;

    @Override
    public RequestHolder onServiceInvoke(Method method, RequestHolder requestHolder) {
        // 这里处于发送请求前,可以对请求Url,参数,Header作处理

        // 添加通用参数,统一都放到Header
        Map<String, String> params = null;
        Object body = requestHolder.body();

        if (requestHolder.method() == HttpMethod.GET || requestHolder.method() == HttpMethod.PUT) {
            params = requestHolder.headers();
            if (body != null && body instanceof Map) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) body).entrySet()) {
                    Object value = entry.getValue();
                    if (value != null)
                        params.put(entry.getKey(), value.toString());
                }
            }
        } else {
            if (body != null && body instanceof Map) {
                params = (Map<String, String>) body;
            } else {
                params = new HashMap<>();
            }
        }

        if (params == null)
            params = new HashMap<>();
        // 通用参数
        params.put("commapp", "1100");
        params.put("commappversion", "1.0.0");
        params.put("commdiamond", "174");
        params.put("commdid", "android-9beb26ac97fb4b87a8f2154d442165be");
        params.put("commlocale", "zh_CN");
        params.put("commmodel", "web-model");
        params.put("commos", "android");
        params.put("commuid", "");
        params.put("commuuid", "");

        if (requestHolder.method() == HttpMethod.GET || requestHolder.method() == HttpMethod.PUT) {
            requestHolder.headers(params);
        } else {
            if (body != null && body instanceof Map) {
                // body中是键值对参数
                requestHolder.body(params);
            } else {
                params.putAll(requestHolder.headers());
                requestHolder.headers(params);
            }
            requestHolder.headers().put("Content-Type", "application/json; charset=UTF-8");
        }

        String url = requestHolder.url();
        Log.e("Http", "Url: " + requestHolder.url());

        return requestHolder;
    }


}
