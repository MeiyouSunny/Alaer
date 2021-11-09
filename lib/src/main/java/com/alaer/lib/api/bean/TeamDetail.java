package com.alaer.lib.api.bean;

import java.io.Serializable;

public class TeamDetail implements Serializable {

    public int uid;
    public String uuid;
    public int diamond;
    public int hierarchy;
    public int action;
    public String wechat;
    public String name;
    public String uname;
    public String avatar;
    public String code;
    public String refCode;
    public int refUid;
    public String inviteUrl;
    public String diallingCode;
    // 1是已提交 2是已通过
    public int isAuthSenior;
    public int isAuthVideo;
    // 认证驳回原因
    public String authFailReason;
    public int identityPaidStatus;
    public double identityFee;
    public int secondaryCurrency;
    public int greyLevel;
    public int actionLevel;
    public String s;

}
