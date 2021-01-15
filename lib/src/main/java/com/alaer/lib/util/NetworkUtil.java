package com.alaer.lib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络Util
 */
public class NetworkUtil {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 网络是否可用
     */
    public static boolean isNetworkAvailable() {
        if (mContext == null)
            return false;

        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

}
