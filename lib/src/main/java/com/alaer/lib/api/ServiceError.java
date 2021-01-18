package com.alaer.lib.api;

public class ServiceError extends RuntimeException {

    public static final int ERROR_TOKEN_INVALID = 9999;

    public int code;
    public String msg;

    public ServiceError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
