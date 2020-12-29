package com.alaer.lib.api;

import likly.reverse.Reverse;

public class ApiUtil {

    private static ApiService mApiService;

    public static synchronized ApiService apiService() {
        if (mApiService == null)
            mApiService = Reverse.service(ApiService.class);
        return mApiService;
    }

}
