package com.alaer.lib.data;

import android.text.TextUtils;

import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.UserData;

import likly.dollar.$;

public class UserDataUtil {
    private static final UserDataUtil instance = new UserDataUtil();

    public static UserDataUtil instance() {
        return instance;
    }

    private UserDataUtil() {
    }

    private UserData mUserData;
    private TeamDetail mTeamDetail;
    private TeamInfo mTeamInfo;
    private boolean claimNewbieMiner;
    private Balance mBalanse;
    private boolean tokenInvalid;

    public UserData getUserData() {
        return mUserData;
    }

    public TeamDetail getTeamDetail() {
        return mTeamDetail;
    }

    public void setUserData(UserData userData) {
        this.mUserData = userData;
    }

    public void saveUserDataInfo(UserData userData) {
        mUserData = userData;
        String saveJson;
        if (userData == null)
            saveJson = "";
        else
            saveJson = $.json().toJson(userData);
        $.config().putString("userData", saveJson);
    }

    public UserData getSavedUserData() {
        final String saveJson = $.config().getString("userData", "");
        if (!TextUtils.isEmpty(saveJson)) {
            UserData userData = $.json().toBean(saveJson, UserData.class);
            mUserData = userData;
            return userData;
        }

        return null;
    }

    public void saveTeamDetailInfo(TeamDetail teamDetail) {
        mTeamDetail = teamDetail;
        String saveJson;
        if (teamDetail == null)
            saveJson = "";
        else
            saveJson = $.json().toJson(teamDetail);
        $.config().putString("teamDetail", saveJson);
    }

    public TeamDetail getSavedTeamDetail() {
        final String saveJson = $.config().getString("teamDetail", "");
        if (!TextUtils.isEmpty(saveJson)) {
            TeamDetail teamDetail = $.json().toBean(saveJson, TeamDetail.class);
            mTeamDetail = teamDetail;
            return teamDetail;
        }

        return null;
    }

    public void setTeamInfo(TeamInfo teamInfo) {
        mTeamInfo = teamInfo;
    }

    public TeamInfo teamInfo() {
        return mTeamInfo;
    }

    public int teamLevel() {
        if (mTeamInfo != null && mTeamInfo.profile != null)
            return mTeamInfo.profile.level;

        return 1;
    }

    public Balance getBalanse() {
        return mBalanse;
    }

    public void setBalanse(Balance mBalanse) {
        this.mBalanse = mBalanse;
    }

    public boolean isAuthed() {
        return mTeamDetail != null && mTeamDetail.isAuthSenior > 1 && mTeamDetail.isAuthVideo > 0;
    }

    // 正在审核
    public boolean isAuthing() {
        return mTeamDetail != null && mTeamDetail.isAuthSenior == 1;
    }

    // 审核驳回
    public boolean isAuthFailed() {
        return mTeamDetail != null && mTeamDetail.isAuthSenior == -1;
    }

    // 审核驳回原因
    public String authFailedReason() {
        if (mTeamDetail != null)
            return mTeamDetail.authFailReason;
        return "";
    }

    // 3D视界用户
    public boolean isFrom3DAccount() {
        return mTeamInfo != null && mTeamInfo.channel == 2;
    }

    public void clearUserDatas() {
        saveTeamDetailInfo(null);
        saveUserDataInfo(null);
        mBalanse = null;
        mTeamInfo = null;
    }

    public boolean isClaimNewbieMiner() {
        return claimNewbieMiner;
    }

    public void setClaimNewbieMiner(boolean claimNewbieMiner) {
        this.claimNewbieMiner = claimNewbieMiner;
    }

    public boolean isTokenInvalid() {
        return tokenInvalid;
    }

    public void setTokenInvalid(boolean tokenInvalid) {
        this.tokenInvalid = tokenInvalid;
    }
}
