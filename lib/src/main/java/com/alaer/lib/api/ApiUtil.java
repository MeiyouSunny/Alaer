package com.alaer.lib.api;

import likly.reverse.Reverse;

public class ApiUtil {

    public static ApiService apiService() {
        return Reverse.service(ApiService.class);
    }

}
