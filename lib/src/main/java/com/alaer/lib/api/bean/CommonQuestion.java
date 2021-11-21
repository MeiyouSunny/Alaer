package com.alaer.lib.api.bean;

public class CommonQuestion {

    public int slideId;
    public int type;
    public String title;
    public String description;
    public String image;
    public String action;
    public String path;
    public int status;
    public int seq;
    public String i18n;
    public String createTime;
    public Infos infos;

    public class Infos {
        public Info zh_CN;
        public Info zh_TW;
        public Info en_US;
    }

    public class Info {
        public String title;
        public String description;
        public String path;
    }

}
