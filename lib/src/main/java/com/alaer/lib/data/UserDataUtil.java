package com.alaer.lib.data;

import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;

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
}
