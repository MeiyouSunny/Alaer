package com.cyberalaer.hybrid.data;

import com.alaer.lib.api.bean.UserData;

public class UserDataUtil {
    private static final UserDataUtil instance = new UserDataUtil();

    public static UserDataUtil instance() {
        return instance;
    }

    private UserDataUtil() {
    }

    private UserData mUserData;

    public UserData getUserData() {
        if (mUserData == null)
            mUserData = new UserData();
        return mUserData;
    }

    public void setUserData(UserData userData) {
        this.mUserData = userData;
    }
}
