package com.alaer.lib.api;

import com.alaer.lib.api.bean.AccessPointInfo;
import com.alaer.lib.api.bean.ActiveBillList;
import com.alaer.lib.api.bean.ActiveDetail;
import com.alaer.lib.api.bean.AdTask;
import com.alaer.lib.api.bean.AdVideo;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.BannerList;
import com.alaer.lib.api.bean.CityChartData;
import com.alaer.lib.api.bean.CityMaster;
import com.alaer.lib.api.bean.CityMasterDetail;
import com.alaer.lib.api.bean.CityStatistic;
import com.alaer.lib.api.bean.CommonQuestionList;
import com.alaer.lib.api.bean.CurrencyRecord;
import com.alaer.lib.api.bean.FruitBill;
import com.alaer.lib.api.bean.Notice;
import com.alaer.lib.api.bean.OrderInfo;
import com.alaer.lib.api.bean.SeedMine;
import com.alaer.lib.api.bean.SeedStoreList;
import com.alaer.lib.api.bean.SharedUserDetail;
import com.alaer.lib.api.bean.SharedUserList;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.TeamLevel;
import com.alaer.lib.api.bean.TeamProfile;
import com.alaer.lib.api.bean.UpdateInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.api.bean.UserLevelList;

import java.util.List;

