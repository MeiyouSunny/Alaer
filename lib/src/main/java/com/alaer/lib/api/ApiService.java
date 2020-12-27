package com.alaer.lib.api;

import likly.reverse.Call;
import likly.reverse.annotation.BaseUrl;
import likly.reverse.annotation.CallExecuteListener;
import likly.reverse.annotation.FormBody;
import likly.reverse.annotation.GET;
import likly.reverse.annotation.POST;
import likly.reverse.annotation.Part;
import likly.reverse.annotation.Query;
import likly.reverse.annotation.ServiceInvokeListener;

/**
 * 请求定义
 */
@BaseUrl(AppConfig.BASE_URL)
@ServiceInvokeListener(OnApiServiceRequestListener.class)
@CallExecuteListener(ApiCallExecuteListener.class)
@SuppressWarnings("all")
public interface ApiService {

//    @GET("/query")
//    Call<String> request(@Query("username") String userName, Callback<String> callback);
//
//    @GET("/query")
//    Call<String> request(@QueryMap Map<String, String> params, Callback<String> callback);

    /**
     * 登录
     */
    @FormBody
    @POST("/user/signin")
    Call<String> login(@Part("phone") String phone, @Part("password") String password, @Part("validate") String validate, @Part("source") String source,
                       Callback<String> callback);

    /**
     * 获取验证码
     */
    @GET("/user/vcode")
    Call<String> getVCode(@Query("diallingCode") String diallingCode, @Query("email") String email, @Query("captchaId") String captchaId,
                         @Query("validate") String validate, @Query("type") String type,
                         Callback<String> callback);

}
