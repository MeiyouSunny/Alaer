package com.alaer.lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.AvatarUploadResult;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import likly.dollar.$;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 头像上传
 */
public class AvatarUploader {
    private Context mContext;
    private String mPicPath;

    private OnUploadListener mListener;

    public void setmListener(OnUploadListener listener) {
        this.mListener = listener;
    }

    public interface OnUploadListener {
        void onUploadResult(boolean success, String picUrl);
    }

    public AvatarUploader(Context context) {
        this.mContext = context;
    }

    public void upload(String picPath) {
        mPicPath = picPath;
        new Thread() {
            @Override
            public void run() {
                super.run();
                execute();
            }
        }.start();
    }

    private void execute() {
        OkHttpClient okHttpClient = new OkHttpClient();

        String data = buildBase64();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), data);

        Request request = new Request.Builder()
                .url(buildUrl())
                .headers(buildHeaders())
                .post(requestBody)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            handleResult(result);
        } catch (IOException e) {
            e.printStackTrace();
            if (mListener != null)
                mListener.onUploadResult(false, "");
        }
    }

    private void handleResult(String result) {
        if (mListener == null || TextUtils.isEmpty(result))
            return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AvatarUploadResult resultInfo = $.json().toBean(result, AvatarUploadResult.class);
                if (resultInfo != null && resultInfo.attachment != null && !TextUtils.isEmpty(resultInfo.attachment.url)) {
                    mListener.onUploadResult(true, resultInfo.attachment.url);
                } else {
                    mListener.onUploadResult(false, "");
                }
            }
        });
    }

    private String buildUrl() {
        String url = AppConfig.BASE_URL + "/system/upload/base64";
        UserData userData = UserDataUtil.instance().getUserData();
        url = url + "?uid=" + userData.uid
                + "&uuid=" + userData.uuid
                + "&token=" + userData.token
                + "&diamondCurrency=174"
                + "&qrcode=0";
        return url;
    }

    private Headers buildHeaders() {
        Map<String, String> params = new HashMap<>();
        params.put("commapp", "1100");
        params.put("commappversion", "1.0.0");
        params.put("commdiamond", "174");
        params.put("commdid", "android-9beb26ac97fb4b87a8f2154d442165be");
        params.put("commlocale", "zh_CN");
        params.put("commmodel", "android-model");
        params.put("commos", "android");
        params.put("User-Agent", "MNC/1.0 Android 9 Okhttp");
        params.put("Content-Type", "application/x-www-form-urlencoded");

        if (UserDataUtil.instance().getUserData() != null) {
            final int uid = UserDataUtil.instance().getUserData().uid;
            if (uid != 0)
                params.put("commuid", String.valueOf(uid));
            final String uuid = UserDataUtil.instance().getUserData().uuid;
            if (!TextUtils.isEmpty(uuid))
                params.put("commuuid", uuid);
            final String token = UserDataUtil.instance().getUserData().token;
            if (!TextUtils.isEmpty(token))
                params.put("commtoken", token);
        }

        Headers headers = Headers.of(params);
        return headers;
    }

    public String buildBase64() {
        Bitmap bitmap = BitmapFactory.decodeFile(mPicPath);
        String base64 = bitmapToBase64(bitmap);
        base64 = "data:image/png;base64," + base64;
        try {
            String result = URLEncoder.encode(base64, "UTF-8");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