import likly.reverse.Call;
import likly.reverse.Callback;
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
@BaseUrl(com.alaer.lib.api.AppConfig.BASE_URL)
@ServiceInvokeListener(com.alaer.lib.api.OnApiServiceRequestListener.class)
@CallExecuteListener(com.alaer.lib.api.ApiCallExecuteListener.class)
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
                         @Part("captchaId") String captchaId, @Part("source") String source, @Part("diallingCode") String diallingCode,
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
                          @Part("validate") String validate, @Part("captchaId") String captchaId, @Part("diallingCode") String diallingCode,
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
     * 市民等级级别
     */
    @GET("/mining/user/level/query")
    Call<UserLevelList> userLevels(@Query("uuid") String uuid, @Query("uid") String uid,
                                   @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                   Callback<UserLevelList> callback);

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

    /**
     * 拉取任务列表(去除Action:2)
     */
    @GET("/mining/ad/tasks")
    Call<List<AdTask>> queryTasks(@Query("uuid") String uuid, @Query("uid") String uid,
                                  @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                  Callback<List<AdTask>> callback);

    /**
     * 获取轮播图、内容
     * 类型。1100：轮播图；1101：新手指南
     */
    @GET("/open/slide/query")
    Call<BannerList> banners(@Query("type") int type, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                             Callback<BannerList> callback);

    /**
     * 获取视频
     * @param type 1
     */
    @GET("/mining/slide/videos")
    Call<List<AdVideo>> getAdVideo(@Query("uuid") String uuid, @Query("uid") String uid,
                                   @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                   @Query("type") int type,
                                   Callback<List<AdVideo>> callback);

    /**
     * 查询账户流水 - 果实
     */
    @GET("/mining/profile/account/water/dmd/query")
    Call<List<FruitBill>> fruitBill(@Query("uuid") String uuid, @Query("uid") String uid,
                                    @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                    @Query("lastId") int lastId, @Query("size") int size,
                                    Callback<List<FruitBill>> callback);

    /**
     * 查询账户流水 - 元宇積分明细
     */
    @GET("/mining/profile/account/water/cny/query")
    Call<List<FruitBill>> buildScoreBill(@Query("uuid") String uuid, @Query("uid") String uid,
                                         @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                         @Query("lastId") int lastId, @Query("size") int size,
                                         Callback<List<FruitBill>> callback);

    /**
     * 查询活跃度明细 - 树苗
     */
    @GET("/mining/profile/activityness/query")
    Call<ActiveBillList> seedActivity(@Query("uuid") String uuid, @Query("uid") String uid,
                                      @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                      @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                      Callback<ActiveBillList> callback);

    /**
     * 活跃度明细
     */
    @GET("/mining/team/activityinfo")
    Call<ActiveDetail> activityDetail(@Query("uuid") String uuid, @Query("uid") String uid,
                                      @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                      @Query("minerId") String minerId,
                                      Callback<ActiveDetail> callback);

    /**
     * 贡献值明细
     */
    @GET("/mining/profile/contribution/query")
    Call<ActiveBillList> contributionRecord(@Query("uuid") String uuid, @Query("uid") String uid,
                                            @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                            @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                            Callback<ActiveBillList> callback);

    /**
     * 实名认证:创建支付订单
     */
    @FormBody
    @POST("/mining/alipay/create")
    Call<String> createPayOrder(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                @Part("name") String name, @Part("idNumber") String idNumber,
                                Callback<String> callback);

    /**
     * 实名认证：证件上传认证
     * @return
     */
    @FormBody
    @POST("/user/submitUserInfo")
    Call<String> submitCardInfo(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                @Part("firstName") String firstName, @Part("country") String country,
                                @Part("secondName") String secondName, @Part("idNumber") String idNumber,
                                @Part("positiveImages") String positiveImages, @Part("propertyOther") String propertyOther,
                                Callback<String> callback);

    /**
     * 实名认证:查询支付订单状态
     */
    @GET("/mining/alipay/detail")
    Call<OrderInfo> queryPayState(@Query("uuid") String uuid, @Query("uid") String uid,
                                  @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                  @Query("payOrderNo") String payOrderNo,
                                  Callback<OrderInfo> callback);

    /**
     * 确认交易密码
     */
    @FormBody
    @POST("/open/check/trade/passphrase/simple")
    Call<String> confirmTransactionCode(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                        @Part("tradePassphrase") String tradePassphrase,
                                        Callback<String> callback);

    /**
     * 果实兑换
     */
    @FormBody
    @POST("/mining/diamond/withdraw")
    Call<String> exchangeFruit(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                               @Part("validate") String validate, @Part("captchaId") String captchaId, @Part("tradePhraseCode") String tradePhraseCode,
                               @Part("amount") String amount,
                               Callback<String> callback);

    /**
     * 树苗续期
     */
    @FormBody
    @PUT("/mining/user/miner/renew")
    Call<String> seedRenewal(@Query("uuid") String uuid, @Query("uid") String uid, @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                             @Query("validate") String validate, @Query("captchaId") String captchaId, @Query("tradePhraseCode") String tradePhraseCode,
                             @Query("userMinerId") int userMinerId,
                             Callback<String> callback);

    /**
     * 获取验证码 - 用户中心 (须 Token 授权)
     * 操作类型：1.邮箱；2.手机
     */
    @GET("/user/vcode/uc")
    Call<String> getPhoneCode(@Query("uuid") String uuid, @Query("uid") String uid, @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                              @Query("validate") String validate, @Query("captchaId") String captchaId, @Query("type") int type,
                              Callback<String> callback);

    /**
     * 修改交易密码
     */
    @FormBody
    @POST("/user/trade/password")
    Call<String> resetSecondPwd(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                @Part("validate") String validate, @Part("captchaId") String captchaId,
                                @Part("newFdPassWord") String newFdPassWord, @Part("vercodePhone") String vercodePhone,
                                Callback<String> callback);

    /**
     * 检查更新
     * deviceType Android: 1101；iOS: 1101
     */
    @GET("/client/lastest")
    Call<UpdateInfo> checkUpdate(@Query("deviceType") int deviceType,
                                 Callback<UpdateInfo> callback);

    /**
     * 新手指南常见问题
     * type 1100：轮播图；1101：新手指南
     */
    @GET("/open/slide/query")
    Call<CommonQuestionList> commonQuestions(@Query("type") int type, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                             Callback<CommonQuestionList> callback);

    /**
     * 退出登录
     */
    @FormBody
    @POST("/user/logout")
    Call<String> exitAccount(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                             Callback<String> callback);

    /**
     * 更新用户信息
     * key: name, avatar, wechat, code
     */
    @FormBody
    @PUT("/mining/team/info/meta")
    Call<String> modifyProfile(@Query("uuid") String uuid, @Query("uid") String uid, @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                               @Query("validate") String validate, @Query("captchaId") String captchaId, @Query("tradePhraseCode") String tradePhraseCode,
                               @Query("key") String key, @Query("value") String value,
                               Callback<String> callback);

    /**
     * 城市7日 - 统计曲线
     */
    @GET("/mining/city/master/week")
    Call<CityChartData> cityWeekData(@Query("uuid") String uuid, @Query("uid") String uid,
                                     @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                     Callback<CityChartData> callback);

    /**
     * 所有城主信息
     */
    @GET("/mining/city/masters")
    Call<List<CityMaster>> cityMasters(@Query("uuid") String uuid, @Query("uid") String uid,
                                       @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                       Callback<List<CityMaster>> callback);

    /**
     * 城市统计
     */
    @GET("/mining/city/master/statistic")
    Call<CityStatistic> cityStatistic(@Query("uuid") String uuid, @Query("uid") String uid,
                                      @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                      Callback<CityStatistic> callback);

    /**
     * 获取城主资料
     */
    @GET("/mining/city/master/profile")
    Call<CityMasterDetail> cityMasterInfo(@Query("uuid") String uuid, @Query("uid") String uid,
                                          @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                          Callback<CityMasterDetail> callback);

    /**
     * 接入点信息
     */
    @GET("/system/heartbeat")
    Call<AccessPointInfo> accessPoint(@Query("uuid") String uuid, @Query("uid") String uid,
                                      @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                      Callback<AccessPointInfo> callback);

    /**
     * 城市节点申请状态
     */
    @GET("/mining/master/apply")
    Call<Boolean> cityMasterStatus(@Query("uuid") String uuid, @Query("uid") String uid,
                                   @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                   Callback<Boolean> callback);

    /**
     * 上传城主位置
     */
    @FormBody
    @POST("/mining/city/master/location")
    Call<String> uploadLocation(@Part("uuid") String uuid, @Part("uid") String uid,
                                @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                @Part("lon") double lon, @Part("lat") double lat,
                                Callback<String> callback);

    /**
     * 申请城市节点
     * name:真实姓名
     * phone:手机号
     * wechat:微信
     * inviter:推荐人
     * inviterPhone:推荐人手机号
     * city:城市
     * address:地址
     * amount:投入资金
     * cooperateType：合作资格1个人2公司
     * manageType：经营类型1个人2合伙
     * msgJob：相关工作描述
     * msgRelation：当地人脉描述
     * star：是否报名城市大会1报名0未报名
     */
    @FormBody
    @POST("/mining/master/apply")
    Call<String> applyCityNode(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                               @Part("name") String name, @Part("phone") String phone, @Part("wechat") String wechat,
                               @Part("inviter") String inviter, @Part("inviterPhone") String inviterPhone, @Part("city") String city,
                               @Part("address") String address, @Part("amount") int amount, @Part("cooperateType") int cooperateType,
                               @Part("manageType") int manageType, @Part("msgJob") String msgJob, @Part("msgRelation") String msgRelation,
                               @Part("star") int star,
                               Callback<String> callback);

    /**
     * 获取用户总资产
     */
    @GET("/wallet/assets")
    Call<AssetsTotalInfo> queryTotalAssets(@Query("uuid") String uuid, @Query("uid") String uid,
                                           @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                           @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                           @Query("baseCurrency") String baseCurrency, @Query("local") String local,
                                           Callback<AssetsTotalInfo> callback);

    /**
     * 单个币种账户流水
     */
    @GET("/mining/profile/account/water/dmd/query")
    Call<List<CurrencyRecord>> queryCurrencyRecords(@Query("uuid") String uuid, @Query("uid") String uid,
                                                    @Query("token") String token, @Query("diamondCurrency") String diamondCurrency,
                                                    @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize,
                                                    Callback<List<CurrencyRecord>> callback);

    /**
     * 币种充值记录
     */
    @FormBody
    @POST("/coin/selectListByUuid")
    Call<String> queryCurrencyRechargeRecords(@Part("uuid") String uuid, @Part("uid") String uid,
                                              @Part("token") String token, @Part("diamondCurrency") String diamondCurrency,
                                              @Part("start") int start, @Part("size") int size,
                                              @Part("currencyId") int currencyId, @Part("beginTime") String beginTime, @Part("endTime") String endTime,
                                              @Part("status") String status,
                                              Callback<String> callback);

    /**
     * 资产兑换
     */
    @FormBody
    @POST("/mining/diamond/exchange")
    Call<String> exchangeAssets(@Part("uuid") String uuid, @Part("uid") String uid, @Part("token") String token,
                               @Part("validate") String validate, @Part("captchaId") String captchaId, @Part("tradePhraseCode") String tradePhraseCode,
                               @Part("amount") String amount, @Part("tradeCurrencyId") String tradeCurrencyId, @Part("diamondCurrencyId") String diamondCurrencyId,
                               Callback<String> callback);

}
