package com.alaer.lib.api.bean;

public class AvatarUploadResult {

    public Result attachment;
    public String message;
    public int status;

    public class Result {
        public String text;
        public String url;
    }

}
