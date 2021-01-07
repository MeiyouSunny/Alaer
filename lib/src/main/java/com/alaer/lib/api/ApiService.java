package com.alaer.lib.api;

import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.Notice;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.SeedStoreList;
import com.alaer.lib.api.bean.SharedUserDetail;
import com.alaer.lib.api.bean.SharedUserList;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.TeamProfile;
import com.alaer.lib.api.bean.UserData;

import java.util.List;

import likly.reverse.Call;
import likly.reverse.annotation.BaseUrl;
import likly.reverse.annotation.CallExecuteListener;
import likly.reverse.annotation.FormBody;
import likly.reverse.annotation.GET;
import likly.reverse.annotation.POST;
import likly.reverse.annotation.PUT;
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
    Call<UserData> login(@Part("phone") String phone, @Part("password") String password, @Part("validate") String validate,
                         @Part("captchaId") String captchaId, @Part("source") String source,
                         Callback<UserData> callback);

    /**
     * 获取验证码
     */
    @GET("/user/vcode")
    Call<String> getVCode(@Query("diallingCode") String diallingCode, @Query("email") String email, @Query("captchaId") String captchaId,
                          @Query("validate") String validate, @Query("type") String type,
                          Callback<String> callback);

    /**
     * 注册
     */
    @FormBody
    @POST("/user/signup")
    Call<UserData> regist(@Part("phone") String phone, @Part("vcode") String vcode, @Part("password") String password, @Part("inviteCode") String inviteCode,
                          @Part("validate") String validate, @Part("captchaId") String captchaId, @Part("diallingCode") String diallingCode,
                          Callback<UserData> callback);

    /**
     * 重置密码
     */
    @FormBody
    @POST("/user/reset")
    Call<String> resetPwd(@Part("phone") String phone, @Part("phoneCode") String phoneCode, @Part("password") String password,
                          @Part("validate") String validate, @Part("captchaId") String captchaId,
                          Callback<String> callback);

    /**
     * 获取用户信息
     */
    @GET("/mining/team/userinfo")
    Call<String> getUserInfo(@Query("uid") int uid, @Query("teamUuid") String teamUuid, @Query("token") String token,
                             Callback<String> callback);

    /**
     * 获取用户信息
     */
    @GET("/mining/team/details")
    Call<TeamDetail> getTeamDetailInfo(@Query("uuid") String uuid, @Query("uid") String uid,
                                       @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                       Callback<TeamDetail> callback);

    /**
     * 邀请人信息
     */
    @GET("/mining/team/info/follow")
    Call<TeamDetail> getFollowInfo(@Query("uuid") String uuid, @Query("uid") String uid,
                                   @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                   Callback<TeamDetail> callback);

    @GET("/mining/info")
    Call<TeamInfo> info(@Query("uuid") String uuid, @Query("uid") String uid,
                        @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                        Callback<TeamInfo> callback);

    /**
     * 贡献值
     */
    @GET("/mining/diamond/balance")
    Call<Balance> getBalance(@Query("uuid") String uuid, @Query("uid") String uid,
                             @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                             Callback<Balance> callback);

    /**
     * 开始种植
     */
    @FormBody
    @POST("/mining/start/all")
    Call<String> produceStart(@Part("uuid") String uuid, @Part("uid") String uid,
                              @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                              Callback<String> callback);

    /**
     * 更新步骤
     */
    @FormBody
    @PUT("/mining/step")
    Call<String> updateProduceStep(@Query("uuid") String uuid, @Query("uid") String uid,
                                   @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                   Callback<String> callback);

    /**
     * 拉取任务列表(Action:2)
     */
    @GET("/mining/ad/tasks")
    Call<List<AdTask>> adTasks(@Query("uuid") String uuid, @Query("uid") String uid,
                               @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                               @Query("action") String action,
                               Callback<List<AdTask>> callback);

    /**
     * 开始任务
     */
    @FormBody
    @POST("/mining/ad/task/start")
    Call<String> startTask(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token,
                           @Part("diamondCurrency") String diamondCurrency, @Part("taskId") String taskId,
                           Callback<String> callback);

    /**
     * 完成任务
     */
    @FormBody
    @POST("/mining/ad/task/complete")
    Call<String> completeTask(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token,
                              @Part("diamondCurrency") String diamondCurrency, @Part("taskId") String taskId,
                              Callback<String> callback);

    /**
     * 收获
     */
    @FormBody
    @POST("/mining/stop/all")
    Call<String> produceFinish(@Part("uuid") String uuid, @Part("uid") String uid,
                               @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                               @Part("captchaId") String captchaId, @Part("validate") String validate,
                               Callback<String> callback);

    /**
     * 用户的树苗
     * status：1.正常；-1.过期
     */
    @GET("/mining/user/miner/query")
    Call<List<SeedMine>> mySeeds(@Query("uuid") String uuid, @Query("uid") String uid,
                                 @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                 @Query("status") int status, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                 Callback<List<SeedMine>> callback);

    /**
     * 种子商店
     */
    @GET("/mining/miner/query/publish")
    Call<SeedStoreList> seedStore(@Query("uuid") String uuid, @Query("uid") String uid,
                                  @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                  @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                  Callback<SeedStoreList> callback);

    /**
     * 购买树苗
     */
    @FormBody
    @POST("/mining/user/miner")
    Call<String> bugSeed(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token,
                         @Part("diamondCurrency") String diamondCurrency, @Part("minerId") String minerId,
                         Callback<String> callback);

    /**
     * 公告列表
     */
    @GET("/announce/pageList")
    Call<List<Notice>> noticeList(@Query("page") int page, @Query("rows") int rows, @Query("appCode") int appCode,
                                  Callback<List<Notice>> callback);

    /**
     * 团队星级别
     */
    @GET("/mining/team/stars")
    Call<List<TeamLevel>> teamStarLevel(@Query("uuid") String uuid, @Query("uid") String uid,
                                        @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                        Callback<List<TeamLevel>> callback);

    /**
     * 查询团队信息
     */
    @GET("/mining/team/profile")
    Call<TeamProfile> teamProfile(@Query("uuid") String uuid, @Query("uid") String uid,
                                  @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                  Callback<TeamProfile> callback);

    /**
     * 获取分享用户列表
     * 排序字段：team_activeness.伙伴活跃度；num.伙伴人数；level.市民星级；uid.注册时间
     * 排序：asc.正序；desc.倒序
     */
    @GET("/mining/team/direct/users")
    Call<SharedUserList> teamSharedUserList(@Query("uuid") String uuid, @Query("uid") String uid,
                                            @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                            @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                            @Query("orderField") String orderField, @Query("orderDirection") String orderDirection,
                                            Callback<SharedUserList> callback);

    /**
     * 获取分享用户详情
     */
    @GET("/mining/team/userinfo")
    Call<SharedUserDetail> teamSharedUserDetail(@Query("uuid") String uuid, @Query("uid") String uid,
                                              @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                              @Query("teamUuid") String teamUuid,
                                              Callback<SharedUserDetail> callback);

}
