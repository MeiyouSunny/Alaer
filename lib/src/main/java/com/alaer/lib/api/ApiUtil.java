package com.alaer.lib.api;

import likly.reverse.Reverse;

public class ApiUtil {

    private static com.alaer.lib.api.ApiService mApiService;

    public static synchronized com.alaer.lib.api.ApiService apiService() {
        if (mApiService == null)
            mApiService = Reverse.service(com.alaer.lib.api.ApiService.class);
        return mApiService;
    }

}
