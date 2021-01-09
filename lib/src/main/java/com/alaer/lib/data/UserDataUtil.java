package com.alaer.lib.data;

import android.text.TextUtils;

import com.alaer.lib.api.bean.TeamDetail;
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

    public UserData getUserData() {
        if (mUserData == null)
            mUserData = new UserData();
        return mUserData;
    }

    public TeamDetail getTeamDetail() {
        if (mTeamDetail == null)
            mTeamDetail = new TeamDetail();
        return mTeamDetail;
    }

    public void setTeamDetail(TeamDetail teamDetail) {
        mTeamDetail = teamDetail;
    }

    public void setUserData(UserData userData) {
        this.mUserData = userData;
    }

    public void saveUserDataInfo(UserData userData) {
        mUserData = userData;
        final String saveJson = $.json().toJson(userData);
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
        final String saveJson = $.json().toJson(teamDetail);
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

}
