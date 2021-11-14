package com.alaer.lib.api.bean;

/**
 * Created by HuangJW on 2021/11/14 16:24.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class TakeCoinInfo {

    public Resp resp;
    public Detail detail;

    public static class Resp {
        public String fee;
        public float cashAmount;
        public int point;
        public String msgCode;
    }

    public static class Detail {
        public int start;
        public int size;
        public int total;
        public String orderBy;
        public int linkId;
        public int currencyId;
        public String currencyName;
        public String currencyNameEn;
        public int actionId;
        public String actionName;
        public float amountLowLimit;
        public float amountHighLimit;
        public int feeType;
        public float fee;
        public int riskHighAmount;
        public int status;
        public int actionStatus;
        public String createTime;
        public String createBy;
        public String lastEditTime;
        public String lastEditBy;
        public int riskSingleDayCumulativeMaximum;
        public int riskSingleDayCumulativeMaxtimes;
    }
}