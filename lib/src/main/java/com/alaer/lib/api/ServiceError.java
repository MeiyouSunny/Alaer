package com.alaer.lib.api;

public class ServiceError extends RuntimeException {

    public int code;
    public String msg;

    public ServiceError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
