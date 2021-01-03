package com.alaer.lib.api;

import android.text.TextUtils;
import android.util.Log;

import com.alaer.lib.data.UserDataUtil;
import com.alaer.lib.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import likly.reverse.HttpMethod;
import likly.reverse.OnServiceInvokeListener;
import likly.reverse.RequestHolder;

public class OnApiServiceRequestListener implements OnServiceInvokeListener {

    private String TOKEN_KEY;

    @Override
    public RequestHolder onServiceInvoke(Method method, RequestHolder requestHolder) {
        // 这里处于发送请求前,可以对请求Url,参数,Header作处理

        if (requestHolder.url() != null && requestHolder.url().contains("mining")) {
            Log.e("Http", "mining");
            doSignForMiningModule(requestHolder);
        }

        // 添加通用参数,统一都放到Header
        Map<String, String> params = null;
        Object body = requestHolder.body();

        if (requestHolder.method() == HttpMethod.GET || requestHolder.method() == HttpMethod.PUT) {
            params = requestHolder.headers();
//            if (body != null && body instanceof Map) {
//                for (Map.Entry<String, Object> entry : ((Map<String, Object>) body).entrySet()) {
//                    Object value = entry.getValue();
//                    if (value != null)
//                        params.put(entry.getKey(), value.toString());
//                }
//            }
        } else {
            params = requestHolder.headers();
            if (body != null && body instanceof Map) {
//                params = (Map<String, String>) body;
//                params.putAll((Map<String, String>) body);
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
        params.put("commmodel", "android-model");
        params.put("commos", "android");
        params.put("User-Agent", "Alaer/1.0 Android 9 Okhttp");

        final int uid = UserDataUtil.instance().getUserData().uid;
        if (uid != 0)
            params.put("commuid", String.valueOf(uid));
        final String uuid = UserDataUtil.instance().getUserData().uuid;
        if (!TextUtils.isEmpty(uuid))
            params.put("commuuid", uuid);
        final String token = UserDataUtil.instance().getUserData().token;
        if (!TextUtils.isEmpty(token))
            params.put("commtoken", token);

        if (requestHolder.method() == HttpMethod.GET || requestHolder.method() == HttpMethod.PUT) {
            requestHolder.headers(params);
            requestHolder.headers().put("Content-Type", "application/json; charset=UTF-8");
        } else {
//            params.putAll(requestHolder.headers());
            requestHolder.headers(params);
            if (body != null && body instanceof Map) {
                // body中是键值对参数
//                requestHolder.body(params);
            }
            requestHolder.headers().put("Content-Type", "application/x-www-form-urlencoded");
        }

        String url = requestHolder.url();
        Log.e("Http", "Url: " + requestHolder.toString());

        return requestHolder;
    }

    private void doSignForMiningModule(RequestHolder requestHolder) {
        Object body = requestHolder.body();
        if (body == null || !(body instanceof Map)) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.putAll((Map<String, Object>) body);
        int nonce = new Random().nextInt(10000);
        // 添加 Timestamp 和 Nonce
        long timestamp = System.currentTimeMillis();
        params.put("Timestamp", System.currentTimeMillis());
        params.put("Nonce", nonce);
        // 排序
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        // 拼接参数
        StringBuilder stringBuilder = new StringBuilder();
        for (Object key : keys) {
            stringBuilder.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String signString = null;
        try {
            signString = URLEncoder.encode(stringBuilder.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        let salt = `$ {clientSalt}_${userinfo.s}` // userinfo为登录成功后返回的数据
//        let key = `${params.uid}_${params.token}_${salt}`

        String key = UserDataUtil.instance().getUserData().uid + "_"
                + UserDataUtil.instance().getUserData().token + "_"
                + AppConfig.CLIENT_SALT + "_"
                + UserDataUtil.instance().getTeamDetail().s;

//        let hash = CryptoJS.HmacSHA256(signString, key)
//        let hashString = hash.toString()
//        let signFromString = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(hashString))

        String hash = StringUtil.HMACSHA256(signString, key);
        String signFromString = StringUtil.base64(hash);

        // 添加到Header
        Map<String, String> headers = requestHolder.headers();
        if (headers == null)
            headers = new HashMap<>();
        headers.put("Timestamp", String.valueOf(timestamp));
        headers.put("Nonce", String.valueOf(nonce));
        headers.put("Authorization", signFromString);

        requestHolder.headers(headers);

        System.out.println("");
    }

}
